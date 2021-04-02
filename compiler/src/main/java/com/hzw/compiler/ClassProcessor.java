package com.hzw.compiler;

import com.hzw.annotations.BindView;
import com.hzw.annotations.Builder;
import com.hzw.annotations.OnClick;
import com.hzw.compiler.utils.LogUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.util.ElementFilter;

import static com.hzw.compiler.names.MethodNames.M2_INITIALIZE;
import static com.hzw.compiler.names.MethodNames.M_INITIALIZE;
import static com.hzw.compiler.names.MethodNames.M_ON_CLICK;
import static com.hzw.compiler.names.MethodNames.M_UNBIND;
import static com.hzw.compiler.names.ParameterNames.P_ACTIVITY;
import static com.hzw.compiler.names.ParameterNames.P_VIEW;
import static com.hzw.compiler.names.VariableNames.V_ACTIVITY;
import static com.hzw.compiler.names.VariableNames.V_VIEW;
import static com.hzw.compiler.types.ClassTypes.ANDROID_CLICK_LISTENER;
import static com.hzw.compiler.types.ClassTypes.ANDROID_VIEW;

/**
 * author:HZWei
 * date:  2020/8/16
 * desc:
 */
public class ClassProcessor {

    //�������б�ע���Ԫ�أ�key: packageName_����   value�����������еı�ע���Ԫ��
    private Map<String, List<Element>> mAnnotatedElementMap = new LinkedHashMap<>();

    public void process(RoundEnvironment roundEnv){
        boolean processingOver = roundEnv.processingOver();
        if (!processingOver) {
            // δ�����ע��Ԫ��
            parseAnnotationElement(roundEnv, Builder.class);
            parseAnnotationElement(roundEnv, BindView.class);
            parseAnnotationElement(roundEnv, OnClick.class);
        } else {
            // �ѽ����������
            mAnnotatedElementMap.forEach((key, elements) -> {
                LogUtils.warn("key -> " + key);
                //key�ֽ�
                String[] split = key.split("_");
                String packageName = split[0];
                String activityClassName = split[1];
                ClassName className = ClassName.get(packageName, activityClassName);

                //������
                TypeSpec.Builder classBuilder = TypeSpec.classBuilder(activityClassName + "_ViewBinding")
                        .addModifiers(Modifier.PUBLIC)
                        .addField(className, V_ACTIVITY, Modifier.PRIVATE);  //��Ա����


                //���ɹ��캯��
                MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(className, P_ACTIVITY) //���캯�����β�
                        .addStatement("this.$N=$N", V_ACTIVITY, P_ACTIVITY) //���캯����ֵ��ʼ�� this.mActivity=activity  ����N��ʾһ�����ƣ������������
                        .addStatement(M_INITIALIZE); //����init����

                //����init����
                MethodSpec.Builder initBuilder = MethodSpec.methodBuilder(M2_INITIALIZE).addModifiers(Modifier.PRIVATE);

                //���ɽ�󷽷�
                MethodSpec.Builder unbind = MethodSpec.methodBuilder(M_UNBIND).addModifiers(Modifier.PUBLIC);

                //setContentView
                for (Element element : elements) {
                    if (!element.getKind().isClass()) continue;
                    Builder builder = element.getAnnotation(Builder.class);
                    if (builder == null) continue;
                    int value = builder.value();
                    if (value == 0) continue;
                    initBuilder.addStatement("this.$N.setContentView($L)", V_ACTIVITY, value);

                }

                //��ʼ���󶨵�View
                ElementFilter.fieldsIn(elements).forEach(variableElement -> {
                    BindView bindView = variableElement.getAnnotation(BindView.class);
                    Name varName = variableElement.getSimpleName();
                    if (bindView != null) {
                        int value = bindView.value();
                        initBuilder.addStatement("$N.$N=($T)$N.findViewById($L)", V_ACTIVITY, varName, variableElement, V_ACTIVITY, value);
                        unbind.addStatement("$N.$N=$L", V_ACTIVITY, varName, null);
                    }
                });

                //��ʼ������¼�

                ElementFilter.methodsIn(elements).forEach(executableElement -> {
                    OnClick onClick = executableElement.getAnnotation(OnClick.class);
                    Name methodName = executableElement.getSimpleName();
                    if (onClick != null) {
                        int[] value = onClick.value();
                        for (int valueId : value) {
                            String fieldName = V_VIEW + Integer.toHexString(valueId);
                            classBuilder.addField(ANDROID_VIEW, fieldName, Modifier.PRIVATE);
                            initBuilder.addStatement("$N=$N.findViewById($L)", fieldName, V_ACTIVITY, valueId) // ��ʼ����Ҫ����¼���View
                                    .addStatement("$N.setOnClickListener($L)", fieldName, createAnonymousClassForClick(methodName));//��ӵ���¼�

                            unbind.addStatement("$N.setOnClickListener($L)", fieldName, null)
                                    .addStatement("this.$N=$L", fieldName, null);


                        }
                    }
                });
                unbind.addStatement("this.$N=$L", V_ACTIVITY, null);
                classBuilder.addMethod(constructorBuilder.build()) //�ѹ��캯���������
                        .addMethod(initBuilder.build())//��init�������
                        .addMethod(unbind.build());
                try {
                    JavaFile.builder(packageName, classBuilder.build()).build().writeTo(AptContext.filer);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            });
        }

    }


    /**
     * ���������ڲ���
     * @param methodName
     * @return
     */
    private TypeSpec createAnonymousClassForClick(Name methodName) {

        //����OnClickListener�Ļص����� onClick
        MethodSpec methodSpec = MethodSpec.methodBuilder(M_ON_CLICK)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ANDROID_VIEW, P_VIEW)
                .addStatement("$N.$N($N)", V_ACTIVITY, methodName, P_VIEW)
                .returns(void.class)
                .build();

        //�����ڲ���
        return TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(ANDROID_CLICK_LISTENER)
                .addMethod(methodSpec)
                .build();
    }



    private void parseAnnotationElement(RoundEnvironment env, Class<? extends Annotation> clazz) {
        env.getElementsAnnotatedWith(clazz)
                .forEach(element -> {
                    String simpleName = "";
                    if (element.getKind().isClass() || element.getKind().isInterface()) {
                        simpleName = element.getSimpleName().toString();
                    } else {
                        simpleName = element.getEnclosingElement().getSimpleName().toString();
                    }
                    String packageName = AptContext.elements.getPackageOf(element).getQualifiedName().toString();
                    List<Element> elementList = mAnnotatedElementMap.get(packageName + "_" + simpleName);
                    if (elementList == null) {
                        elementList = new ArrayList<>();
                        mAnnotatedElementMap.put(packageName + "_" + simpleName, elementList);
                    }
                    elementList.add(element);

                });
    }

}

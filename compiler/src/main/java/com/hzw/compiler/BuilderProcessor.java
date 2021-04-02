package com.hzw.compiler;

import com.hzw.annotations.BindView;
import com.hzw.annotations.Builder;
import com.hzw.annotations.OnClick;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * author:HZWei
 * date:  2020/8/12
 * desc: ע�⴦�������{@link AbstractProcessor}��javax��һ����
 *
 * <p>��google��auto-service��֧��</p>
 * <p>AutoService���ɵ�META-INFĿ¼����build/classes/java/main/·����</p>
 */
//@AutoService(Processor.class)
public class BuilderProcessor extends AbstractProcessor {

    private static final String TAG = "BuilderProcessor";

    private static final List<String> supportedAnnotations = Arrays.asList(BindView.class.getCanonicalName(),
            OnClick.class.getCanonicalName(), Builder.class.getCanonicalName());

    //�������б�ע���Ԫ�أ�key: packageName_����   value�����������еı�ע���Ԫ��
    private Map<String, List<Element>> mAnnotatedElementMap = new LinkedHashMap<>();


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        AptContext.init(processingEnv);
    }

    /**
     * ֧��ע�⴦�����������ͼ���
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new TreeSet<>(supportedAnnotations);
    }


    /**
     * ָ��ע�⴦��֧�ֵİ汾
     *
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    /**
     * <p>����ʵ�� ɨ�����б�ע���Ԫ�أ�����������������ļ����÷����ķ���ֵΪboolean���ͣ�
     * ������true,������δ����ע���Ѿ�����������ϣ����һ��ע�⴦������������������һ��ע�⴦�������������</p>
     *
     * <p>ע�⣺ֻҪ���������е�ע����뷢���˸ı䣬mark project�� process�����Żᱻ����</p>
     *
     * @param annotations
     * @param roundEnv
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        new ClassProcessor().process(roundEnv);

        return true;
    }



}

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
 * desc: 注解处理器入口{@link AbstractProcessor}是javax中一个类
 *
 * <p>对google的auto-service不支持</p>
 * <p>AutoService生成的META-INF目录是在build/classes/java/main/路径下</p>
 */
//@AutoService(Processor.class)
public class BuilderProcessor extends AbstractProcessor {

    private static final String TAG = "BuilderProcessor";

    private static final List<String> supportedAnnotations = Arrays.asList(BindView.class.getCanonicalName(),
            OnClick.class.getCanonicalName(), Builder.class.getCanonicalName());

    //保存类中被注解的元素，key: packageName_类名   value：该类下所有的被注解的元素
    private Map<String, List<Element>> mAnnotatedElementMap = new LinkedHashMap<>();


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        AptContext.init(processingEnv);
    }

    /**
     * 支持注解处理器的类类型集合
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new TreeSet<>(supportedAnnotations);
    }


    /**
     * 指定注解处理支持的版本
     *
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    /**
     * <p>必须实现 扫描所有被注解的元素，并作处理，最后生成文件。该方法的返回值为boolean类型，
     * 若返回true,则代表本次处理的注解已经都被处理，不希望下一个注解处理器继续处理，否则下一个注解处理器会继续处理</p>
     *
     * <p>注意：只要当工程类中的注解代码发生了改变，mark project后 process方法才会被调用</p>
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

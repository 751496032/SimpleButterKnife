package com.hzw.compiler.types;

import com.hzw.compiler.AptContext;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

/**
 * author:HZWei
 * date:  2020/8/16
 * desc:
 */
public class ClassTypes {


    public final static TypeName ANDROID_VIEW=getClassTypeName("android.view.View");

    public final static TypeName ANDROID_CLICK_LISTENER=getClassTypeName("android.view.View.OnClickListener");


    /**
     * ��ȡ����ʵ��������
     * @param className <p>�� android.view.View</p>
     * @return ����ʵ�������� {@link TypeName}   {@link ClassName}
     */
    public static TypeName getClassTypeName(String className) {
        return ParameterizedTypeName.get(AptContext.elements.getTypeElement(className).asType());
    }



}

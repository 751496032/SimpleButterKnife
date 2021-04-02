package com.hzw.compiler.utils;

import com.hzw.compiler.AptContext;

import javax.tools.Diagnostic;

/**
 * author:HZWei
 * date:  2020/8/19
 * desc:
 */
public class LogUtils {

    public static void warn(String msg) {
        AptContext.messager.printMessage(Diagnostic.Kind.WARNING, msg);
    }

    public static void error(String msg) {
        AptContext.messager.printMessage(Diagnostic.Kind.ERROR, msg);
    }


}

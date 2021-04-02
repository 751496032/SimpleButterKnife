package com.hzw.annotations;

import androidx.annotation.IdRes;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author:HZWei
 * date:  2020/8/6
 * desc:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface OnClick {

    @IdRes int[] value() default {0};

}

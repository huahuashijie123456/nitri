package com.fuhuitong.nitri.common.utils.Excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Wang
 * @Date 2019/5/11 0011 18:32
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {
    String dict() default "";

    String name();

    int sort();

    String dataformat() default "";

    boolean permission() default false;

    int type() default 0;
}



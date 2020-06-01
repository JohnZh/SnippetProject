package com.john.purejava.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    ColumnType value() default ColumnType.STRING;
    String name() default "";
    int length() default 0;
    Constraint constraint() default @Constraint;
}

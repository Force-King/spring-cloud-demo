package com.br.demo.annotation;


import com.br.demo.enums.DBName;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface DataSourceAnnotation {
  DBName dbName() default DBName.DEMO_R;
}

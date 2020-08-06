package com.domain.demo.datasource;

/**
 * @Description 动态数据源参数常量
 * @Classname DataSourceConstant
 * @Author CleverApe
 * @Date 2020-08-06 14:27
 * @Version V1.0
 */
public class DataSourceConstant {

    /** 多数据源的名称集合 **/
    public static final String MULTIPLE_DATA_SOURCE_NAME = "spring.datasource.name";

    /** druid初始化的名称 **/
    public static final String DRUID_INIT_PREFIX = "spring.datasource.druid.";

    /** 数据源的前缀名称 **/
    public static final String DATA_SOURCE_PREFIX = "spring.datasource.";

    /** datasource 的type名称 **/
    public static final String DATA_SOURCE_TYPE_NAME = "spring.datasource.type";

    /** 数据源初始化参数 **/
    public static final String INIT_PARAM = "spring.datasource.init.param";
}

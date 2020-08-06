package com.domain.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.domain.demo.datasource.DataSourceConstant;
import com.domain.demo.datasource.DynamicDataSource;
import com.domain.demo.datasource.DynamicDataSourceContextHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description 多数据源初始化
 * @Classname DruidConfig
 * @Author CleverApe
 * @Date 2020-08-06 14:27
 * @Version V1.0
 */
@Configuration
@EnableTransactionManagement
@AllArgsConstructor
@Slf4j
public class DruidConfig {

    private static final Logger logger = LoggerFactory.getLogger(DruidConfig.class);

    private Environment environment;

    @Bean(name = "dataSource")
    @Primary
    public DataSource dataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        LinkedHashMap<Object, Object> targetDatasources = new LinkedHashMap<Object, Object>();
        initCustomDataSources(targetDatasources);
        dynamicDataSource.setDefaultTargetDataSource(targetDatasources.get(DynamicDataSourceContextHolder.datasourceId.get(0)));
        dynamicDataSource.setTargetDataSources(targetDatasources);
        dynamicDataSource.afterPropertiesSet();
        return dynamicDataSource;
    }

    private void initCustomDataSources(LinkedHashMap<Object, Object> targetDataResources) {
        // 获取数据源的集合names
        String dataSourceNames = environment.getProperty(DataSourceConstant.MULTIPLE_DATA_SOURCE_NAME);
        logger.info("dataSourceNames: {}", dataSourceNames);
        Binder binder = Binder.get(environment);

        if (StringUtils.isEmpty(dataSourceNames)) {
            logger.error("The multiple data source list are empty");
            return;
        }

        // 初始化的属性值, 反射设置
        Map<String, Object> initParamMap = binder.bind(DataSourceConstant.INIT_PARAM, Bindable.mapOf(String.class, Object.class)).get();

        for (String dataSourceName : dataSourceNames.split(",")) {
            try {
                // 构建数据源
                DruidDataSource druidDataSource = binder.bind(DataSourceConstant.DATA_SOURCE_PREFIX + dataSourceName, DruidDataSource.class).get();
                for (Map.Entry<String, Object> entry : initParamMap.entrySet()) {
                    BeanWrapper beanWrapper = new BeanWrapperImpl(druidDataSource);
                    beanWrapper.setPropertyValue(entry.getKey(), entry.getValue());
                }

                if (druidDataSource != null) {
                    druidDataSource.setName(dataSourceName);
                    DynamicDataSourceContextHolder.datasourceId.add(dataSourceName);
                    targetDataResources.put(dataSourceName, druidDataSource);
                    logger.info("Data source initialization 【" + dataSourceName + "】 successfully ...");
                }

            } catch (Exception e) {
                logger.error("Data source initialization 【" + dataSourceName + "】 failed ...", e);
            }
        }
    }

}

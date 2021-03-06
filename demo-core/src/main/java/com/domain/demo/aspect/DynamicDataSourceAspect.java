package com.domain.demo.aspect;

import com.domain.demo.annotation.DataSourceAnnotation;
import com.domain.demo.datasource.DynamicDataSourceContextHolder;
import com.domain.demo.enums.DBName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Description 动态多数据源切面
 * @Classname DynamicDataSourceAspect
 * @Author CleverApe
 * @Date 2020-08-03 14:29
 * @Version V1.0
 */
@Aspect
@Order(-1)
@Component
public class DynamicDataSourceAspect {

    private static final Logger logger = LogManager.getLogger(DynamicDataSourceAspect.class);
      
    /**
     * 切换数据库
     * @param point
     * @param dataSourceAnnotation
     * @return
     * @throws Throwable
     */
    @Before(value = "@annotation(dataSourceAnnotation)")
    public void changeDataSource(JoinPoint point, DataSourceAnnotation dataSourceAnnotation){
        String dbName = dataSourceAnnotation.dbName().getName();   logger.info("get dbName from annotation {}", dbName);
        if(!DynamicDataSourceContextHolder.existDateSoure(dbName)){
            logger.warn("No data source found dbName is {}", dbName);
            //没有找到库名 设置默认为dmp
            DynamicDataSourceContextHolder.setDateSoureType(DBName.DEMO_R.getName());
            return;
        }else{
            DynamicDataSourceContextHolder.setDateSoureType(dbName);
        }
    }

    /**
     * @Title: destroyDataSource
     * @Description: 销毁数据源  在所有的方法执行执行完毕后
     * @param point
     * @param dataSourceAnnotation
     * @return void
     * @throws
     */
    @After(value = "@annotation(dataSourceAnnotation)")
    public void destroyDataSource(JoinPoint point, DataSourceAnnotation dataSourceAnnotation){
        DynamicDataSourceContextHolder.clearDateSoureType();
    }
}

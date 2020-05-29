package com.br.demo.aspect;

import com.br.demo.annotation.DataSourceAnnotation;
import com.br.demo.datasource.DynamicDataSourceContextHolder;
import com.br.demo.enums.DBName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


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

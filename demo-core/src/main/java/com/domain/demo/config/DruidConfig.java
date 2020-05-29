package com.domain.demo.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.domain.demo.datasource.DynamicDataSource;
import com.domain.demo.datasource.DynamicDataSourceContextHolder;
import com.domain.demo.util.AppConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.web.servlet.DynamicRegistrationBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.FilterRegistration.Dynamic;
import javax.sql.DataSource;
import java.util.*;


@Configuration
@EnableTransactionManagement
public class DruidConfig implements EnvironmentAware {

    private List<String> customDataSourceNames = new ArrayList<String>();

    private Logger logger = LogManager.getLogger(DruidConfig.class);

    private ConversionService conversionService = new DefaultConversionService();

    private Environment environment;

    /**
     * @param environment the enviroment to set
     */
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Bean(name = "dataSource")
    @Primary
    public AbstractRoutingDataSource dataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        LinkedHashMap<Object, Object> targetDatasources = new LinkedHashMap<Object, Object>();
        initCustomDataSources(targetDatasources);
        dynamicDataSource.setDefaultTargetDataSource(targetDatasources.get(customDataSourceNames.get(0)));
        dynamicDataSource.setTargetDataSources(targetDatasources);
        dynamicDataSource.afterPropertiesSet();
        return dynamicDataSource;
    }

    private void initCustomDataSources(LinkedHashMap<Object, Object> targetDataResources)
    {
        String dataSourceNames = environment.getProperty(AppConstants.DATA_SOURCE_PREfIX_CUSTOM + AppConstants.DATA_SOURCE_CUSTOM_NAME);
        
        Binder binder = Binder.get(environment);
        
        if(StringUtils.isEmpty(dataSourceNames))
        {
            logger.error("The multiple data source list are empty.");
        }
        else{
              Map<String, Object> druidPropertiesMaps = binder.bind("spring.datasource.druid", Bindable.mapOf(String.class, Object.class)).get();
              Map<String,Object> druidValuesMaps = new HashMap<String,Object>();
              for(String key:druidPropertiesMaps.keySet())
              {
                  String druidKey = AppConstants.DRUID_SOURCE_PREFIX + key;
                  druidValuesMaps.put(druidKey,druidPropertiesMaps.get(key));
              }

              MutablePropertyValues dataSourcePropertyValue = new MutablePropertyValues(druidValuesMaps);

              for (String dataSourceName : dataSourceNames.split(AppConstants.SEP)) {
                try {
                    Map<String, Object> dsMaps = binder.bind("spring.custom.datasource." + dataSourceName, Bindable.mapOf(String.class, Object.class)).get();
                    for(String dsKey : dsMaps.keySet())
                    {
                        if(dsKey.equals("type"))
                        {
                            dataSourcePropertyValue.addPropertyValue("spring.datasource.type", dsMaps.get(dsKey));
                        }
                        else
                        {
                            String druidKey = AppConstants.DRUID_SOURCE_PREFIX + dsKey;
                            dataSourcePropertyValue.addPropertyValue(druidKey, dsMaps.get(dsKey));
                        }
                    }

                    DataSource ds = dataSourcebuild(dataSourcePropertyValue);
                    if(null != ds){
                        if(ds instanceof DruidDataSource)
                        {
                            DruidDataSource druidDataSource = (DruidDataSource)ds;
                            druidDataSource.setName(dataSourceName);
                            initDruidFilters(druidDataSource);
                        }

                        customDataSourceNames.add(dataSourceName);
                        DynamicDataSourceContextHolder.datasourceId.add(dataSourceName);
                        targetDataResources.put(dataSourceName,ds);

                    }
                    logger.info("Data source initialization 【"+dataSourceName+"】 successfully ...");
                } catch (Exception e) {
                    logger.error("Data source initialization【"+dataSourceName+"】 failed ...", e);
                }
            }
        }
    }


    /**
     * @Title: DataSourcebuild
     * @Description: 创建数据源
     * @param dataSourcePropertyValue 数据源创建所需参数
     *
     * @return DataSource 创建的数据源对象
     */
    @SuppressWarnings("resource")
    public DataSource dataSourcebuild(MutablePropertyValues dataSourcePropertyValue)
    {
        DataSource ds = null;
        DruidDataSource dsMaps = null;

        if(dataSourcePropertyValue.isEmpty()){
            return ds;
        }

        String type = dataSourcePropertyValue.get("spring.datasource.type").toString();

        if(StringUtils.isNotEmpty(type))
        {
            if(StringUtils.equals(type,DruidDataSource.class.getTypeName()))
            {   
                Binder binder = Binder.get(environment);
                dsMaps = binder.bind("spring.datasource.druid", Bindable.of(DruidDataSource.class)).get();
                
                //补充没有绑定上的参数
                PropertyValue name = dataSourcePropertyValue.getPropertyValue("spring.datasource.druid.name");
                PropertyValue username = dataSourcePropertyValue.getPropertyValue("spring.datasource.druid.username");
                PropertyValue driverClassName = dataSourcePropertyValue.getPropertyValue("spring.datasource.druid.driver-class-name");
                PropertyValue url = dataSourcePropertyValue.getPropertyValue("spring.datasource.druid.url");
                PropertyValue password = dataSourcePropertyValue.getPropertyValue("spring.datasource.druid.password");
                PropertyValue typePro = dataSourcePropertyValue.getPropertyValue("spring.datasource.type");
                Properties p = new Properties();
                try {
                  p.put("name", name.getValue().toString());
                  p.put("username", username.getValue().toString());
                  p.put("url", url.getValue().toString());
                  p.put("password", password.getValue().toString());
                  p.put("type", typePro.getValue().toString());
                  p.put("driverClassName", driverClassName.getValue().toString());
                                
                  ds  = DruidDataSourceFactory.createDataSource(p);
                  
                  dsMaps.setName(name.getValue().toString());
                  dsMaps.setUsername(username.getValue().toString());
                  dsMaps.setUrl(url.getValue().toString());
                  dsMaps.setPassword(password.getValue().toString());
                  dsMaps.setDriverClassName(driverClassName.getValue().toString());
                  
                } catch (Exception e) {
                  e.printStackTrace();
                }
            }
        }
        return dsMaps;
    }

    @Bean
    public ServletRegistrationBean statViewServlet(){
        Binder binder = Binder.get(environment);
        Map<String, Object> druidPropertiesMaps = binder.bind("spring.datasource.druid.stat-view-servlet" , Bindable.mapOf(String.class, Object.class)).get();

        boolean statViewServletEnabled = false;
        String statViewServletEnabledKey = AppConstants.ENABLED_ATTRIBUTE_NAME;
        ServletRegistrationBean registrationBean = null;

        if(druidPropertiesMaps.containsKey(statViewServletEnabledKey))
        {
            String statViewServletEnabledValue =
                    druidPropertiesMaps.get(statViewServletEnabledKey).toString();
            statViewServletEnabled = Boolean.parseBoolean(statViewServletEnabledValue);
        }
        if(statViewServletEnabled){
            registrationBean = new ServletRegistrationBean();
            StatViewServlet statViewServlet = new StatViewServlet();

            registrationBean.setServlet(statViewServlet);

            String urlPatternKey= "url-pattern";
            String allowKey= "allow";
            String denyKey= "deny";
            String usernameKey= "login-username";
            String secretKey = "login-password";
            String resetEnableKey= "reset-enable";

            if(druidPropertiesMaps.containsKey(urlPatternKey)){
                String urlPatternValue =
                        druidPropertiesMaps.get(urlPatternKey).toString();
                registrationBean.addUrlMappings(urlPatternValue);
            }
            else
            {
                registrationBean.addUrlMappings("/druid/*");
            }

            addBeanParameter(druidPropertiesMaps,registrationBean, "allow",allowKey);
            addBeanParameter(druidPropertiesMaps,registrationBean, "deny",denyKey);
            addBeanParameter(druidPropertiesMaps,registrationBean, "loginUsername",usernameKey);
            addBeanParameter(druidPropertiesMaps,registrationBean, "loginPassword",secretKey);
            addBeanParameter(druidPropertiesMaps,registrationBean, "resetEnable",resetEnableKey);
        }

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        Binder binder = Binder.get(environment);
        Map<String, Object> druidPropertiesMaps = binder.bind("spring.datasource.druid.web-stat-filter" , Bindable.mapOf(String.class, Object.class)).get();


        boolean webStatFilterEnabled = false;
        String webStatFilterEnabledKey = AppConstants.ENABLED_ATTRIBUTE_NAME;
        FilterRegistrationBean registrationBean = null;
        if(druidPropertiesMaps.containsKey(webStatFilterEnabledKey))
        {
            String webStatFilterEnabledValue =
                    druidPropertiesMaps.get(webStatFilterEnabledKey).toString();
            webStatFilterEnabled = Boolean.parseBoolean(webStatFilterEnabledValue);
        }
        if(webStatFilterEnabled){
            registrationBean = new FilterRegistrationBean();
            WebStatFilter filter = new WebStatFilter();
            registrationBean.setFilter(filter);

            String urlPatternKey = "url-pattern";
            String exclusionsKey = "exclusions";
            String sessionStatEnabledKey = "session-stat-enable";
            String profileEnabledKey = "profile-enable";
            String principalCookieNameKey = "principal-cookie-name";
            String principalSessionNameKey = "principal-session-name";
            String sessionStateMaxCountKey = "session-stat-max-count";

            if(druidPropertiesMaps.containsKey(urlPatternKey)){
                String urlPatternValue =
                        druidPropertiesMaps.get(urlPatternKey).toString();
                registrationBean.addUrlPatterns(urlPatternValue);
            }
            else{
                registrationBean.addUrlPatterns("/*");
            }

            if(druidPropertiesMaps.containsKey(exclusionsKey)){
                String exclusionsValue =
                        druidPropertiesMaps.get(exclusionsKey).toString();
                registrationBean.addInitParameter("exclusions",exclusionsValue);
            }
            else{
                registrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
            }

            addBeanParameter(druidPropertiesMaps,registrationBean, "sessionStatEnable",sessionStatEnabledKey);
            addBeanParameter(druidPropertiesMaps,registrationBean, "profileEnable",profileEnabledKey);
            addBeanParameter(druidPropertiesMaps,registrationBean, "principalCookieName",principalCookieNameKey);
            addBeanParameter(druidPropertiesMaps,registrationBean, "sessionStatMaxCount",sessionStateMaxCountKey);
            addBeanParameter(druidPropertiesMaps,registrationBean, "principalSessionName",principalSessionNameKey);
        }
        return registrationBean;
    }

    private void addBeanParameter(Map<String,Object> druidPropertyMap, RegistrationBean registrationBean, String paramName, String propertyKey){
        if(druidPropertyMap.containsKey(propertyKey)){
            String propertyValue =
                    druidPropertyMap.get(propertyKey).toString();
            ((DynamicRegistrationBean<Dynamic>) registrationBean).addInitParameter(paramName, propertyValue);
        }
    }

    private void initDruidFilters(DruidDataSource druidDataSource){
        List<Filter> filters = druidDataSource.getProxyFilters();
        String filterNames= environment.getProperty("spring.datasource.druid.filters");
        String[] filterNameArray = filterNames.split("\\,");

        for(int i=0; i<filterNameArray.length;i++){
            String filterName = filterNameArray[i];
            Filter filter = filters.get(i);

//            Map<String, Object> filterValueMap = filterProperty.getSubProperties(filterName + ".");
//            
//            String statFilterEnabled = filterValueMap.get(ENABLED_ATTRIBUTE_NAME).toString();
//            if(statFilterEnabled.equals("true")){
//                MutablePropertyValues propertyValues = new  MutablePropertyValues(filterValueMap);
//                RelaxedDataBinder dataBinder = new RelaxedDataBinder(filter);
//                dataBinder.bind(propertyValues);
//            }
        }
    }
}

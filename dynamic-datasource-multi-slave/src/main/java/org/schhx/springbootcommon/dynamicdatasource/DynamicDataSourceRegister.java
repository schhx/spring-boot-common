package org.schhx.springbootcommon.dynamicdatasource;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shanchao
 * @date 2019-01-22
 */
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private static final String DATASOURCE_PROP_PREX = "spring.datasource";

    private Environment environment;

    private DataSource defaultDataSource;
    private Map<String, DataSource> dataSources = new HashMap<>();

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        initDataSourceDefinitions();
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        beanDefinition.setPrimary(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
        mpv.addPropertyValue("targetDataSources", dataSources);
        beanDefinitionRegistry.registerBeanDefinition("dynamicDataSource", beanDefinition);
    }


    /**
     * 读取配置文件
     */
    private void initDataSourceDefinitions() {
        Set<String> dataSourceKeys = new RelaxedPropertyResolver(environment, DATASOURCE_PROP_PREX)
                .getSubProperties(".")
                .keySet()
                .stream()
                .map(key -> key.split("\\.")[0])
                .collect(Collectors.toSet());
        if(!dataSourceKeys.contains(DataSourceConstant.DB_TYPE_MASTER)) {
            throw new RuntimeException("必须配置master数据源");
        }

        dataSourceKeys.stream().forEach(key -> {
            Map<String, Object> prop = new RelaxedPropertyResolver(environment, DATASOURCE_PROP_PREX + "." + key).getSubProperties(".");
            PropertyValues propertyValues = new MutablePropertyValues(prop);

            DataSource dataSource = DataSourceBuilder.create().build();
            RelaxedDataBinder dataBinder = new RelaxedDataBinder(dataSource);
            dataBinder.bind(propertyValues);
            dataSources.put(key, dataSource);
        });

        defaultDataSource = dataSources.get(DataSourceConstant.DB_TYPE_MASTER);

        List<String> slaveKeys = new ArrayList<>(dataSourceKeys);
        slaveKeys.remove(DataSourceConstant.DB_TYPE_MASTER);
        DBContextHolder.setSlaveDbKeys(slaveKeys);
    }
}

package org.schhx.springbootcommon.dynamicdatasource;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;
import java.util.*;

/**
 * @author shanchao
 * @date 2019-01-22
 */
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private static final String DATASOURCE_PROP_PREX = "spring.datasource";

    private Binder binder;

    private DataSource defaultDataSource;
    private Map<String, DataSource> dataSources = new HashMap<>();

    /**
     * 别名
     */
    private final static ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();

    /**
     * 由于部分数据源配置不同，所以在此处添加别名，避免切换数据源出现某些参数无法注入的情况
     */
    static {
        aliases.addAliases("url", new String[]{"jdbc-url"});
        aliases.addAliases("username", new String[]{"user"});
    }

    @Override
    public void setEnvironment(Environment environment) {
        binder = Binder.get(environment);
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
        Set<String> dataSourceKeys = binder.bind(DATASOURCE_PROP_PREX, Map.class).get().keySet();
        if (!dataSourceKeys.contains(DataSourceConstant.DB_TYPE_MASTER)) {
            throw new RuntimeException("必须配置master数据源");
        }

        dataSourceKeys.stream().forEach(key -> {
            Map<String, Object> prop = binder.bind(DATASOURCE_PROP_PREX + "." + key, Map.class).get();

            DataSource dataSource = DataSourceBuilder.create().build();
            bind(dataSource, prop);
            dataSources.put(key, dataSource);
        });

        defaultDataSource = dataSources.get(DataSourceConstant.DB_TYPE_MASTER);

        List<String> slaveKeys = new ArrayList<>(dataSourceKeys);
        slaveKeys.remove(DataSourceConstant.DB_TYPE_MASTER);
        DBContextHolder.setSlaveDbKeys(slaveKeys);
    }


    /**
     * 绑定参数
     *
     * @param result
     * @param properties
     */
    private void bind(DataSource result, Map properties) {
        ConfigurationPropertySource source = new MapConfigurationPropertySource(properties);
        Binder binder = new Binder(new ConfigurationPropertySource[]{source.withAliases(aliases)});
        // 将参数绑定到对象
        binder.bind(ConfigurationPropertyName.EMPTY, Bindable.ofInstance(result));
    }
}

package org.schhx.springbootcommon.dynamicdatasource;

import org.schhx.springbootcommon.dynamicdatasource.selector.DataSourceSelector;
import org.schhx.springbootcommon.dynamicdatasource.selector.RoundDataSourceSelector;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author shanchao
 * @date 2018-09-28
 */
@Configuration
@Import({DynamicDataSourceRegister.class})
public class DynamicDatasourceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DataSourceSelector dataSourceSelector() {
        return new RoundDataSourceSelector();
    }

    @Bean
    public DataSourceAspect dataSourceAspect() {
        return new DataSourceAspect();
    }
}

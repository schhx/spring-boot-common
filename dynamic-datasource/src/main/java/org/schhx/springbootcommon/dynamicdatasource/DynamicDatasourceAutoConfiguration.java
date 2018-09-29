package org.schhx.springbootcommon.dynamicdatasource;

import org.springframework.context.annotation.Import;

/**
 * @author shanchao
 * @date 2018-09-28
 */
@Import({DataSourceAop.class, DataSourceConfig.class})
public class DynamicDatasourceAutoConfiguration {
}

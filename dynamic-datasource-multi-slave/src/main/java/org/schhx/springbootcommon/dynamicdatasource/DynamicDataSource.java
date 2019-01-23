package org.schhx.springbootcommon.dynamicdatasource;

import org.schhx.springbootcommon.dynamicdatasource.selector.DataSourceSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author shanchao
 * @date 2018-08-30
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Autowired
    private DataSourceSelector selector;

    @Override
    protected Object determineCurrentLookupKey() {
        return selector.getDataSourceKey();
    }
}

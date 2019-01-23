package org.schhx.springbootcommon.dynamicdatasource.selector;

import org.schhx.springbootcommon.dynamicdatasource.DBContextHolder;
import org.schhx.springbootcommon.dynamicdatasource.DataSourceConstant;

/**
 * @author shanchao
 * @date 2019-01-22
 */
public abstract class AbstractDataSourceSelector implements DataSourceSelector {

    @Override
    public String getDataSourceKey() {
        if(DataSourceConstant.DB_TYPE_MASTER.endsWith(DBContextHolder.getDbType())) {
            return DataSourceConstant.DB_KEY_MASTER;
        } else if(DBContextHolder.getSlaveDbKeys().size() == 1) {
            return DBContextHolder.getSlaveDbKeys().get(0);
        } else {
            return getSlaveDataSourceKey();
        }
    }

    /**
     * 获取从库 key
     *
     * @return
     */
    protected abstract String getSlaveDataSourceKey();
}

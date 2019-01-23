package org.schhx.springbootcommon.dynamicdatasource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shanchao
 * @date 2018-08-30
 */
public class DBContextHolder {

    private static List<String> slaveDbKeys = new ArrayList<>();

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static String getDbType() {
        String db = contextHolder.get();
        return db != null ? db : DataSourceConstant.DB_TYPE_MASTER;
    }

    public static void setDbType(String dbType) {
        if (!(dbType.equals(DataSourceConstant.DB_TYPE_MASTER) || dbType.equals(DataSourceConstant.DB_TYPE_SLAVE))) {
            contextHolder.set(DataSourceConstant.DB_TYPE_MASTER);
        } else {
            contextHolder.set(dbType);
        }
    }

    public static void clearDbType() {
        contextHolder.set(null);
    }

    public static void setSlaveDbKeys(List<String> keys) {
        slaveDbKeys = keys;
    }

    public static List<String> getSlaveDbKeys() {
        return slaveDbKeys;
    }
}

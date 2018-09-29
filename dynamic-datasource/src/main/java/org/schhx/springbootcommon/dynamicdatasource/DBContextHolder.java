package org.schhx.springbootcommon.dynamicdatasource;

/**
 * @author shanchao
 * @date 2018-08-30
 */
public class DBContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static String getDbType() {
        String db = contextHolder.get();
        return db != null ? db : DataSourceConstant.MASTER;
    }

    public static void setDbType(String dbType) {
        if (!(dbType.equals(DataSourceConstant.MASTER) || dbType.equals(DataSourceConstant.SLAVE))) {
            contextHolder.set(DataSourceConstant.MASTER);
        } else {
            contextHolder.set(dbType);
        }
    }

    public static void clearDbType() {
        contextHolder.set(null);
    }
}

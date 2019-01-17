package org.schhx.springbootcommon.distributedlock;

import java.util.concurrent.TimeUnit;

/**
 * @author shanchao
 * @date 2019-01-17
 */
public interface LockOperation {

    /**
     * 加锁
     *
     * @param key      key
     * @param value    value，用于解锁，最好使用不重复的值，防止被误解锁
     * @param timeout
     * @param timeUnit
     * @return 获取成功：true，获取失败：false
     */
    boolean lock(String key, String value, long timeout, TimeUnit timeUnit);

    /**
     * 解锁
     *
     * @param key
     * @param value
     * @return
     */
    boolean unlock(String key, String value);

    /**
     * 延时解锁
     *
     * @param key
     * @param value
     * @param delayTime
     * @param unit
     * @return
     */
    boolean unlock(final String key, final String value, long delayTime, TimeUnit unit);
}

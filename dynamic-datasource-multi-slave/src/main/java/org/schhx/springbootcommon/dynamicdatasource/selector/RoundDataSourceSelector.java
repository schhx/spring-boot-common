package org.schhx.springbootcommon.dynamicdatasource.selector;

import org.schhx.springbootcommon.dynamicdatasource.DBContextHolder;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询算法
 *
 * @author shanchao
 * @date 2019-01-22
 */
public class RoundDataSourceSelector extends AbstractDataSourceSelector {

    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private static final int SLAVE_COUNT = DBContextHolder.getSlaveDbKeys().size();


    @Override
    protected String getSlaveDataSourceKey() {
        int i = atomicInteger.getAndIncrement() % SLAVE_COUNT;
        if(i < 0) {
            i += SLAVE_COUNT;
        }
        return DBContextHolder.getSlaveDbKeys().get(i);
    }
}

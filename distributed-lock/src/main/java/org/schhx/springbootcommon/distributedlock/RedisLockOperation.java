package org.schhx.springbootcommon.distributedlock;

import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author shanchao
 * @date 2019-01-17
 */
public class RedisLockOperation implements LockOperation {

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

    private StringRedisTemplate redisTemplate;

    public RedisLockOperation(StringRedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean lock(String key, String value, long timeout, TimeUnit timeUnit) {
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.set(key.getBytes(), value.getBytes(), Expiration.from(timeout, timeUnit), RedisStringCommands.SetOption.SET_IF_ABSENT);
            return null;
        });
        String lockValue = redisTemplate.opsForValue().get(key);
        return value.equals(lockValue);
    }

    @Override
    public boolean unlock(String key, String value) {
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        String resourceValue = redisTemplate.opsForValue().get(key);
        return value.equals(resourceValue) && redisTemplate.delete(key);
    }

    @Override
    public boolean unlock(String key, String value, long delayTime, TimeUnit unit) {
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        if (delayTime <= 0) {
            return unlock(key, value);
        } else {
            executorService.schedule(() -> unlock(key, value), delayTime, unit);
            return true;
        }
    }
}

package org.schhx.springbootcommon.distributedlock;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author shanchao
 * @date 2018-09-28
 */
@Configuration
@Import({DistributedLockAspect.class})
public class DistributedLockAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public LockOperation redisLockOperation(StringRedisTemplate redisTemplate) {
        return new RedisLockOperation(redisTemplate);
    }
}

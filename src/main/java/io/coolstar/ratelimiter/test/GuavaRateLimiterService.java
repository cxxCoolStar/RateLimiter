package io.coolstar.ratelimiter.test;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Service;

@Service
public class GuavaRateLimiterService {
    /*每秒控制5个许可*/
    RateLimiter rateLimiter = RateLimiter.create(5.0);
 
    /**
     * 获取令牌
     *
     * @return
     */
    public boolean tryAcquire() {
        return rateLimiter.tryAcquire();
    }
    
}

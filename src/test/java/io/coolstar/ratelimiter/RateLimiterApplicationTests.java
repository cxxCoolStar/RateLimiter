package io.coolstar.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;
import io.coolstar.ratelimiter.exception.InvalidUrlException;
import io.coolstar.ratelimiter.rule.ApiLimit;
import io.coolstar.ratelimiter.rule.AppUrlRateLimitRule;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class RateLimiterApplicationTests {

    @Test
    void contextLoads() throws InvalidUrlException {
        AppUrlRateLimitRule appUrlRateLimitRule = new AppUrlRateLimitRule();

        appUrlRateLimitRule.addLimitInfo(new ApiLimit("/api/hello", 1));
        ApiLimit limitInfo = appUrlRateLimitRule.getLimitInfo("/api/hello");
        System.out.println(limitInfo);
    }
}

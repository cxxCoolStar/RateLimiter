package io.coolstar.ratelimiter.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenxingxing
 * @date 2022/12/15 8:16 下午
 */
@RestController
public class RateLimitController {

    @RateLimitAspect
    @RequestMapping("/ratelimiter")
    public String testRateLimiter() {
            return "success";
    }
}

package io.coolstar.ratelimiter.test;

import java.lang.annotation.*;

/**
 * @author chenxingxing
 * @date 2022/12/15 8:40 下午
 */
@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimitAspect {
}

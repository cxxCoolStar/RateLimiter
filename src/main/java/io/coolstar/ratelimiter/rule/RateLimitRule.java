package io.coolstar.ratelimiter.rule;

/**
 * @author chenxingxing
 * @date 2022/12/12 9:14 下午
 */
public interface RateLimitRule {

    /**
     * 通过appId和api获取ApiLimit
     * @param appId
     * @param api
     * @return
     */
    ApiLimit getLimit(String appId, String api);
}

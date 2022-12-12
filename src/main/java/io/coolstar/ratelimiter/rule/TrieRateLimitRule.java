package io.coolstar.ratelimiter.rule;

/**
 * @author chenxingxing
 * @date 2022/12/11 7:28 下午
 */

public class TrieRateLimitRule implements RateLimitRule{
    public TrieRateLimitRule(RuleConfig ruleConfig) {
        //...
    }

    public ApiLimit getLimit(String appId, String api) {
        //...
        return new ApiLimit();
    }
}

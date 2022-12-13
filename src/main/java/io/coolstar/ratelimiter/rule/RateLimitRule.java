package io.coolstar.ratelimiter.rule;

import io.coolstar.ratelimiter.exception.InvalidUrlException;
import io.coolstar.ratelimiter.rule.datasource.UniformRuleConfigMapping;

import java.util.List;

/**
 * @author chenxingxing
 * @date 2022/12/12 9:14 下午
 */
public interface RateLimitRule {

    /**
     * get limit info of one url.
     *
     * @param appId
     * @param api
     * @return
     * @throws InvalidUrlException
     */
    ApiLimit getLimit(String appId, String api) throws InvalidUrlException;

    /**
     * add one limit for one app.
     *
     * @param appId
     * @param apiLimit
     * @throws InvalidUrlException
     */
    void addLimit(String appId, ApiLimit apiLimit) throws InvalidUrlException;

    /**
     * add limits for one app.
     *
     * @param limits
     * @throws InvalidUrlException
     */
    void addLimits(String appId, List<ApiLimit> limits) throws InvalidUrlException;

    /**
     * override old rule.
     *
     * @param uniformRuleConfigMapping
     */
    void rebuildRule(UniformRuleConfigMapping uniformRuleConfigMapping);

    /**
     * add rule into the existing rule.
     *
     * @param uniformRuleConfigMapping
     */
    void addRule(UniformRuleConfigMapping uniformRuleConfigMapping);
}

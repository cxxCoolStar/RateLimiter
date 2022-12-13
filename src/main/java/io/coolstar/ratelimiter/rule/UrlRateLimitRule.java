package io.coolstar.ratelimiter.rule;

import io.coolstar.ratelimiter.exception.InvalidUrlException;
import io.coolstar.ratelimiter.rule.datasource.UniformRuleConfigMapping;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Rate limit rule which use trie tree to store limit rules. This class is thread-safe.
 *
 * @author chenxingxing
 * @date 2022/12/11 7:28 下午
 */
public class UrlRateLimitRule implements RateLimitRule{

    /**
     * store <appId, limit rules> pairs.
     */
    private volatile ConcurrentHashMap<String, AppUrlRateLimitRule> limitRules =
            new ConcurrentHashMap<>();

    public UrlRateLimitRule() {}

    public UrlRateLimitRule(RuleConfig ruleConfig) {
        //TODO

    }

    public ApiLimit getLimit(String appId, String urlPath) throws InvalidUrlException{
        if (StringUtils.isEmpty(appId) || StringUtils.isEmpty(urlPath)) {
            return null;
        }

        AppUrlRateLimitRule appUrlRateLimitRule = limitRules.get(appId);
        if (appUrlRateLimitRule == null) {
            return null;
        }

        ApiLimit apiLimit = appUrlRateLimitRule.getLimitInfo(urlPath);
        return apiLimit;
    }

    @Override
    public void addLimit(String appId, ApiLimit apiLimit) throws InvalidUrlException {
    }

    @Override
    public void addLimits(String appId, List<ApiLimit> limits) throws InvalidUrlException {

    }

    @Override
    public void rebuildRule(UniformRuleConfigMapping uniformRuleConfigMapping) {

    }

    @Override
    public void addRule(UniformRuleConfigMapping uniformRuleConfigMapping) {

    }
}

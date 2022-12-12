package io.coolstar.ratelimiter.rule.datasource;

import io.coolstar.ratelimiter.rule.RuleConfig;

/**
 * @author chenxingxing
 * @date 2022/12/12 8:52 下午
 */
public interface RuleConfigSource {
    /**
     * 加载配置源
     */
    RuleConfig load();
}

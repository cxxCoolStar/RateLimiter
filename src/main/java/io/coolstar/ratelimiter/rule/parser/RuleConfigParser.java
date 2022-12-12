package io.coolstar.ratelimiter.rule.parser;

import io.coolstar.ratelimiter.rule.RuleConfig;

import java.io.InputStream;

/**
 * @author chenxingxing
 * @date 2022/12/12 8:50 下午
 */
public interface RuleConfigParser {

    /**
     * 根据配置路径解析
     * @param configText
     * @return
     */
    RuleConfig parse(String configText);

    /**
     * 根据输入流解析
     * @param in
     * @return
     */
    RuleConfig parse(InputStream in);
}

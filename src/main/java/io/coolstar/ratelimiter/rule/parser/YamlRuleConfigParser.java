package io.coolstar.ratelimiter.rule.parser;

import io.coolstar.ratelimiter.RateLimiter;
import io.coolstar.ratelimiter.rule.RuleConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author chenxingxing
 * @date 2022/12/12 8:55 下午
 */
public class YamlRuleConfigParser implements RuleConfigParser {

    private static final Logger log = LoggerFactory.getLogger(YamlRuleConfigParser.class);

    @Override
    public RuleConfig parse(String configText) {
        return null;
    }

    @Override
    public RuleConfig parse(InputStream in) {
        RuleConfig ruleConfig = null;
        try {
            if (in != null) {
                Yaml yaml = new Yaml();
                ruleConfig = yaml.loadAs(in, RuleConfig.class);
            }
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("close file error:{}", e);
                }
            }
        }
        return ruleConfig;
    }
}

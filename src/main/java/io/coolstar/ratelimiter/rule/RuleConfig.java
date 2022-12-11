package io.coolstar.ratelimiter.rule;

import java.util.List;

/**
 * @author chenxingxing
 * @date 2022/12/11 7:27 下午
 */

public class RuleConfig {
    private List<AppRuleConfig> configs;

    public List<AppRuleConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<AppRuleConfig> configs) {
        this.configs = configs;
    }

    public static class AppRuleConfig {
        private String appId;
        private List<ApiLimit> limits;

        public AppRuleConfig() {}

        public AppRuleConfig(String appId, List<ApiLimit> limits) {
            this.appId = appId;
            this.limits = limits;
        }
        //...省略getter、setter方法...
    }
}



package io.coolstar.ratelimiter.rule;

import lombok.Data;

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

        public AppRuleConfig() {
        }

        public AppRuleConfig(String appId, List<ApiLimit> limits) {
            this.appId = appId;
            this.limits = limits;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public List<ApiLimit> getLimits() {
            return limits;
        }

        public void setLimits(List<ApiLimit> limits) {
            this.limits = limits;
        }

        @Override
        public String toString() {
            return "AppRuleConfig{" +
                    "appId='" + appId + '\'' +
                    ", limits=" + limits +
                    '}';
        }
    }
}



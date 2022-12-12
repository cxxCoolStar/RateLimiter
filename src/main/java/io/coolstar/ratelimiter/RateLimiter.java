package io.coolstar.ratelimiter;

import io.coolstar.ratelimiter.alg.FixedTimeRateLimitAlg;
import io.coolstar.ratelimiter.alg.RateLimitAlg;
import io.coolstar.ratelimiter.rule.ApiLimit;
import io.coolstar.ratelimiter.rule.RateLimitRule;
import io.coolstar.ratelimiter.rule.RuleConfig;
import io.coolstar.ratelimiter.rule.TrieRateLimitRule;
import io.coolstar.ratelimiter.rule.datasource.FileRuleConfigSource;
import io.coolstar.ratelimiter.rule.datasource.RuleConfigSource;
import org.apache.maven.InternalErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiter {
  private static final Logger log = LoggerFactory.getLogger(RateLimiter.class);
  // 为每个api在内存中存储限流计数器
  private ConcurrentHashMap<String, RateLimitAlg> counters = new ConcurrentHashMap<>();
  private RateLimitRule rule;

  public RateLimiter() {
    RuleConfigSource configSource = new FileRuleConfigSource();
    RuleConfig ruleConfig = configSource.load();
    this.rule = new TrieRateLimitRule(ruleConfig);
  }

  public boolean limit(String appId, String url) throws Exception {
    ApiLimit apiLimit = rule.getLimit(appId, url);
    if (apiLimit == null) {
      return true;
    }

    // 获取api对应在内存中的限流计数器（rateLimitCounter）
    String counterKey = appId + ":" + apiLimit.getApi();
    RateLimitAlg rateLimitCounter = counters.get(counterKey);
    if (rateLimitCounter == null) {
      RateLimitAlg newRateLimitCounter = new FixedTimeRateLimitAlg(apiLimit.getLimit());
      rateLimitCounter = counters.putIfAbsent(counterKey, newRateLimitCounter);
      if (rateLimitCounter == null) {
        rateLimitCounter = newRateLimitCounter;
      }
    }

    // 判断是否限流
    return rateLimitCounter.tryAcquire();
  }

  public static void main(String[] args) {
    RateLimiter rateLimiter = new RateLimiter();
  }
}
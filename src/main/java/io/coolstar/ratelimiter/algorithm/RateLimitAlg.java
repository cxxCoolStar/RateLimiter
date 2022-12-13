package io.coolstar.ratelimiter.algorithm;

/**
 * @author chenxingxing
 * @date 2022/12/12 9:08 下午
 */
public interface RateLimitAlg {

    /**
     * 尝试获取规则
     * @return
     * @throws Exception
     */
    boolean tryAcquire() throws Exception;
}

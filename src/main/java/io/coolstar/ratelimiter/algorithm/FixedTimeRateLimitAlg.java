package io.coolstar.ratelimiter.algorithm;

import com.google.common.base.Stopwatch;
import org.apache.maven.InternalErrorException;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenxingxing
 * @date 2022/12/11 7:28 下午
 */
public class FixedTimeRateLimitAlg implements RateLimitAlg{
    /* timeout for {@code Lock.tryLock() }. */
    private static final long TRY_LOCK_TIMEOUT = 200L;  // 200ms.
    private Stopwatch stopwatch;
    private AtomicInteger currentCount = new AtomicInteger(0);
    private final int limit;
    private Lock lock = new ReentrantLock();

    public FixedTimeRateLimitAlg(int limit) {
        this(limit, Stopwatch.createStarted());
    }

   // @VisibleForTesting
    protected FixedTimeRateLimitAlg(int limit, Stopwatch stopwatch) {
        this.limit = limit;
        this.stopwatch = stopwatch;
    }

    public boolean tryAcquire() throws Exception {
        int updatedCount = currentCount.incrementAndGet();
        if (updatedCount <= limit) {
            return true;
        }

        try {
            if (lock.tryLock(TRY_LOCK_TIMEOUT, TimeUnit.MILLISECONDS)) {
                try {
                    if (stopwatch.elapsed(TimeUnit.MILLISECONDS) > TimeUnit.SECONDS.toMillis(1)) {
                        currentCount.set(0);
                        stopwatch.reset().start();
                    }
                    updatedCount = currentCount.incrementAndGet();
                    return updatedCount <= limit;
                } finally {
                    lock.unlock();
                }
            } else {
                throw new Exception("tryAcquire() wait lock too long:" + TRY_LOCK_TIMEOUT + "ms");
            }
        } catch (InterruptedException e) {
            throw new InternalErrorException("tryAcquire() is interrupted by lock-time-out.", e);
        }
    }
}
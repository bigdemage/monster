package com.monster.spring.config;

import com.monster.spring.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.*;

@Slf4j
public class ThreadPoolConfig {
    /** 核心线程数 */
    public static final Integer CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    /** 最大线程数 */
    public static final Integer MAX_POOL_SIZE = (int) (CORE_POOL_SIZE / (1 - 0.9));

    /** 定时任务线程池，定时打印各线程池的运行情况 */
    @Bean(destroyMethod = "shutdown")
    public ExecutorService scheduledLogStatsThreadPool(
            Map<String, ThreadPoolExecutor> threadPoolExecutorMap) {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
        scheduledThreadPool.scheduleAtFixedRate(
                () -> {
                    threadPoolExecutorMap.forEach(
                            ((name, executor) ->
                                    log.info(
                                            "[定时监控线程池] {}, 核心线程数:{}, 最大线程数:{}, 当前线程数:{}, 活跃线程数:{}, 同时存在最大线程数:{}, 线程池任务总量:{}, 队列类型:{}, 队列容量:{}, 队列元素个数:{}, 队列剩余个数:{}",
                                            name,
                                            executor.getCorePoolSize(),
                                            executor.getMaximumPoolSize(),
                                            executor.getPoolSize(),
                                            executor.getActiveCount(),
                                            executor.getLargestPoolSize(),
                                            executor.getCompletedTaskCount(),
                                            executor.getQueue().getClass().getSimpleName(),
                                            executor.getQueue().size() + executor.getQueue().remainingCapacity(),
                                            executor.getQueue().size(),
                                            executor.getQueue().remainingCapacity())));
                },
                1,
                10,
                TimeUnit.SECONDS);
        return scheduledThreadPool;
    }

}

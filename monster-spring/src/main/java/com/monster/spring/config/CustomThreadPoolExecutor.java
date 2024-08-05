package com.monster.spring.config;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

@Slf4j
@Component
public class CustomThreadPoolExecutor implements AutoCloseable{

    private static final int DEFAULT_QUEUE_SIZE = 1024;
    private static final int DEFAULT_POOL_SIZE = 9 + 1;

    @Getter
    private int queueSize = DEFAULT_QUEUE_SIZE;

    @Getter
    private int poolSize = DEFAULT_POOL_SIZE;

    /**
     * 用于周期性监控业务线程池的运行状态
     */
    private final ScheduledExecutorService scheduledExecutorService = Executors
            .newSingleThreadScheduledExecutor(new BasicThreadFactory.Builder().namingPattern("biz-thread-executor-monitor").build());

    /**
     * 自定义异步线程池，把线程池类比为一个项目组，而线程就是项目组的成员
     * 1. 任务队列使用有界队列
     * 2. 自定义拒绝策略
     */
    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            // 线程池保有的最小线程数。有些项目很闲，但是也不能把人都撤了，至少要留 corePoolSize 个人坚守阵地。
            poolSize,
            // 线程池创建的最大线程数。当项目很忙时，就需要加人，但是也不能无限制地加，最多就加到 maximumPoolSize 个人。
            // 当项目闲下来时，就要撤人了，最多能撤到 corePoolSize 个人。
            poolSize,
            // 如果一个线程空闲了 keepAliveTime & unit 这么久，而且线程池的线程数大于 corePoolSize，那么这个空闲的线程就要被回收了。
            0, TimeUnit.MILLISECONDS,
            // LinkedBlockingQueue 为无界队列，高负载情境下，无界队列很容易导致 OOM，而 OOM 会导致所有请求都无法处理，这是致命问题。
            // 任务队列强烈建议使用有界队列
            new ArrayBlockingQueue<>(queueSize),
            // 自定义如何创建线程，例如可以给线程指定一个有意义的名字。
            new BasicThreadFactory.Builder().namingPattern("biz-thread-%d").build(),
            // 自定义拒绝策略
            ((r, executor) -> log.error("The async executor pool is full!"))
    );

    private final ExecutorService executorService = threadPoolExecutor;

    @PostConstruct
    public void initialize() {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            // 线程池需要执行的任务数
            final long taskCount = threadPoolExecutor.getTaskCount();
            // 线程池在运行过程中已完成的任务数
            final long completedTaskCount = threadPoolExecutor.getCompletedTaskCount();
            // 曾经创建过的最大线程数
            final int largestPoolSize = threadPoolExecutor.getLargestPoolSize();
            // 线程池里的线程数量
            final int poolSize = threadPoolExecutor.getPoolSize();
            // 线程池里活跃的线程数量
            final int activeCount = threadPoolExecutor.getActiveCount();

            log.info("biz-thread-executor-monitor: taskCount={}, completedTaskCount={}, largestPoolSize={}, poolSize={}, activeCount={}",
                    taskCount, completedTaskCount, largestPoolSize, poolSize, activeCount);
        }, 0, 10, TimeUnit.MINUTES);
    }

    public void execute(Runnable task) {
        executorService.execute(task);
    }

    @Override
    public void close() {
        executorService.shutdown();
        scheduledExecutorService.shutdown();
    }
}

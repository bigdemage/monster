package com.monster.ThreadPool;

import ch.qos.logback.core.util.TimeUtil;

import java.util.concurrent.*;

/**
 * @ClassName ThreadPoolUtil
 * @Deacription
 * @Author wrx
 * @Date 2021/12/17/017 18:03
 * @Version 1.0
 **/
public class ThreadPoolUtil {

    //机器的cpu个数
    private static final int cpuCount=Runtime.getRuntime().availableProcessors();


    /**
     *  自定义I/O密集型线程池
     *  核心线程数为cpu个数+1
     *  最大线程数为cpu个数的2倍
     *  有界队列的长度可以根据配置文件配置
     *  使用CallerRunsPolicy拒绝策略，保证只要jvm进程正常运行 ，任务一定能被执行到
     *
     */
    public ThreadPoolExecutor getIOInstance(){
        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(
                cpuCount + 1,
                cpuCount * 2,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(500),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread=new Thread(r,"wrx-wudi-thread-poll");
                        thread.setDaemon(true);
                        return thread;
                    }
                },
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        return threadPoolExecutor;
    }

}

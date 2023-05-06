package com.dx.mqttclient;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author 32825
 */
public class BlockedThreadPool {

    private ExecutorService executor;

    private Semaphore semaphore;

    /**
     * 接收两个参数，最大允许线程数，自定义线程名
     *
     * @param nThreads
     * @param name
     */
    private BlockedThreadPool(int nThreads, String name) {
        if (nThreads <= 0) {
            throw new IllegalArgumentException();
        }
        semaphore = new Semaphore(nThreads);
        executor = Executors.newCachedThreadPool(r -> {
            Thread t = new Thread(r);
            t.setName(name + UUID.randomUUID().toString());
            return t;
        });
    }

    /**
     * 提供工厂方法
     *
     * @param nThread
     * @param name
     */
    public static BlockedThreadPool createBlockedThreadPool(int nThread, String name) {
        return new BlockedThreadPool(nThread, name);
    }

    /**
     * 向线程池提交任务
     * @param r
     */
    public void submit(Runnable r) {
        executor.submit(() -> {
            try {
                semaphore.acquire();
                r.run();
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}

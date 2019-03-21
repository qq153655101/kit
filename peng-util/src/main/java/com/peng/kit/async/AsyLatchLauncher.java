package com.peng.kit.async;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * 利用闭锁协调一组线程处理任务
 * 注意：对于处理用一个对象（getOriginal，result）的同一个成员变量可能存在线程不安全
 * created by guoqingpeng on 2019/3/20
 */
@Slf4j
public class AsyLatchLauncher<T> {
    private Executor executor;
    private int timeOut;
    private T reslut;
    private boolean allFinished = true;
    private List<Consumer<T>> consumers = new ArrayList<>();


    public void execute(){
        if (consumers.size()==0)
            throw new RuntimeException("AsyLatchLauncher ： there is no runnable job !");
        CountDownLatch latch = new CountDownLatch(consumers.size());
        for (Consumer<T> consumer : consumers){
            executor.execute(()->{
                try{
                    consumer.accept(reslut);
                }catch (Exception e){
                    allFinished = false;
                    log.error("AsyLatchLauncher  execute runnable error:{}",e);
                }finally {
                    latch.countDown();
                }
            });
        }

        try{
            boolean allSuccess = latch.await(timeOut, TimeUnit.SECONDS);
            if (!allSuccess){
                allFinished = false;
                log.error("AsyLatchLauncher : there has unfinish job ! ");
            }
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    public AsyLatchLauncher addRunnable(Consumer<T> c){
        consumers.add(c);
        return this;
    }

    public void clear(){
        consumers.clear();
    }

    public boolean isAllFinished() {
        return allFinished;
    }

    public T getReslut() {
        return reslut;
    }

    public AsyLatchLauncher setExecutor(Executor executor) {
        this.executor = executor;
        return this;
    }

    public AsyLatchLauncher setTimeOut(int timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    private AsyLatchLauncher() { }

    public AsyLatchLauncher(Executor executor, int timeOut) {
        this.executor = executor;
        this.timeOut = timeOut;
    }
}

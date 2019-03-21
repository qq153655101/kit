package com.peng.kit.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 利用栅栏协调一组线程处理任务
 * 注意：对于处理用一个对象（getOriginal，result）的同一个成员变量可能存在线程不安全
 * created by guoqingpeng on 2019/3/20
 */
@Slf4j
public class AsyBarrierLauncher<T,R> {
    private Executor executor;
    private long timeOut;
    private boolean allFinished = true;
    private CyclicBarrier cyclicBarrier;
    private Consumer<R> mergeConsumer;
    private Function<T,R> resultFunction;
    private List<Consumer<T>> consumers = new ArrayList<>();
    private T original;
    private R result;


    public AsyBarrierLauncher(Executor executor, long timeOut) {
        this.executor = executor;
        this.timeOut = timeOut;
    }

    /**
     * 使用CyclicBarrier协调一组线程处理任务，若处理的是同一个对象数据可用getOriginal获取结果
     */
    public void doTask(){
        if (consumers == null || resultFunction ==null)
            throw new RuntimeException("resultFunction or runnable list is null");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("AsyBarrierLauncher doTaskW");
        cyclicBarrier = new CyclicBarrier(consumers.size()+1);
        this.execute();
        this.waitToFinish();
        stopWatch.stop();
        this.stopWatchLog(stopWatch);
    }

    /**
     * 使用CyclicBarrier协调一组线程处理对象original，并经过mergeConsumer最终转换成result
     */
    public void doTaskWithMerge(){
        if  (consumers == null || mergeConsumer ==null)
            throw new RuntimeException("mergeConsumer or runnable list is null");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("AsyBarrierLauncher doTaskWithMerge");
        cyclicBarrier = new CyclicBarrier(consumers.size()+1,()->mergeConsumer.accept(result));
        this.execute();
        this.waitToFinish();
        stopWatch.stop();
        this.stopWatchLog(stopWatch);
    }

    /**
     * 使用CyclicBarrier协调一组线程处理对象original，并经过resultFunction最终转换成result
     */
    public void doTaskWithFunction(){
        if  (consumers == null)
            throw new RuntimeException("runnable list is null");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("AsyBarrierLauncher doTaskWithFunction");
        cyclicBarrier = new CyclicBarrier(consumers.size()+1);
        this.execute();
        this.waitToFinish();
        this.result = resultFunction.apply(original);
        stopWatch.stop();
        this.stopWatchLog(stopWatch);
    }

    public void execute(){
        for (Consumer<T> consumer:consumers){
            executor.execute(()->{
                try{
                    consumer.accept(original);
                    cyclicBarrier.await(timeOut,TimeUnit.SECONDS);
                }catch (Exception e){
                    allFinished = false;
                    log.error("AsyBarrierLauncher execute runnable error :{}",e);
                }
            });
        }
    }


    public void waitToFinish(){
        try{
            cyclicBarrier.await(timeOut, TimeUnit.SECONDS);
        }catch (Exception e){
            allFinished = false;
            log.error("AsyBarrierLauncher execute await for main thread error :{}",e);
        }
    }

    public AsyBarrierLauncher addRunnable(Consumer<T> c){
        consumers.add(c);
        return this;
    }

    public void clear(){
        consumers.clear();
    }

    public void stopWatchLog(StopWatch stopWatch){
        log.info(stopWatch.prettyPrint());
    }

    public boolean isAllFinished() {
        return allFinished;
    }

    public T getOriginal() {
        return original;
    }

    public R getResult() {
        return result;
    }

    //对外不提供默认构造方法
    private AsyBarrierLauncher() { }

    public AsyBarrierLauncher setExecutor(Executor executor) {
        this.executor = executor;
        return this;
    }

    public AsyBarrierLauncher setTimeOut(long timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    public AsyBarrierLauncher setMergeConsumer(Consumer<R> mergeConsumer) {
        this.mergeConsumer = mergeConsumer;
        return this;
    }

    public AsyBarrierLauncher setResultFunction(Function<T, R> resultFunction) {
        this.resultFunction = resultFunction;
        return this;
    }

    public AsyBarrierLauncher setResult(R result) {
        this.result = result;
        return this;
    }
}

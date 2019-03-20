package com.peng.kit.async;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 *
 * created by guoqingpeng on 2019/3/20
 */
@Slf4j
public class AsyBarrierLauncher<T,R> {
    private Executor executor;
    private long timeOut;
    private CyclicBarrier cyclicBarrier;
    private Consumer<R> mergeConsumer;
    private Function<T,R> resultFunction;
    private List<Consumer<T>> consumers = new ArrayList<>();
    private T original;
    private R result;

    public void doTaskW(){
        cyclicBarrier = new CyclicBarrier(consumers.size()+1);
        this.execute();
        this.waitToFinish();
    }

    public void doTaskWithMerge(){
        cyclicBarrier = new CyclicBarrier(consumers.size()+1,()->mergeConsumer.accept(result));
        this.execute();
        this.waitToFinish();
    }

    public void doTaskWithFunction(){
        cyclicBarrier = new CyclicBarrier(consumers.size()+1);
        this.execute();
        this.waitToFinish();
        this.result = resultFunction.apply(original);
    }

    public void execute(){
        for (Consumer<T> consumer:consumers){
            executor.execute(()->{
                try{
                    consumer.accept(original);
                    cyclicBarrier.await(timeOut,TimeUnit.SECONDS);
                }catch (Exception e){
                    log.error("AsyBarrierLauncher execute runnable error :{}",e);
                }
            });
        }
    }


    public void waitToFinish(){
        try{
            cyclicBarrier.await(timeOut, TimeUnit.SECONDS);
        }catch (Exception e){
            log.error("AsyBarrierLauncher execute await for main thread error :{}",e);
        }
    }

    public AsyBarrierLauncher addRunnable(Consumer<T> c){
        consumers.add(c);
        return this;
    }

    public T getOriginal() {
        return original;
    }

    public R getResult() {
        return result;
    }

    public AsyBarrierLauncher() {
    }

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

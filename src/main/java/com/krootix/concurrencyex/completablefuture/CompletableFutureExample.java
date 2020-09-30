package com.krootix.concurrencyex.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class CompletableFutureExample {

    private AtomicInteger someStateVariable = new AtomicInteger();

    private void process() {
        System.out.println(Thread.currentThread() + " Process");
        someStateVariable.set(100);
    }

    private int processSomeData() {
        System.out.println(Thread.currentThread() + " Process some data");
        return someStateVariable.incrementAndGet();
    }

    private int processWithoutPrint() {
        System.out.println(Thread.currentThread() + " Process some data");
        return someStateVariable.incrementAndGet();
    }

    private void notify(Integer integer) {
        System.out.println("notify " + someStateVariable.get());
    }

    public void completableFutureRunAsync() {
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(this::process);
        runAsync.join();
        System.out.println("Completable Future Run Async " + someStateVariable.get());
    }

    public void completableFutureSupplyAsync() {
        CompletableFuture<Integer> supplyAsync = CompletableFuture.supplyAsync(this::processSomeData);
        try {
            System.out.println("Completable Future Supply Async " + supplyAsync.get()); //Blocking
        } catch (ExecutionException | InterruptedException e) {
            System.out.println("No Exception expected");
        }
    }

    public void completableFutureSupplyAsyncWithExecutor() {
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(2);
        CompletableFuture<Integer> supplyAsync = CompletableFuture.supplyAsync(this::processSomeData, newFixedThreadPool);
        try {
            System.out.println("Completable Future Supply Async With Executor " + supplyAsync.get());
            newFixedThreadPool.shutdown();
        } catch (ExecutionException | InterruptedException e) {
            System.out.println("No Exception expected");
        } finally {
            newFixedThreadPool.shutdown();
        }
    }

    public void completableFutureThenAccept() {
        CompletableFuture.supplyAsync(this::processSomeData)
                .thenAccept(this::notify) //Non Blocking,notify method will be called automatically after compilation or process method
                .join();
//        assertEquals(100,someStateVaribale.get());
    }

}

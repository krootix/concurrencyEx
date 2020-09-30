package com.krootix.concurrencyex;

//import com.krootix.concurrencyex.completablefuture.CompletableFutureExample;

import com.krootix.concurrencyex.control.*;
import com.krootix.concurrencyex.cyclicbarier.CyclicBarrierExample;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class Main {

    final static String REGEX = "\\{\\d+}";

    private static String uriPath;

    public static void main(String[] args) throws InterruptedException, ExecutionException {

//  receive amount of available processors
//        Runtime.getRuntime().availableProcessors();

//        long[] s = new long[Integer.MAX_VALUE];

//        List<Object> objectList = new ArrayList<>();

//        while (true){
//            objectList.add(new Object());
//        }

//        CompletableFutureExample completableFutureExample = new CompletableFutureExample();
//        completableFutureExample.completableFutureRunAsync();
//        completableFutureExample.completableFutureSupplyAsync();
//        completableFutureExample.completableFutureSupplyAsyncWithExecutor();
//        completableFutureExample.completableFutureThenAccept();

//        List<Thread> runnables =
//                Arrays.asList(
//                        new Thread(() -> new ExtendThreadExample().execute()),
//                        new Thread(() -> new ImplementRunnableExample().execute()),
//                        new Thread(() -> new NonSynchThreadExample().execute()),
//                        new Thread(() -> new SynchThreadExample().execute()),
//                        new Thread(() -> new ThreadControlExample().execute()),
//                        new Thread(() -> new ThreadInteractionExample().execute()),
//                        new Thread(() -> new NonDeadLockExample().execute()),
//                        new Thread(() -> new BlockingQueueExample().execute()),
//                        new Thread(() -> new ReentrantLockExample().execute()),
//                        new Thread(() -> new SemaphoreExample().execute())
//                );
//
//        for (Thread action : runnables) {
//            action.start();
//            System.out.println(action.getClass().getSimpleName() + " executed");
//        }

        // don't uncomment all of that Examples, if you don't want to get a
        // chaos from all these threads
        // believe me, it's not worth it
        List<Example> examples =
                Arrays.asList(
                        new ExtendThreadExample()
//                        new ImplementRunnableExample(),
//                        new NonSynchThreadExample(),
//                        new SynchThreadExample(),
//                        new ThreadControlExample(),
//                        new ThreadInteractionExample(),
//                        new DeadLockExample(),              // deadlock
//                        new NonDeadLockExample(),
//                        new ThreadWaitingForItselfExample(), // deadlock
//                        new BlockingQueueExample(),
//                        new ReentrantLockExample(),
//                        new SemaphoreExample(),
//                        new CompletableFutureExample(),
//                        new SynchronizedWithCountDownLatchExample(),
//                        new BuffersExample(),
//                        new CyclicBarrierExample()
                );

        for (Example action : examples) {
            action.execute();
            System.out.println(action.getClass().getSimpleName() + " executed");
        }

//        new Semaphore(0).acquire();
//        uriPath = "ankey/managed/approle/({0})";
//        System.out.println(getReplacedString("56"));
//        System.out.println(getReplacedStringFormat(56));
//        System.out.println(getReplacedStringMessageFormat(56));
//        System.out.println(msgFormat(uriPath, 56));
    }


    static String getReplacedString(String param) {
        return uriPath.replaceAll(REGEX, param);
    }

    static String getReplacedStringFormat(int param) {
        return String.format(uriPath, param);
    }

    static String getReplacedStringMessageFormat(int param) {
        return MessageFormat.format(uriPath, param);
    }

    public static String msgFormat(String s, Object... args) {
        return new MessageFormat(s).format(args);
    }
}
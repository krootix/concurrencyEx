package com.krootix.concurrencyex.reentrantlock.conditions;

import java.util.concurrent.CountDownLatch;

public class SynchronizedWithCountDownLatch {

    private static final byte AMOUNT_OF_THREADS = 4;

    public void run() throws InterruptedException {

        Thread[] threadArray = new Thread[AMOUNT_OF_THREADS];
        final Object lock = new Object();
        final CountDownLatch latch = new CountDownLatch(threadArray.length);

        for (int i = 0; i < threadArray.length; i++) {
            threadArray[i] = new Thread(() -> {
                synchronized (lock) {
                    latch.countDown();
                    try {
                        System.out.println(Thread.currentThread().getName() + " "
                                + Thread.currentThread().getState());
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new Error("Never");
                    }
                }
            });
            threadArray[i].start();
        }

        latch.await();
        synchronized (lock) {
            for (Thread thread : threadArray) System.out.println(thread.getName() + " " + thread.getState());
            lock.notifyAll();
            for (Thread thread : threadArray) System.out.println(thread.getName() + " " + thread.getState());
        }
    }
}


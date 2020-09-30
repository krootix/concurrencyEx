package com.krootix.concurrencyex.synchronization.with;

import com.krootix.concurrencyex.control.Counter;

public class SynchThread extends Thread {
    /**
     * Using synchronized makes a method / block accessible by only on thread at a time.
     * it's thread-safe.
     * 1. mutual exclusion. (взаимное исключение)
     * 2. visibility (видимость)
     */

    private Thread thread;
    private String threadName;
    private final Counter counter;

    public SynchThread(String threadName, Counter counter) {
        this.threadName = threadName;
        this.counter = counter;
    }

    @Override
    public void run() {
        System.out.println("Thread " + threadName + " is running...");
        synchronized (counter) {
            counter.displayCounter();
        }
        System.out.println("Leaving " + threadName + " thread...");
    }

    @Override
    public synchronized void start() {
        System.out.println("Thread " + threadName + " successfully started.");
        if (thread == null) {
            thread = new Thread(this, threadName);
            thread.start();
        }
    }
}
package com.krootix.concurrencyex.threads;

import static com.krootix.concurrencyex.control.Example.TIME_TO_SLEEP;

public class ThreadByExtendingThread extends Thread {

    private Thread thread;
    private String threadName;

    public ThreadByExtendingThread(String threadName) {
        this.threadName = threadName;
        System.out.println("ThreadByExtendingThread " + threadName + " created successfully.");
    }

    @Override
    public void run() {
        System.out.println("Thread " + threadName + " is running...");

        try {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Thread " + threadName + " " + i);
                Thread.sleep(TIME_TO_SLEEP);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread " + threadName + " is interrupted.");
            e.printStackTrace();
        }
        System.out.println("Leaving thread " + threadName);
    }

    @Override
    public synchronized void start() {
        System.out.println("Thread " + threadName + " is started successfully.");
        if (thread == null) {
            thread = new Thread(this, threadName);
            thread.start();
        }
    }
}

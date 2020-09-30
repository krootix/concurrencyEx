package com.krootix.concurrencyex.control;

import com.krootix.concurrencyex.threadcontrol.ThreadControl;

// thread extends Thread

public class ThreadControlExample implements Example {

    @Override
    public void execute() {

        // basic threading example
        ThreadControl threadControlOne = new ThreadControl("Thread One");
        threadControlOne.start();

        ThreadControl threadControlTwo = new ThreadControl("Thread Two");
        threadControlTwo.start();

        try {
            Thread.sleep(SLEEP_TIME_1_SEC);
            threadControlOne.suspend();
            System.out.println("Thread One suspended...");
            Thread.sleep(SLEEP_TIME_1_SEC);
            threadControlOne.resume();
            System.out.println("Thread One activated");
            threadControlTwo.suspend();
            System.out.println("Thread Two suspended...");
            Thread.sleep(SLEEP_TIME_1_SEC);
            threadControlTwo.resume();
            System.out.println("Thread Two activated");
        } catch (InterruptedException e) {
            System.out.println("Main thread is interrupted... " + e);
            Thread.currentThread().interrupt();
        }
        try {
            System.out.println("Standing by for finishing threads.");
            threadControlOne.thread.join();
            threadControlTwo.thread.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread is interrupted... " + e);
            Thread.currentThread().interrupt();
        }
        System.out.println("Leaving main thread...");
    }
}
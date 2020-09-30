package com.krootix.concurrencyex.control;

import com.krootix.concurrencyex.threads.ThreadByImplementingRunnable;

// thread implements Runnable

public class ImplementRunnableExample implements Example {

    @Override
    public void execute() {
        ThreadByImplementingRunnable threadByImplementingRunnableOne = new ThreadByImplementingRunnable("ThreadOne");
        ThreadByImplementingRunnable threadByImplementingRunnableTwo = new ThreadByImplementingRunnable("ThreadTwo");

        threadByImplementingRunnableOne.start();
        threadByImplementingRunnableTwo.start();

        // example of anonymous implementation of Runnable
        Runnable myRunnable =
                new Runnable() {
                    public void run() {
                        System.out.println("anonymous implementation of runnable "
                                + Thread.currentThread().getName()
                                + " running");
                    }
                };

        // example of lambda implementation of Runnable
        Thread myRunnableThread = new Thread(myRunnable);
        myRunnableThread.start();

        Runnable lambdaRunnable=
                () -> System.out.println("lambda implementation of runnable "
                        + Thread.currentThread().getName()
                        + " running");
        Thread lambdaRunnableThread = new Thread(lambdaRunnable);
        lambdaRunnableThread.start();
    }
}
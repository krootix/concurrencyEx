package com.krootix.concurrencyex.control;

import com.krootix.concurrencyex.threads.ThreadByExtendingThread;

// thread extends Thread

public class ExtendThreadExample implements Example {

    @Override
    public void execute() throws InterruptedException {
        ThreadByExtendingThread threadByExtendingThreadOne = new ThreadByExtendingThread("thread By Extending Thread One");
        ThreadByExtendingThread threadByExtendingThreadTwo = new ThreadByExtendingThread("thread By Extending Thread Two");

        threadByExtendingThreadOne.start();
        threadByExtendingThreadTwo.start();
    }
}

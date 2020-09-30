package com.krootix.concurrencyex.control;

import com.krootix.concurrencyex.reentrantlock.conditions.SynchronizedWithCountDownLatch;

public class SynchronizedWithCountDownLatchExample implements Example {

    @Override
    public void execute() throws InterruptedException {

        SynchronizedWithCountDownLatch synchronizedWithCountDownLatch = new SynchronizedWithCountDownLatch();

        synchronizedWithCountDownLatch.run();
    }
}
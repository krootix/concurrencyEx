package com.krootix.concurrencyex.reentrantlock;

import java.util.concurrent.atomic.AtomicInteger;

public class Resource {

    private final AtomicInteger count = new AtomicInteger();

    public AtomicInteger getCount() {
        return count;
    }

    public void doSomething() {
        //do some operation, DB read, write etc
        System.out.println(Thread.currentThread().getName() + " doing something with "
                + this.getClass().getSimpleName());
    }

    public void doLogging() {
        //logging, no need for thread safety
        System.out.println("some logging from "
                + this.getClass().getSimpleName());
    }

    public int incrementAndGetCount() {
        return count.incrementAndGet();
    }
}

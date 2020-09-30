package com.krootix.concurrencyex.semaphore;

import com.krootix.concurrencyex.reentrantlock.Resource;

import java.util.concurrent.Semaphore;

import static com.krootix.concurrencyex.control.Example.TIME_TO_SLEEP;

public class CountThread implements Runnable {

    private static final int FROM = 0;
    private static final int TO = 5;

    private Resource resource;
    private Semaphore semaphore;
    private String name;

    public CountThread(Resource resource, Semaphore semaphore, String name) {
        this.resource = resource;
        this.semaphore = semaphore;
        this.name = name;
    }

    public void run() {

        try {
            System.out.println(name + " pending permission");
            semaphore.acquire();
            resource.getCount().set(1);
            for (int i = FROM; i < TO; i++) {
                System.out.println(this.name + ": " + resource.getCount().intValue());
                resource.getCount().incrementAndGet();
                Thread.sleep(TIME_TO_SLEEP);
            }

        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(name + " releases permission");
        semaphore.release();
    }
}
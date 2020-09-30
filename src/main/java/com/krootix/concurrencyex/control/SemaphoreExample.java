package com.krootix.concurrencyex.control;

import com.krootix.concurrencyex.reentrantlock.Resource;
import com.krootix.concurrencyex.reentrantlock.ResourceCreator;
import com.krootix.concurrencyex.semaphore.CountThread;

import java.util.concurrent.Semaphore;

// Thread interaction example

public class SemaphoreExample implements Example {

    @Override
    public void execute() {

        Semaphore semaphore = new Semaphore(1);

        Resource resource = ResourceCreator.create();

        new Thread(new CountThread(resource, semaphore, "CountThread 1")).start();
        new Thread(new CountThread(resource, semaphore, "CountThread 2")).start();
        new Thread(new CountThread(resource, semaphore, "CountThread 3")).start();

    }
}
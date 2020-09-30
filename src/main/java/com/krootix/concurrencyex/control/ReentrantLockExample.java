package com.krootix.concurrencyex.control;

import com.krootix.concurrencyex.reentrantlock.ConcurrencyLock;
import com.krootix.concurrencyex.reentrantlock.Resource;
import com.krootix.concurrencyex.reentrantlock.ResourceCreator;
import com.krootix.concurrencyex.reentrantlock.SynchronizedLock;

// Thread waiting for itself deadlock

public class ReentrantLockExample implements Example {

    @Override
    public void execute() {

        Resource resource = ResourceCreator.create();

        SynchronizedLock synchronizedLock = new SynchronizedLock(resource);
        Thread synchronizedLockThread = new Thread(synchronizedLock);
        synchronizedLockThread.start();

        ConcurrencyLock concurrencyLock = new ConcurrencyLock(resource);
        Thread concurrencyLockThread = new Thread(concurrencyLock);
        concurrencyLockThread.start();
    }
}
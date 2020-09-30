package com.krootix.concurrencyex.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrencyLock implements Runnable{

    private Resource resource;
    private Lock lock;

    public ConcurrencyLock(Resource resource){
        this.resource = resource;
        this.lock = new ReentrantLock();
    }

    @Override
    public void run() {
        try {
            if(lock.tryLock(10, TimeUnit.SECONDS)){
                resource.doSomething();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            //release lock
            lock.unlock();
        }
        resource.doLogging();
    }
}
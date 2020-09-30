package com.krootix.concurrencyex.reentrantlock;

public class SynchronizedLock implements Runnable{

    private final Resource resource;

    public SynchronizedLock(Resource r){
        this.resource = r;
    }

    @Override
    public void run() {
        synchronized (resource) {
            resource.doSomething();
        }
        resource.doLogging();
    }
}
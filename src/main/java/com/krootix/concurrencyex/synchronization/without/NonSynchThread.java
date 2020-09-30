package com.krootix.concurrencyex.synchronization.without;

import com.krootix.concurrencyex.control.Counter;

public class NonSynchThread extends Thread{
    private Thread thread;
    private String threadName;
    private Counter counter;

    public NonSynchThread(String threadName, Counter counter) {
        this.threadName = threadName;
        this.counter = counter;
    }

    @Override
    public void run(){
        System.out.println("Thread " + threadName + " is running...");
        counter.displayCounter();
        System.out.println("Leaving " + threadName + " thread...");
    }

    @Override
    public synchronized void start(){
        System.out.println("Thread " + threadName + " is started.");
        if(thread == null){
            thread = new Thread(this,threadName);
            thread.start();
        }
    }
}
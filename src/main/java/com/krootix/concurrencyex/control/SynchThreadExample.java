package com.krootix.concurrencyex.control;

import com.krootix.concurrencyex.synchronization.with.SynchThread;

// thread extends Thread

public class SynchThreadExample implements Example {

    @Override
    public void execute() {

        // shared object Counter
        Counter counter = CounterCreator.create();
        SynchThread synchThreadOne = new SynchThread("Synchronized Thread One", counter);
        SynchThread synchThreadTwo = new SynchThread("Synchronized Thread Two", counter);

        synchThreadOne.start();
        synchThreadTwo.start();

        try {
            synchThreadOne.join();
            synchThreadTwo.join();
        } catch (InterruptedException e) {
            System.out.println("Threads interrupted." + e);
            Thread.currentThread().interrupt();
        }
    }
}
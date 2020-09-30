package com.krootix.concurrencyex.control;

import com.krootix.concurrencyex.synchronization.without.NonSynchThread;

// thread extends Thread

public class NonSynchThreadExample implements Example {

    @Override
    public void execute() {

        // shared object Counter
        Counter counter = CounterCreator.create();

        NonSynchThread simpleThreadOne = new NonSynchThread("SimpleThread One", counter);
        NonSynchThread simpleThreadTwo = new NonSynchThread("SimpleThread Two", counter);

        simpleThreadOne.start();
        simpleThreadTwo.start();

        try {
            simpleThreadOne.join();
            simpleThreadTwo.join();
        } catch (InterruptedException e) {
            System.out.println("Threads interrupted." + e);
            Thread.currentThread().interrupt();
        }
    }
}

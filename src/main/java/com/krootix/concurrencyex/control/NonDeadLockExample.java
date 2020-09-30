package com.krootix.concurrencyex.control;

import com.krootix.concurrencyex.deadlock.NonDeadThreadOne;
import com.krootix.concurrencyex.deadlock.NonDeadThreadTwo;

// Thread interaction example

/**
 * Tips to prevent deadlocks in java
 * To Do
 */
public class NonDeadLockExample implements Example {

    public static final Object Lock1 = new Object();
    public static final Object Lock2 = new Object();

    @Override
    public void execute() {
        NonDeadThreadOne nonDeadThreadOne = new NonDeadThreadOne();
        NonDeadThreadTwo nonDeadThreadTwo = new NonDeadThreadTwo();

        nonDeadThreadOne.start();
        nonDeadThreadTwo.start();

        try {
            nonDeadThreadOne.join();
            nonDeadThreadTwo.join();
            System.out.println("Threads " + nonDeadThreadOne.getClass().getSimpleName()
                    + " and " + nonDeadThreadTwo.getClass().getSimpleName() + " ended.");
        } catch (InterruptedException e) {
            System.out.println("Threads interrupted. " + e);
            Thread.currentThread().interrupt();
        }
    }
}
package com.krootix.concurrencyex.control;

import com.krootix.concurrencyex.deadlock.DeadThreadOne;
import com.krootix.concurrencyex.deadlock.DeadThreadTwo;

// Thread interaction example

public class DeadLockExample implements Example {

    public static final Object Lock1 = new Object();
    public static final Object Lock2 = new Object();

    @Override
    public void execute() {
        DeadThreadOne threadOne = new DeadThreadOne();
        DeadThreadTwo threadTwo = new DeadThreadTwo();

        threadOne.start();
        threadTwo.start();
    }
}
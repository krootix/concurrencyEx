package com.krootix.concurrencyex.control;

import com.krootix.concurrencyex.deadlock.ThreadWaitingForItself;

// Thread waiting for itself deadlock

public class ThreadWaitingForItselfExample implements Example {

    @Override
    public void execute() {
        ThreadWaitingForItself.deadlock();
    }
}

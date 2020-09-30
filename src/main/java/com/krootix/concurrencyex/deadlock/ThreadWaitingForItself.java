package com.krootix.concurrencyex.deadlock;

public class ThreadWaitingForItself {

    public static synchronized void deadlock() {
        try {
            Thread t = new Thread(ThreadWaitingForItself::deadlock);
            t.start();
            t.join();
        } catch (Exception ex) {
        }
    }
}
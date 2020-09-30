package com.krootix.concurrencyex.deadlock;

import static com.krootix.concurrencyex.control.NonDeadLockExample.Lock1;
import static com.krootix.concurrencyex.control.NonDeadLockExample.Lock2;

public class NonDeadThreadOne extends Thread {

    @Override
    public void run() {
        synchronized (Lock2) {
            System.out.println("ThreadTwo is holding lock 1...");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ThreadOne is waiting for lock 2......");
            synchronized (Lock1) {
                System.out.println("ThreadTwo is holding lock 1 & 2...");
            }
        }
    }
}
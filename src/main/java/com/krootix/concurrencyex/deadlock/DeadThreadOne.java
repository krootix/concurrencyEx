package com.krootix.concurrencyex.deadlock;

import static com.krootix.concurrencyex.control.DeadLockExample.Lock1;
import static com.krootix.concurrencyex.control.DeadLockExample.Lock2;

public class DeadThreadOne extends Thread {

    private static final int SLEEP_TIME_1_SEC = 1000;

    @Override
    public void run() {
        synchronized (Lock1) {
            System.out.println("DeadThreadOne is holding LOCK 1...");
            try {
                Thread.sleep(SLEEP_TIME_1_SEC);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("DeadThreadOne is waiting for Lock 2...");
            synchronized (Lock2) {
                System.out.println("DeadThreadOne  is holding Lock 1 and Lock 2...");
            }
        }
    }
}
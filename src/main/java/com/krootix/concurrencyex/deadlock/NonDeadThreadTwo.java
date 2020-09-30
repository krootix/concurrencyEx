package com.krootix.concurrencyex.deadlock;

import static com.krootix.concurrencyex.control.DeadLockExample.Lock1;
import static com.krootix.concurrencyex.control.DeadLockExample.Lock2;
import static com.krootix.concurrencyex.control.Example.SLEEP_TIME_1_SEC;

public class NonDeadThreadTwo extends Thread {

    @Override
    public void run() {
        synchronized (Lock2) {
            System.out.println("DeadThreadTwo is holding lock 2...");
            try {
                Thread.sleep(SLEEP_TIME_1_SEC);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("DeadThreadOne is waiting for Lock 1...");
            synchronized (Lock1) {
                System.out.println("DeadThreadOne  is holding Lock 1 and Lock 2...");
            }
        }
    }
}
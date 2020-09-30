package com.krootix.concurrencyex.threadcontrol;

import static com.krootix.concurrencyex.control.Example.TIME_TO_SLEEP;
import static java.lang.Thread.sleep;

/**
 * wait-notify are communication mechanisms that threads use to communicate
 * among themselves of any condition/state changes.
 */
public class ThreadControl implements Runnable {

    public Thread thread;
    private String threadName;
    boolean suspended = false;

    public ThreadControl(String name) {
        threadName = name;
        System.out.println("Thread " + threadName + " successfully created.");
    }

    @Override
    public void run() {
        System.out.println("Thread " + threadName + " is running...");
        try {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Thread " + threadName + " " + i);

                sleep(TIME_TO_SLEEP);
                synchronized (this) {
                    while (suspended) {
                        wait();
                    }
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Thread " + threadName + " is interrupted.");
        }
        System.out.println("Leaving thread " + threadName + "...");
    }

    public void start() {
        System.out.println("Starting " + threadName + " thread...");
        if (thread == null) {
            thread = new Thread(this, threadName);
            thread.start();
        }
    }

    public void suspend() {
        suspended = true;
    }

    public synchronized void resume() {
        suspended = false;

        // let's not do this, ok?
        //
        notify();
//        notifyAll();
    }
}

package com.krootix.concurrencyex.examples.queue;

import java.util.concurrent.BlockingQueue;

public class WorkerThread extends Thread {

    private final BlockingQueue<Runnable> queue;

    public WorkerThread(BlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!queue.isEmpty()) {
            try {
                System.out.println("queue.size: " + queue.size());
                Runnable task = queue.take();
                // if you want to create each task in new thread
//                new Thread(task).start();
                task.run();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                Thread.currentThread().interrupt();
                break; /* Allow thread to exit */
            }
        }
    }
}
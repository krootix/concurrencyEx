package com.krootix.concurrencyex.examples.queue;

import java.util.concurrent.BlockingQueue;

public class BlockingQueueConsumer implements Runnable{

    protected BlockingQueue<String> queue = null;

    public BlockingQueueConsumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            System.out.println(queue.take());
            System.out.println(queue.take());
            System.out.println(queue.take());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
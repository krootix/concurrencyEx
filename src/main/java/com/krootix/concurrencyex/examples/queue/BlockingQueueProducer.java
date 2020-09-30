package com.krootix.concurrencyex.examples.queue;

import java.util.concurrent.BlockingQueue;

public class BlockingQueueProducer implements Runnable{

    protected BlockingQueue<String> queue = null;

    public BlockingQueueProducer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            queue.put("1");
            Thread.sleep(1000);
            queue.put("2");
            Thread.sleep(1000);
            queue.put("3");
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
package com.krootix.concurrencyex.control;

import com.krootix.concurrencyex.examples.queue.BlockingQueueConsumer;
import com.krootix.concurrencyex.examples.queue.BlockingQueueProducer;
import com.krootix.concurrencyex.examples.queue.WorkerThread;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

//  Array Blocking Queue example
//  you can initiate queue like this
//    BlockingQueue<String> unbounded = new LinkedBlockingQueue<String>();
//    BlockingQueue<String> bounded   = new LinkedBlockingQueue<String>(1024);
//  Some types of queue:
//    new ArrayBlockingQueue()
//    new LinkedBlockingQueue();
//    new LinkedBlockingDeque();
//    new PriorityBlockingQueue();
//    new SynchronousQueue();

/**
 * The Java BlockingQueue interface, java.util.concurrent.BlockingQueue, represents
 * a queue which is thread safe to put elements into, and take elements out of from.
 * In other words, multiple threads can be inserting and taking elements concurrently
 * from a Java BlockingQueue, without any concurrency issues arising.
 * <p>
 * The term blocking queue comes from the fact that the Java BlockingQueue is capable
 * of blocking the threads that try to insert or take elements from the queue. For
 * instance, if a thread tries to take an element and there are none left in the queue,
 * the thread can be blocked until there is an element to take. Whether or not the
 * calling thread is blocked depends on what methods you call on the BlockingQueue.
 * <p>
 * The LinkedBlockingQueue class implements the BlockingQueue interface. Read the
 * BlockingQueue text for more information about the interface.
 * <p>
 * The LinkedBlockingQueue keeps the elements internally in a linked structure
 * (linked nodes). This linked structure can optionally have an upper bound if desired.
 * If no upper bound is specified, Integer.MAX_VALUE is used as the upper bound.
 * <p>
 * The LinkedBlockingQueue stores the elements internally in FIFO (First In, First Out)
 * order. The head of the queue is the element which has been in queue the longest time,
 * and the tail of the queue is the element which has been in the queue the shortest time.
 * <p>
 * The PriorityBlockingQueue is an unbounded concurrent queue. It uses the same ordering
 * rules as the java.util.PriorityQueue class. You cannot insert null into this queue.
 * <p>
 * All elements inserted into the PriorityBlockingQueue must implement the
 * java.lang.Comparable interface. The elements thus order themselves according to whatever
 * priority you decide in your Comparable implementation.
 * <p>
 * Notice that the PriorityBlockingQueue does not enforce any specific behaviour for
 * elements that have equal priority (compare() == 0).
 * <p>
 * Also notice, that in case you obtain an Iterator from a PriorityBlockingQueue, the
 * Iterator does not guarantee to iterate the elements in priority order.
 * <p>
 * The SynchronousQueue is a queue that can only contain a single element internally.
 * A thread inseting an element into the queue is blocked until another thread
 * takes that element from the queue. Likewise, if a thread tries to take an element
 * and no element is currently present, that thread is blocked until a thread insert
 * an element into the queue.
 */

/**
 * The BlockingDeque interface in the java.util.concurrent class represents a deque
 * which is thread safe to put into, and take instances from. In this text I will
 * show you how to use this BlockingDeque.
 * <p>
 * The BlockingDeque class is a Deque which blocks threads tring to insert or remove
 * elements from the deque, in case it is either not possible to insert or remove
 * elements from the deque.
 * <p>
 * A deque is short for "Double Ended Queue". Thus, a deque is a queue which you can
 * insert and take elements from, from both ends.
 */

public class BlockingQueueExample implements Example {

    private static final int QUEUE_SIZE = 30;

    private static final int SLEEP_TIME_05_SEC = 500;
    private static final int SLEEP_TIME_10_SEC = 10000;

    @Override
    public void execute() {

        exampleWithConsumesAndProducer();

        exampleWithMultipleConsumersAndProducers();
    }

    public void exampleWithConsumesAndProducer() {

        // example 1 BlockingQueue<String>
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(QUEUE_SIZE);

        BlockingQueueProducer blockingQueueProducer = new BlockingQueueProducer(queue);
        BlockingQueueConsumer blockingQueueConsumer = new BlockingQueueConsumer(queue);

        new Thread(blockingQueueProducer).start();
        new Thread(blockingQueueConsumer).start();

        // example 2 BlockingQueue<Runnable>
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>();

        Runnable lambdaRunnable1 =
                () -> System.out.println("Runnable from workerThread example "
                        + currentThread().getName()
                        + " running");

        Runnable lambdaRunnable2 =
                () -> System.out.println("Runnable from workerThread example "
                        + currentThread().getName()
                        + " running");

        try {
            blockingQueue.put(lambdaRunnable1);
            blockingQueue.put(lambdaRunnable2);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            currentThread().interrupt();
        }

        WorkerThread workerThread = new WorkerThread(blockingQueue);
        workerThread.start();
    }

    public void exampleWithMultipleConsumersAndProducers() {

        System.out.println("=== BlockingQueue ===");

        var uuidQueue = new LinkedBlockingQueue<UUID>(QUEUE_SIZE);

        System.out.println("Queue will execute for 10s");

        // Multiple consumers
        Runnable runConsumer = () -> {
            currentThread().setName("Consumer " + currentThread().getName());
            while (!currentThread().isInterrupted()) {
                try {
                    sleep(SLEEP_TIME_05_SEC);
                    var uuid = uuidQueue.take();
                    System.out.print("Consumed: " + uuid + " by " + currentThread().getName());
                    System.out.println(" uuidQueue.size(): " + uuidQueue.size());
                } catch (InterruptedException e) {
                    // interrupted pattern
                    // InterruptedException makes isInterrupted returns false
                    currentThread().interrupt();
                    System.err.println("Consumer Finished");
                }
            }
        };
        var consumer1 = new Thread(runConsumer);
        consumer1.start();
        var consumer2 = new Thread(runConsumer);
        consumer2.start();

        // Producer Thread
        Runnable runProducer = () -> {
            currentThread().setName("Producer " + currentThread().getName());
            try {
                while (!currentThread().isInterrupted()) {
                    Random r = new Random();
                    // Delay producer
                    sleep(r.nextInt(SLEEP_TIME_1_SEC));
                    UUID randomUUID = UUID.randomUUID();
                    System.out.print("Produced: " + randomUUID + " by " + currentThread().getName());
                    uuidQueue.put(randomUUID);
                    System.out.println(" uuidQueue.size(): " + uuidQueue.size());
                }
            } catch (InterruptedException e) {
                // interrupted pattern
                System.err.println("Producer Finished");
            }
        };
        // Multiple producers - Examples using simple threads this time.
        var producer1 = new Thread(runProducer);
        producer1.start();
        var producer2 = new Thread(runProducer);
        producer2.start();
        var producer3 = new Thread(runProducer);
        producer3.start();

        try {
            // Queue will run for 10secs
            sleep(SLEEP_TIME_10_SEC);
            producer1.interrupt();
            producer2.interrupt();
            producer3.interrupt();
            while (!uuidQueue.isEmpty()) {
                sleep(SLEEP_TIME_1_SEC);
            }
            consumer1.interrupt();
            consumer2.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
package com.krootix.concurrencyex.reentrantlock.conditions;

import static java.lang.Thread.sleep;

public class SleepyBounedBuffer<V> extends BaseBoundedBuffer<V> {

    private static final int SLEEP_GRANULARITY = 1000;

    protected SleepyBounedBuffer(int size) {
        super(size);
    }

    public void put(V v) throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (isFull()) {
                    doPut(v);
                    return;
                }
            }
            sleep(SLEEP_GRANULARITY);
        }
    }

    public synchronized V take() throws InterruptedException {
        while (true) {
            synchronized ((this)) {
                if (isEmpty())
                    return doTake();
            }
            sleep(SLEEP_GRANULARITY);
        }
    }
}
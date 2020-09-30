package com.krootix.concurrencyex.reentrantlock.conditions;

public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {

    protected BoundedBuffer(int size) {
        super(size);
    }

    public synchronized void put(V v) throws InterruptedException {
        while (isFull())
            wait();
        doPut(v);
        notifyAll();
    }

    public synchronized V take() throws InterruptedException {
        while (isEmpty())
            wait();
        V v = doTake();
        notifyAll();
        return v;
    }
}
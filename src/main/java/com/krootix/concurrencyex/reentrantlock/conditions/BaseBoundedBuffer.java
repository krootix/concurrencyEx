package com.krootix.concurrencyex.reentrantlock.conditions;

public abstract class BaseBoundedBuffer<V> {

    private final V[] buf;
    private int tail;
    private int head;
    private int count;

    protected BaseBoundedBuffer(int capacity) {
        this.buf = (V[]) new Object[capacity];
    }

    protected final synchronized void doPut(V v) {
        buf[tail] = v;
        if (++tail == buf.length)
            tail = 0;
        ++count;
    }

    protected final synchronized V doTake() {
        V v = buf[head];
        buf[head] = null;
        if (++head == buf.length)
            head = 0;
        --count;
        return v;
    }

    public final synchronized boolean isFull() {
        return count == buf.length;
    }

    public final synchronized boolean isEmpty() {
        return count == 0;
    }
}
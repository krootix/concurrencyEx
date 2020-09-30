package com.krootix.concurrencyex.reentrantlock.conditions;

public class SynchronizedBuffer<T> {

    private final Object lock = new Object(); // private mutex
    private T elem;

    public void put(T newElem) throws InterruptedException {
        synchronized (lock) {
            while (elem != null) {
                lock.wait();
            }
            elem = newElem;
            lock.notify(); // or notifyAll
        }
    }

    public T take() throws InterruptedException {
        synchronized (lock) {
            while (elem == null) {
                lock.wait();
            }
            T tmp = elem;
            elem = null;
            lock.notify(); // or notifyAll
            return tmp;
        }
    }
}

package com.krootix.concurrencyex.collections;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleCopyOnWriteArrayList {

    private volatile Object[] array;

    // The lock protecting all mutations
    private final ReentrantLock lock = new ReentrantLock();

    public boolean contains(Object key) {
        Object[] localRef = this.array;
        if (key == null) {
            for (Object elem : localRef)
                if (elem == null) return true;
        } else {
            for (Object elem : localRef)
                if (key.equals(elem)) return true;
        }
        return false;
    }

    public void add(Object newElem) {
        lock.lock();
        try {
            Object[] localRef = this.array;
            Object[] newArray = Arrays.copyOf(localRef, localRef.length + 1);
            newArray[localRef.length] = newElem;
            array = newArray;
        } finally {
            lock.unlock();
        }
    }

    public List createSyncFreeCopy() {
        return Collections.emptyList();
    }

}

package com.krootix.concurrencyex.reentrantlock.conditions;

import com.krootix.concurrencyex.control.Example;
import com.krootix.concurrencyex.reentrantlock.conditions.exceptions.BufferEmptyException;

import static java.lang.Thread.sleep;

public class GrumpyBoundedBufferExample implements Example {

    private static final byte BUFFER_CAPACITY = 3;
    public static final int GRANULARITY = 1000;

    GrumpyBoundedBuffer<String> buffer = new GrumpyBoundedBuffer<>(BUFFER_CAPACITY);

    @Override
    public void execute() throws InterruptedException {
        while (true)
            try {
                String takenStringItem = buffer.take();
                System.out.println(takenStringItem);
                break;
            } catch (BufferEmptyException e) {
                sleep(GRANULARITY);
            }
    }
}
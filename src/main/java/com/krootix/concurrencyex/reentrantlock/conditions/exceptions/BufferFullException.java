package com.krootix.concurrencyex.reentrantlock.conditions.exceptions;

public class BufferFullException extends Exception {

    public BufferFullException(){
        super("Buffer is full. Please, try again later.");
    }

}
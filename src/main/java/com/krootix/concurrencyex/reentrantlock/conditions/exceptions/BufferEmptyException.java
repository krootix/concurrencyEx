package com.krootix.concurrencyex.reentrantlock.conditions.exceptions;

public class BufferEmptyException extends Throwable {

    public BufferEmptyException(){
        super("Buffer is empty. Please, try again later.");
    }

}
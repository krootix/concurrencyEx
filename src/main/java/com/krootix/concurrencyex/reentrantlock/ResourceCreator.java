package com.krootix.concurrencyex.reentrantlock;

public class ResourceCreator {

    public static Resource create() {
        return new Resource();
    }
}
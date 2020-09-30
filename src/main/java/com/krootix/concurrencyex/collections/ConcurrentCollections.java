package com.krootix.concurrencyex.collections;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class ConcurrentCollections {

    public void run(){

        List<String> list = new CopyOnWriteArrayList<>();

        // CopyOnWriteArraySet made on the basis of CopyOnWriteArrayList
        Set<String> set = new CopyOnWriteArraySet<>();


    }
}

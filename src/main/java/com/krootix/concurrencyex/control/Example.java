package com.krootix.concurrencyex.control;

import java.util.concurrent.ExecutionException;

@FunctionalInterface
public interface Example {

    int SLEEP_TIME_1_SEC = 1000;

    int TIME_TO_SLEEP = 100;

    void execute() throws ExecutionException, InterruptedException;

}
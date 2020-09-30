package com.krootix.concurrencyex.cyclicbarier;

import com.krootix.concurrencyex.control.Example;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.*;

public class CyclicBarrierExample implements Example {

    private static final byte AMOUNT_OF_NEAREST_ELEMENTS = 3;
    private static final int AMOUNT_OF_ITERATIONS = 100_00;

    final CountDownLatch latch = new CountDownLatch(2);
    final double[] doubles = new double[2001];

    @Override
    public void execute() throws ExecutionException, InterruptedException {

        doubles[500] = 1_000_000;

        final CyclicBarrier barrier = new CyclicBarrier(2, () ->
                doubles[1000] = (doubles[1000 - 1] + doubles[1000] + doubles[1000 + 1]) / AMOUNT_OF_NEAREST_ELEMENTS);

        final ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(() -> {
            for (int count = 0; count < AMOUNT_OF_ITERATIONS; count++) {
                double[] newDoubles = new double[1000];
                for (int k = 1; k < 1000; k++)
                    newDoubles[k] = (doubles[k - 1] + doubles[k] + doubles[k + 1]) / AMOUNT_OF_NEAREST_ELEMENTS;
                System.arraycopy(newDoubles, 0, doubles, 0, 1000);
                barrier.await();
            }
            latch.countDown();
            return null;
        });

        executorService.submit(() -> {
            for (int count = 0; count < AMOUNT_OF_ITERATIONS; count++) {
                double[] newDoubles = new double[1000];
                for (int k = 0; k < 999; k++)
                    newDoubles[k] = (doubles[1001 + k - 1] + doubles[1001 + k] + doubles[1001 + k + 1]) / AMOUNT_OF_NEAREST_ELEMENTS;
                System.arraycopy(newDoubles, 0, doubles, 0, 1000);
                barrier.await();
            }
            latch.countDown();
            return null;
        });
        latch.await();
        for (int k = 0; k < 2001; k += 50) {
            String x = Collections.nCopies((int) doubles[k] / 100, " ").toString();
            System.out.println("|" + x.replace("[", "").replace("]", "".replace(",", "*")));
        }
        Arrays.stream(doubles).forEach(System.out::println);
    }
}

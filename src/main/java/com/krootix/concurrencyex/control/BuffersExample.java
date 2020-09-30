package com.krootix.concurrencyex.control;

import com.krootix.concurrencyex.reentrantlock.conditions.ReentrantLockBuffer;
import com.krootix.concurrencyex.reentrantlock.conditions.SynchronizedBuffer;

import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;

import static java.lang.Thread.sleep;

public class BuffersExample implements Example {

    private static final String RIGHT_ARROW = "->";
    private static final String TAB = "   ";
    private static final String COLON = ":";
    private static final String SPACE = " ";
    private static final String EMPTY = "";
    private static final String DOT = ".";
    private static final String PRODUCER = "producer ";
    private static final String CONSUMER = "consumer ";
    private static final int LATCH_FOR_ONE_THREAD = 1;

    private static final int SLEEP_TIME_08_SEC = 800;
    private static final int SLEEP_TIME_03_SEC = 300;

    private static final char START_OF_ALPHABET = 'A';
    private static final char END_OF_ALPHABET = 'z';
    private static final String LAST_ELEMENT = "Z";

    private SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");

    @Override
    public void execute() {

        simpleBuffer();

        synchronizedStringBuffer(3);

        reentrantBuffer(5);

    }

    private void simpleBuffer() {

        final SynchronizedBuffer<Character> buffer = new SynchronizedBuffer<>();
        CountDownLatch countDownLatch = new CountDownLatch(LATCH_FOR_ONE_THREAD);

        // producer
        new Thread(() -> {
            for (char c = START_OF_ALPHABET; c <= END_OF_ALPHABET; c++) {
                try {
                    buffer.put(c);
                    System.err.println(PRODUCER + Thread.currentThread().getId() + COLON + SPACE + c + RIGHT_ARROW + COLON + sdf.format(System.currentTimeMillis()));
                } catch (InterruptedException ignore) {/*NOP*/}
            }
            countDownLatch.countDown();
        }).start();

        // consumer
        new Thread(() -> {
            char takenElement = Character.MIN_VALUE;
            while ((countDownLatch.getCount() != 0) && takenElement != 'z') {
                try {
                    sleep(SLEEP_TIME_08_SEC);
                    System.err.println(CONSUMER + COLON + SPACE + TAB + RIGHT_ARROW + buffer.take() + COLON + sdf.format(System.currentTimeMillis()));
                } catch (InterruptedException ignore) {/*NOP*/}
            }
            System.out.println(CONSUMER + Thread.currentThread().getId() + SPACE + Thread.currentThread().getState());
        }).start();
    }

    private void synchronizedStringBuffer(int amountOfThreads) {

        final SynchronizedBuffer<String> stringBuffer = new SynchronizedBuffer<>();
        CountDownLatch countDownLatch = new CountDownLatch(amountOfThreads);

        // producer
        for (int k = 0; k < amountOfThreads; k++) {
            final int finalK = k;
            new Thread(() -> {
                for (char c = START_OF_ALPHABET; c <= END_OF_ALPHABET; c++) {
                    String value = EMPTY + c + finalK;
                    System.out.println(PRODUCER + Thread.currentThread().getId() + SPACE + value + DOT);
                    try {
                        stringBuffer.put(value);
                    } catch (InterruptedException ignore) {/*NOP*/}
                    System.err.println(PRODUCER + Thread.currentThread().getId() + SPACE + value + RIGHT_ARROW);
                }
                System.out.println(PRODUCER + Thread.currentThread().getId() + SPACE + Thread.currentThread().getState());
                countDownLatch.countDown();
            }).start();
        }

        // consumer
        new Thread(() -> {
            try {
                String value = EMPTY;
                while (countDownLatch.getCount() != 0 || !value.contains(LAST_ELEMENT)) {
                    value = stringBuffer.take();
                    System.err.println(CONSUMER + TAB + RIGHT_ARROW + value);
                    sleep(SLEEP_TIME_03_SEC);
                }
                System.out.println(CONSUMER + SPACE + Thread.currentThread().getState());
            } catch (InterruptedException ignore) {/*NOP*/}
        }).start();
    }

    private void reentrantBuffer(int sizeOfBuffer) {

        final ReentrantLockBuffer<Character> buffer = new ReentrantLockBuffer<>(sizeOfBuffer);
        CountDownLatch countDownLatch = new CountDownLatch(LATCH_FOR_ONE_THREAD);

        // producer
        new Thread(() -> {
            for (char c = START_OF_ALPHABET; c <= END_OF_ALPHABET; c++) {
                try {
                    buffer.put(c);
                    System.err.println(PRODUCER + Thread.currentThread().getId() + SPACE + c + RIGHT_ARROW + COLON + sdf.format(System.currentTimeMillis()));
                } catch (InterruptedException ignore) {/*NOP*/}
            }
            countDownLatch.countDown();
        }).start();

        // consumer
        new Thread(() -> {
            char takenElement = Character.MIN_VALUE;
            while ((countDownLatch.getCount() != 0) && takenElement != END_OF_ALPHABET) {
                try {
                    sleep(SLEEP_TIME_08_SEC);
                    takenElement = buffer.take();
                    System.err.println(CONSUMER + Thread.currentThread().getId() + SPACE + TAB + RIGHT_ARROW + takenElement + COLON + sdf.format(System.currentTimeMillis()));
                } catch (InterruptedException ignore) {/*NOP*/}
            }
            System.out.println(CONSUMER + Thread.currentThread().getId() + SPACE + Thread.currentThread().getState());
        }).start();
    }
}
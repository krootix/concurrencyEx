package com.krootix.concurrencyex.control;

import com.krootix.concurrencyex.reentrantlock.Resource;
import com.krootix.concurrencyex.reentrantlock.ResourceCreator;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.SECONDS;


public class CompletableFutureExample implements Example {

    @Override
    public void execute() throws ExecutionException, InterruptedException {

        runAsyncAnonymousClass();

        runAsyncLambda();

        supplyAsync();

        thenApply();

        thenApplyThenApply();

        thenAccept();

        thenRun();

        supplyAsyncThenApply();

        supplyAsyncThenApplyAsync();

        supplyAsyncThenApplyAsyncWithExecutor();

        thenCompose();

        thenCombine();

        allOf(100);

        anyOf();

        exceptionally();

        handle();
    }

    void runAsyncAnonymousClass() throws ExecutionException, InterruptedException {

        // Run the task specified by the Runnable object asynchronously
        CompletableFuture<Void> future = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                // Имитация длительной работы
                try {
                    SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
                System.out.println("I am going to work in a separate thread, not in a main.");
            }
        });
        // Locking and waiting for a Future to complete
        future.get();
    }

    void runAsyncLambda() throws ExecutionException, InterruptedException {
        // Using a lambda expression
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            // Imitation of long-term operation
            try {
                SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("I am going to work in a separate thread, not in a main.");
        });
        // Locking and waiting for a Future to complete
        future.get();
    }

    void supplyAsync() throws ExecutionException, InterruptedException {

        // Using a lambda expression
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Asynchronous task result";
        });

        // Locking and waiting for a Future to complete
        String result = future.get();
        System.out.println(result);
    }

    void thenApply() throws ExecutionException, InterruptedException {
        // Creating CompletableFuture
        CompletableFuture<String> whatsYourNameFuture = CompletableFuture.supplyAsync(() -> {
            try {
                SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "someName";
        });

        // Add a callback to Future using thenApply ()
        CompletableFuture<String> greetingFuture = whatsYourNameFuture.thenApply(name -> {
            return "Hi, " + name;
        });

        // Locking and waiting for a Future to complete
        System.out.println(greetingFuture.get()); // Привет, someName
    }

    void thenApplyThenApply() throws ExecutionException, InterruptedException {
        CompletableFuture<String> welcomeText = CompletableFuture.supplyAsync(() -> {
            try {
                SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "someName";
        }).thenApply(name -> {
            return "Hi, " + name;
        }).thenApply(greeting -> {
            return greeting + ". Welcome to the new world";
        });

        System.out.println(welcomeText.get());
    }

    void thenAccept() {
        CompletableFuture.supplyAsync(ResourceCreator::create)
                .thenAccept(Resource::doSomething);
    }

    void thenRun() {
        // Example thenRun()
        CompletableFuture.supplyAsync(() -> {
            // Do some calculations
            return 1;
        }).thenRun(() -> {
            // Calculations completed
            System.out.println("CompletableFuture completed");
        });
    }

    void supplyAsyncThenApply() {
        // These asynchronous callback types can help you parallelize tasks
        // by executing them in a separate thread.
        CompletableFuture.supplyAsync(() -> {
            try {
                SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Some result";
        }).thenApply(result -> {
/*
        Runs on the same thread as the supplyAsync() task, or on the main thread if
        the supplyAsync() task ends immediately (remove sleep () to check this)
*/
            return "Processed result";
        });
/*
        In the above example, the thenApply() task runs on the same thread
        as the supplyAsync() task, or on the main thread if the supplyAsync() task
        completes quickly enough (try removing the sleep() call to check).
*/
    }

    /*
        To have more control over the thread running a task, you can use asynchronous
        callbacks. If you use thenApplyAsync() it will execute on a different thread obtained
        from ForkJoinPool.commonPool()
    */
    void supplyAsyncThenApplyAsync() {
        CompletableFuture.supplyAsync(() ->
                "Some result"
        ).thenApplyAsync(result ->
                // Runs on a different thread taken from ForkJoinPool.commonPool()
                "Processed result"
        );
    }

    /*
            Moreover, if you pass an Executor to thenApplyAsync(), the task will be executed
            on a thread sourced from the Executor's thread pool.
    */
    void supplyAsyncThenApplyAsyncWithExecutor() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CompletableFuture.supplyAsync(() ->
                "Some result"
        ).thenApplyAsync(result ->
                        // Executed on the thread received from the Executor
                        "Processed result"
                , executorService);
        executorService.shutdown();
    }

    private CompletableFuture<Resource> getResource() {
        return CompletableFuture.supplyAsync(ResourceCreator::create);
    }

    private CompletableFuture<Integer> getCount(Resource resource) {
        return CompletableFuture.supplyAsync(resource::incrementAndGetCount);
    }

    // thenCompose () is used to combine two tasks when one task depends on the other
    void thenCompose() {
        CompletableFuture<Integer> result = getResource()
                .thenCompose(this::getCount);
    }

    /*
        thenCombine () is used when you want two tasks to run independently of each other,
        and when both are complete, some action is taken.
    */
    void thenCombine() throws ExecutionException, InterruptedException {
        System.out.println("Получение веса.");
        CompletableFuture<Double> weightInKgFuture = CompletableFuture.supplyAsync(() -> {
            try {
                SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return 65.0;
        });

        System.out.println("Получение роста.");
        CompletableFuture<Double> heightInCmFuture = CompletableFuture.supplyAsync(() -> {
            try {
                SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return 177.8;
        });

        System.out.println("Расчёт индекса массы тела.");
        CompletableFuture<Double> combinedFuture = weightInKgFuture
                .thenCombine(heightInCmFuture, (weightInKg, heightInCm) -> {
                    Double heightInMeter = heightInCm / 100;
                    return weightInKg / (heightInMeter * heightInMeter);
                });

        System.out.println("Ваш индекс массы тела - " + combinedFuture.get());
    }

    void allOf(int count) throws ExecutionException, InterruptedException {
        List<CompletableFuture<Resource>> resources = Stream.iterate(0, i -> i++)
                .limit(count)
                .map(x -> getResource())
                .collect(Collectors.toList());

        CompletableFuture<Void> allFutures =
                CompletableFuture.allOf(resources.toArray(new CompletableFuture[0]));
/*
        The problem with CompletableFuture.allOf() is that it returns
        CompletableFuture<Void>. But we can get the results of all completed
        CompletableFuture by adding a few lines of code
*/

        // When all tasks are complete, call future.join() to get the results and collect them into a list
        CompletableFuture<List<Resource>> allResourcesFuture = allFutures.thenApply(v ->
                        resources.stream()
//                              .map(resourceFuture -> resourceFuture.join())
                                .map(CompletableFuture::join)
                                .collect(Collectors.toList())
        );
//        Since we call future.join() when all tasks are complete, blocking is not happening anywhere.

/*
        The join() method is similar to get(). The only difference is that it throws an
        unchecked exception if CompletableFuture fails.
*/

        // Count the number of web pages containing the "CompletableFuture" keyword
        CompletableFuture<Long> countFuture = allResourcesFuture.thenApply(resourceList ->
                (long) resourceList.size()
        );

        System.out.println("Количество resource " +
                countFuture.get());
    }

    void anyOf() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Результат Future 1";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Результат Future 2";
        });

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Результат Future 3";
        });

        CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf(future1, future2, future3);

        System.out.println(anyOfFuture.get());
//        In the above example, anyOfFuture completes when any of the three CompletableFuture completes.
    }

    void exceptionally() throws ExecutionException, InterruptedException {
        int age = -1;

        CompletableFuture<String> maturityFuture = CompletableFuture.supplyAsync(() -> {
            if (age < 0) {
                throw new IllegalArgumentException("Возраст не может быть отрицательным");
            }
            if (age > 18) {
                return "Взрослый";
            } else {
                return "Ребёнок";
            }
        }).exceptionally(ex -> {
            System.out.println("Ой! У нас тут исключение - " + ex.getMessage());
            return "Неизвестно!";
        });
/*

        The exceptionally() method allows you to bypass possible errors,
        if any. You can log an exception and return the default.
*/
        System.out.println("Зрелость: " + maturityFuture.get());
    }

    void handle() throws ExecutionException, InterruptedException {
        int age = -1;

        CompletableFuture<String> maturityFuture = CompletableFuture.supplyAsync(() -> {
            if (age < 0) {
                throw new IllegalArgumentException("Возраст не может быть отрицательным");
            }
            if (age > 18) {
                return "Взрослый";
            } else {
                return "Ребёнок";
            }
        }).handle((res, ex) -> {
            if (ex != null) {
                System.out.println("Ой! У нас тут исключение - " + ex.getMessage());
                return "Неизвестно!";
            }
            return res;
        });

//        If an exception is thrown, the argument res will be null, if not, then ex will be null.
        System.out.println("Зрелость: " + maturityFuture.get());
    }
}
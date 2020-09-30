package com.krootix.concurrencyex.control;

public class Counter {
    public void displayCounter(){
        try {
            for(int i = 1; i<=5; i++){
                System.out.println(Thread.currentThread().getName() + " Counter: " + i);
//                System.out.println(" Counter: " + i);
            }
        }catch (Exception e){
            System.out.println("Thread is interrupted.");
        }
    }
}
package com.krootix.concurrencyex.interaction;

public class ThreadInteraction {

    private boolean isActive = false;

    public synchronized void Request(String request){
        if(isActive){
            try {
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("Pilot:");
        System.out.println(request);
        isActive = true;
        notify(); // there is one thread, that waiting
    }

    public synchronized void Response(String response){
        if(!isActive){
            try {
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        System.out.println("Controller:");
        System.out.println(response);
        isActive = false;
        notify();
    }
}
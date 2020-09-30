package com.krootix.concurrencyex.interaction;

public class Request implements Runnable {
    private ThreadInteraction interaction;
    private String[] requests =
            {
                    "Dnipro Radar, Aeroflot 1816",
                    "Request descent, Aeroflot 1816",
                    "Descending to altitude 6,000 feet, Aeroflot 1816"
            };

    public Request(ThreadInteraction interaction) {
        this.interaction = interaction;
        new Thread(this, "Request").start();
    }

    @Override
    public void run() {
        for (String s : requests) {
            interaction.Request(s);
        }
    }
}
package com.krootix.concurrencyex.interaction;

public class Response implements Runnable {
    private ThreadInteraction interaction;
    private String[] responses =
            {
                    "Aeroflot 1816, Dnipro Radar, go ahead",
                    "Aeroflot 1816, descend to altitude 6,000 feet"
            };

    public Response(ThreadInteraction interaction) {
        this.interaction = interaction;
        new Thread(this, "Response").start();
    }

    @Override
    public void run() {
        for (String s : responses) {
            interaction.Response(s);
        }
    }
}
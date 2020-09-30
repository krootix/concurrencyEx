package com.krootix.concurrencyex.control;

import com.krootix.concurrencyex.interaction.Request;
import com.krootix.concurrencyex.interaction.Response;
import com.krootix.concurrencyex.interaction.ThreadInteraction;

// Thread interaction example

public class ThreadInteractionExample implements Example {

    @Override
    public void execute() {

        ThreadInteraction interaction = new ThreadInteraction();

        Request request = new Request(interaction);
        Response response = new Response(interaction);

    }
}
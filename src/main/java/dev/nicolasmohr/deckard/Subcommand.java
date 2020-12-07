package dev.nicolasmohr.deckard;

import org.javacord.api.event.message.MessageCreateEvent;

public interface Subcommand {
    String getName();
    String getHelp();
    void handle(String args, MessageCreateEvent event);
}

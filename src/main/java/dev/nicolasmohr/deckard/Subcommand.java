package dev.nicolasmohr.deckard;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.function.BiConsumer;

public interface Subcommand {
    String getName();
    String getHelp();
    void handle(String args, MessageCreateEvent event);
}

package dev.nicolasmohr.deckard;

import org.javacord.api.event.message.MessageCreateEvent;

import java.util.function.BiConsumer;

/** Builder class that builds objects that implement the Subcommand interface
 */
public class SubcommandBuilder implements Subcommand {
    String name;
    String help;
    BiConsumer<String, MessageCreateEvent> action;

    public SubcommandBuilder() {
        this.name = "";
        this.help = "";
        this.action = (x,y) -> {};
    }

    public SubcommandBuilder setHelp(String help) {
        this.help = help;
        return this;
    }
    public SubcommandBuilder setName(String name) {
        this.name = name;
        return this;
    }
    public SubcommandBuilder setAction(BiConsumer<String, MessageCreateEvent> action) {
        this.action = action;
        return this;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getHelp() {
        return this.help;
    }

    @Override
    public void handle(String args, MessageCreateEvent event) {
        this.action.accept(args, event);
    }
}

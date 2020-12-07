package dev.nicolasmohr.deckard;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Please provide a valid token as the first argument!");
            return;
        }

        String token = args[0];

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        // Add a listener which answers with "Pong!" if someone writes "!ping"
        api.addMessageCreateListener(event -> {
            if (event.getMessageContent().equalsIgnoreCase("!ping")) {
                event.getChannel().sendMessage("Pong!");
            }
        });

        HashMap<String, Battleground> battlegrounds = Battleground.indexByName(Datasheet.fromFile("data/battlegroundTimings.yaml").battlegrounds);

        BiConsumer<String, MessageCreateEvent> timingsHandler =
                (argString, event) -> {
                    var bgName = argString; // No other arguments so far

                    var optBg =
                            Optional.ofNullable(battlegrounds.get(bgName))
                                    .map(CommandHandler::embedFromBattleground);

                    if (optBg.isPresent()) {
                        event.getChannel().sendMessage(optBg.orElse(new EmbedBuilder().setTitle("This is a bug. Please report if you see this")));
                    } else {
                        event.getChannel().sendMessage("No battleground found with the name " + bgName);
                    }
                };

        var deckardHandler = new CommandHandler("!deckard")
                .setHelpHint("This bot teaches players about Heroes of the Storm.")
                .addSubcommand(
                    new SubcommandBuilder()
                        .setName("timings")
                        .setHelp("Displays timings for the specified battleground")
                        .setAction(timingsHandler)
                );

        api.addMessageCreateListener(deckardHandler);

        // Print the invite url of your bot
        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
    }
}

package dev.nicolasmohr.deckard;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class handles all text messages intended for the bot, indicated with a `!deckard` prefix.
 */
public class CommandHandler implements MessageCreateListener {

    HashMap<String, Battleground> battlegrounds = Battleground.indexByName(Datasheet.fromFile("data/battlegroundTimings.yaml").battlegrounds);

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        var words = event.getMessageContent().split(" ");
        if (words[0].equalsIgnoreCase("!deckard")) {
            if (words.length >= 3 && words[1].equalsIgnoreCase("timings")) {

                // Concatenate the remaining words into a single string to look up as the battleground name
                var bgName =
                        Arrays.stream(words)
                                .skip(2)
                                .collect(Collectors.joining(" "))
                                .toLowerCase();
                var optBg =
                        Optional.ofNullable(battlegrounds.get(bgName))
                                .map(CommandHandler::embedFromBattleground);

                if (optBg.isPresent()) {
                    event.getChannel().sendMessage(optBg.orElse(new EmbedBuilder().setTitle("This is a bug. Please report if you see this")));
                } else {
                    event.getChannel().sendMessage("No battleground found with the name " + words[2]);
                }
            } else {
                event.getChannel().sendMessage("Wrong syntax. Enter `!deckard help` for instructions.");
            }

        } else {
            //Command is not intended for this listener, do nothing.
        }

    }

    /**
     * Generates a formatted embed containing the information about a single battleground.
     */
    public static EmbedBuilder embedFromBattleground(Battleground bg) {
        var embed = new EmbedBuilder()
                .setTitle(bg.name);
        for (Timing t : bg.timings) {
            var timingsString = "Spawn: " + String.valueOf(t.spawn) + " Respawn: " + String.valueOf(t.respawn);
            embed.addField(t.objective, timingsString);
        }
        embed.setThumbnail(bg.thumbnail);
        return embed;
    }
}

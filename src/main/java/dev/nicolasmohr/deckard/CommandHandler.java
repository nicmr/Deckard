package dev.nicolasmohr.deckard;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * This class handles all text messages intended for the bot, marked by starting with the commandPrefix;
 * Its interface is very similar to command line based argument parsers.
 * Subcommands can be added which the commands will be dispatched to.
 */
public class CommandHandler implements MessageCreateListener {

    String commandPrefix;
    HashMap<String, Subcommand> subcommands;
    String helpHint;

    public CommandHandler(String commandPrefix){
        this.commandPrefix = commandPrefix;
        this.subcommands = new HashMap<>();
    }

    public CommandHandler setHelpHint(String helpHint){
        this.helpHint = helpHint;
        return this;
    }

    /**
     * Adds a new subcommand to the CommandHandler
     * Returns this for method chaining
     */
    public CommandHandler addSubcommand(Subcommand subcommand) {
        this.subcommands.put(subcommand.getName(), subcommand);
        return this;
    }


    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        var words = event.getMessageContent().split(" ");
        if (words[0].equalsIgnoreCase(this.commandPrefix)) {

            var subcommand = this.subcommands.get(words[1]);
            if (subcommand != null) {
                subcommand.handle(Arrays.stream(words).skip(2).collect(Collectors.joining(" ")), event);
            } else {
                event.getChannel().sendMessage(this.generalHelpEmbed());
            }
        } else {
            //Command is not intended for this Commandhandler, do nothing.
        }
    }

    /**
     * Generates a formatted embed containing the information about a single battleground.
     */
    public static EmbedBuilder embedFromBattleground(Battleground bg) {
        var embed = new EmbedBuilder()
                .setTitle(bg.name);
        for (Timing t : bg.timings) {
            var timingsString = "Spawn: " + t.spawn + " Respawn: " + t.respawn;
            embed.addField(t.objective, timingsString);
        }
        embed.setThumbnail(bg.thumbnail);
        return embed;
    }

    /**
     * Generates a formatted embed containing general help about the command and its subcommands
     * @return
     */
    public EmbedBuilder generalHelpEmbed(){
        var subcommandString = this.subcommands.keySet().stream().collect(Collectors.joining(", "));

        return new EmbedBuilder().setTitle("General help")
                .addField("Description:", this.helpHint, true)
                .addField("Subcommands:", subcommandString)
                .addField("Hint:", "Learn more about each subcommand by entering " + this.commandPrefix + " help <SUBCOMMAND>");
    }


    /**
     * Generates a formatted embed containing specific help about a single subcommand
     * @return
     */
    public EmbedBuilder subcommandHelpEmbed(String subcommand){
        return new EmbedBuilder().setTitle("SubcommandHelp");
    }
}

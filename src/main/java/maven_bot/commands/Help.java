package maven_bot.commands;

import java.awt.Color;
import java.time.Instant;

import maven_bot.App;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Help {
    public Help(MessageReceivedEvent event) {
        EmbedBuilder embedMessage = new EmbedBuilder();
        User author = event.getAuthor();

        embedMessage.setTitle("Commands")
            .setColor(new Color(0xab2c85))
            .setDescription(author.getName() + ". The current commands are as follows and I quote;")
			.addField(App.prefix + "help", "This command.", false)
			.addField(App.prefix + "roll [amount]d[number] <+/-modifier>", "Rolls a dice up to the given number x amount of times. Can set an optional modifier.", false)
			// .addField(Bot.prefix + "multiroll d[number] <amount> <+/-modifier> <sum=true/false>", "Rolls multiple dice up to the given number. Can set amount, modifier, or if you would like to sum the numbers up.", false)
			// .addField(Bot.prefix + "xkcd [number]", "Pulls up the respective xkcd comic.", false)
			// .addField(Bot.prefix + "whatif [number]", "Pulls up the respective xkcd what if?.", false)
			// .addField(Bot.prefix + "github", "Links to the github.", false)
            .addField(App.prefix + "ping", "Pong!", false)
            .setTimestamp(Instant.now());
        
        author.openPrivateChannel().flatMap(channel -> channel.sendMessage(embedMessage.build())).queue();
    }
}
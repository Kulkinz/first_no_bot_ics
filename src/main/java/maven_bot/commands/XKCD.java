package maven_bot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class XKCD {
    public XKCD(MessageReceivedEvent event, String number) {
        MessageChannel channel = event.getChannel();
        channel.sendMessage("https://xkcd.com/" + number + "/#")
        .queue();
    }
}
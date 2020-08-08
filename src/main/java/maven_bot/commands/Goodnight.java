package maven_bot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Goodnight {
    public Goodnight(MessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
            channel.sendMessage("Sleep tight!")
                .queue();
    }
}
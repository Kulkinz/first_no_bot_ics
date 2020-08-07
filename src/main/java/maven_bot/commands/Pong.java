package maven_bot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Pong {
    public Pong(MessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
            long time = System.currentTimeMillis();
            channel.sendMessage("Ping!").queue(response -> {
                response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).queue();
        });
    }
}
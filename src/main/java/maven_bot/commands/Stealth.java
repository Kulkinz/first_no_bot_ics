package maven_bot.commands;

import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Stealth {
    public Stealth(MessageReceivedEvent event, String ping) {
        MessageChannel channel = event.getChannel();
        try {
            event.getMessage()
            .delete()        
            .queueAfter(5, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            System.out.println("Could not delete");
        }
        channel.sendMessage("ping")
        .delay(5, TimeUnit.MILLISECONDS)
        .flatMap(Message::delete)
        .queue();
        
    }
}
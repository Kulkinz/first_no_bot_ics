package maven_bot.commands;

import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Connor {
    public Connor(MessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        try {
            event.getMessage()
            .delete()        
            .queueAfter(5, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            System.out.println("Could not delete");
        }
        channel.sendMessage("<@393804816419389440>")
        .delay(5, TimeUnit.MILLISECONDS)
        .flatMap(Message::delete)
        .queue();
        
    }
}
package maven_bot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Github {
    public Github(MessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
            channel.sendMessage("The bot's repository can be found at: https://github.com/Kulkinz/maven_java_discord_bot")
                .queue();
    }
}
package maven_bot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Roll {
    public Roll(MessageReceivedEvent event, int amount, int sides, int modifier) {
        MessageChannel channel = event.getChannel();
        channel.sendMessage("Rolling a d" + sides + " with a modifier of " + modifier + ", " + amount + " times.")
            .queue();
        String result = "Result";
        String modifiedResult = "With modifier";
        for (int i = 0; i < amount; i++) {
            int number = (int) Math.floor(Math.random() * sides) + 1;
            result += ", " + number;
            int modifed = number + modifier;
            modifiedResult += ", " + modifed;
        }
        channel.sendMessage(result)
            .queue();
        if (modifier != 0) {
            channel.sendMessage(modifiedResult)
                .queue();
        }
    }
}
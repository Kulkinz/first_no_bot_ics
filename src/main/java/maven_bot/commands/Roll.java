package maven_bot.commands;

import maven_bot.commands.subcommands.RecordRolls;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Roll {
    public Roll(MessageReceivedEvent event, int amount, int sides, int modifier) {
        new java.util.Random();
        MessageChannel channel = event.getChannel();
        channel.sendMessage("Rolling a d" + sides + " with a modifier of " + modifier + ", " + amount + " times.")
            .queue();
        String result = "Result";
        int total = 0;
        String modifiedResult = "Individual dice modifier";
        int modifiedTotal = 0;
        for (int i = 0; i < amount; i++) {
            int number = (int) Math.floor(Math.random() * sides) + 1;
            try {
                new RecordRolls(number);
            } catch (Exception e) {
                e.printStackTrace();
            }
            result += ", " + number;
            total += number;
            int modifed = number + modifier;
            modifiedResult += ", " + modifed;
        }
        modifiedTotal = total + modifier;
        channel.sendMessage(result)
            .queue();
        System.out.println(result);
        if (amount > 1) {
            channel.sendMessage("Total: " + total)
            .queue();
            System.out.println("Total: " + total);
        }
        if (modifier != 0) {
            if (amount > 1) {
                channel.sendMessage("Modified Total: " + modifiedTotal)
                .queue();
                System.out.println("Modified Total: " + modifiedTotal);
            }
            
            channel.sendMessage(modifiedResult)
                .queue();
            System.out.println(modifiedResult);
            
        }
    }
}
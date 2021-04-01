package maven_bot;

import java.util.Calendar;

import maven_bot.commands.Stealth;
import maven_bot.commands.Convert;
import maven_bot.commands.Github;
import maven_bot.commands.Giveme;
import maven_bot.commands.Goodnight;
import maven_bot.commands.Help;
import maven_bot.commands.Isaiah;
import maven_bot.commands.Ping;
import maven_bot.commands.Pong;
import maven_bot.commands.Quote;
import maven_bot.commands.Roll;
import maven_bot.commands.Settings;
import maven_bot.commands.XKCD;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandSelector {
    public CommandSelector(MessageReceivedEvent event, String message, boolean admins) {
        String[] args = message.substring(1).trim().split(" ");
        String command = args[0];
        //message.content.slice(prefix.length).trim().split(/ +/g);
        System.out.println(event.getAuthor() + " just tried to run " + command + " at " + Calendar.getInstance().getTime());

        switch (command) {
            case "ping":
                new Ping(event);
                break;
            case "pong":
                new Pong(event);
                break;
            case "isaiah":
                new Isaiah(event);
                break;
            case "stealth":
                if (args.length > 1) {
                    new Stealth(event, args[1]);
                }
                break;
            case "github":
                new Github(event);
                break;
            case "goodnight":
                new Goodnight(event);
                break;
            case "xkcd":
                if (args.length > 1) {
                    new XKCD(event, args[1]);
                } else {
                    new XKCD(event, "");
                }
                break;
            case "roll":
                String[] dice = args[1].split("d");
                String amountToRollString;
                String sidesString;
                String modifierString;
                if (dice.length > 1) {
                    amountToRollString = dice[0].toString();
                    sidesString = dice[1].toString();
                } else {
                    amountToRollString = 1 + "";
                    sidesString = dice[0].toString();
                }
                if (args.length > 2) {
                    modifierString = args[2];
                } else {
                    modifierString = 0 + "";
                }
                
                int amountToRoll;
                try {
                    amountToRoll = Integer.parseInt(amountToRollString.replaceAll("[^0-9]+", ""));
                } catch (Exception e) {
                    amountToRoll = 1;
                }
                int sides = Integer.parseInt(sidesString.replaceAll("[^0-9]+", ""));
                int modifier = Integer.parseInt(modifierString.replaceAll("[^0-9]-+", ""));

                if (amountToRoll > 200) {
                    amountToRoll = 200;
                }

                System.out.println("Amount: " + amountToRoll + " Sides: " + sides + " Modifier: " + modifier);
                new Roll(event, amountToRoll, sides, modifier);
                break;
            case "convert":
                double amount = Double.parseDouble(args[1]);
                try {
                    Convert.Conversion from = Convert.Conversion.valueOf(args[2].toUpperCase());
                    Convert.Conversion to = Convert.Conversion.valueOf(args[3].toUpperCase());
                    new Convert(event, amount, from, to);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Input Units: " + args[2]);
                    System.err.println("Output Units: " + args[3]);
                    MessageChannel channel = event.getChannel();
                    channel.sendMessage("Unable to parse specified units. Either it was misspelled or isn't in the system.")
                    .queue();
                }
                break;


            case "quote":

                String[] splitQuotes = message.split("\"");

                try {
                    new Quote(event, splitQuotes[1], splitQuotes[3], splitQuotes[5]);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Invalid arguments: " + message);
                    MessageChannel channel = event.getChannel();
                    channel.sendMessage("Unable to create quote. Either the syntax was wrong, or something has really gone wrong")
                    .queue();
                }
                break;


            case "giveme":
                try {
                    if (args.length > 2) {
                        new Giveme(event, admins, args[1], args[2]);
                    } else {
                        new Giveme(event, admins, args[1]);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    MessageChannel channel = event.getChannel();
                    channel.sendMessage("Unable to set roles")
                    .queue();
                }
                break;
            case "help":
                new Help(event, admins);
                break;
            case "settings":
                try {
                    if (args.length > 2) {
                        new Settings(event, admins, args[1], args[2]);
                    } else {
                        new Settings(event, admins);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    MessageChannel channel = event.getChannel();
                    channel.sendMessage("Unable to set settings")
                    .queue();
                }
                break;
        }
    }
}
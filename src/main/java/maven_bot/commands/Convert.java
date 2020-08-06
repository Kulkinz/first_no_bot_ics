package maven_bot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Convert {
    public Convert(MessageReceivedEvent event, double amount, Conversion from, Conversion to) {
        MessageChannel channel = event.getChannel();
        double number = amount;
        switch (from) {
            case CM:
            case CENTIMETER:
            case CENTIMETERS:
                number *= 0.01;
                break;
            case M:
            case METER:
            case METERS:
                break;
            case KM:
            case KILOMETER:
            case KILOMETERS:
                number *= 1000;
                break;
            case FT:
            case FOOT:
            case FEET:
                number *= 0.3048;
                break;
            case IN:
            case INCH:
            case INCHES:
                number *= 0.0254;
                break;
        }

        switch (to) {
            case CM:
            case CENTIMETER:
            case CENTIMETERS:
                number /= 0.01;
                break;
            case M:
            case METER:
            case METERS:
                break;
            case KM:
            case KILOMETER:
            case KILOMETERS:
                number /= 1000;
                break;
            case FT:
            case FOOT:
            case FEET:
                number /= 0.3048;
                break;
            case IN:
            case INCH:
            case INCHES:
                number /= 0.0254;
                break;
        }

        channel.sendMessage(amount + " " + from + " converts to " + number + " " + to + ".")
        .queue();
    }

    public enum Conversion {
        CENTIMETERS,
        CENTIMETER,
        CM,
        METERS,
        METER,
        M,
        KILOMETERS,
        KILOMETER,
        KM,
        FOOT,
        FEET,
        FT,
        INCHES,
        INCH,
        IN,
    }
}
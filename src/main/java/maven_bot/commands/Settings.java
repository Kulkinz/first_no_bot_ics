package maven_bot.commands;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Settings {
    public Settings(MessageReceivedEvent event, boolean admins, String setting, String variable) throws Exception {
        Guild guild = event.getGuild();
        MessageChannel channel = event.getChannel();

        if (!admins) {
            channel.sendMessage("This is restricted to the owner of the server. Apologizes.")
                .queue();
            return;
        }
        //channel.sendMessage(guild.getName())
        //.queue();
        //channel.sendMessage(guild.getOwnerId())
        //.queue();
        //channel.sendMessage(guild.getId())
        //.queue();

        Path jsonFile = Paths.get("serverConfigs\\"+guild.getId()+".json");
        if (!Files.exists(jsonFile)) {
            FileWriter fileWriter = new FileWriter(jsonFile.toFile());
            fileWriter.write("{ }");
            fileWriter.close();
            new Settings(event, admins, setting, variable);
        } else {
            JSONObject localServerSettings = (JSONObject) new JSONParser().parse(new FileReader(jsonFile.toFile()));
            switch (setting) {
                case "listSettings":
                    channel.sendMessage("The list of available settings are: " + localServerSettings.keySet().toString())
                        .queue();
                    break;
            
                case "prefix":
                    if (localServerSettings.containsKey(setting)) {
                        localServerSettings.put(setting, variable);
                        channel.sendMessage("The prefix has been changed to: " + variable)
                            .queue();
                    }
                    break;
            
                default:
                    break;
            }
            try {
                FileWriter fileWriter = new FileWriter(jsonFile.toFile());
                fileWriter.write(localServerSettings.toJSONString());
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Settings(MessageReceivedEvent event, boolean admins) throws Exception {
        this(event, admins, "listSettings", "true");
    }
}
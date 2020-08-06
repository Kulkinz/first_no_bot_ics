package maven_bot;

import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class App extends ListenerAdapter {
    public static char prefix;
    static JSONObject config;

    public static void main(String[] args) throws Exception {
        config = (JSONObject) new JSONParser().parse(new FileReader("config.json"));

        String token = (String) config.get("token");
        prefix = config.get("prefix").toString().charAt(0);

        new JDABuilder(token)
                .addEventListeners(new App())
            .setActivity(Activity.playing("Ayyyy"))
            .build();
        System.out.println("Booted up");
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        Guild guild = event.getGuild();
        String text = msg.getContentRaw();
        boolean admins;

        try {
            Path jsonFile = Paths.get("serverConfigs\\"+guild.getId()+".json");
            JSONObject config = (JSONObject) new JSONParser().parse(new FileReader(jsonFile.toFile()));
            prefix = config.get("prefix").toString().charAt(0);
        } catch (Exception e) {
            //TODO: handle exception
        }

        try {
            if (text.charAt(0) != prefix) {
                return;
            }
        } catch (Exception e) {
            return;
        }
        if (msg.getAuthor().isBot()) {
            return;
        }
        if (msg.getAuthor().getId().equals(guild.getOwnerId()) || msg.getAuthor().getId().equals(config.get("ownerID").toString())) {
            admins = true;
        } else {
            admins = false;
        }
        new CommandSelector(event, text, admins);
    }
}
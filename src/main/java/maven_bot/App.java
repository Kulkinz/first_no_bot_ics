package maven_bot;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class App extends ListenerAdapter {
    public static char prefix;

    public static void main(String[] args) throws Exception {
        JSONObject config = (JSONObject) new JSONParser().parse(new FileReader("config.json"));

        String token = (String) config.get("token");
        prefix = config.get("prefix").toString().charAt(0);

        new JDABuilder(token)
                .addEventListeners(new App())
            .setActivity(Activity.playing("Undergoing some maintanence"))
            .build();
        System.out.println("Booted up");
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String text = msg.getContentRaw();
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
        new CommandSelector(event, text);
    }
}
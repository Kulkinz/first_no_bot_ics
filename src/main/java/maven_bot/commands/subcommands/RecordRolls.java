package maven_bot.commands.subcommands;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class RecordRolls {
    public RecordRolls(int value) throws Exception {
        JSONObject rolls = (JSONObject) new JSONParser().parse(new FileReader("rolls.json"));
        if (rolls.containsKey(Integer.toString(value))) {
            //System.out.println("Hit!");
            int total = (Integer) Math.toIntExact((long) rolls.get(Integer.toString(value)));
            total++;
            //System.out.println(total);
            rolls.put(value, total);
        } else {
            rolls.put(value, 1);
        }
        try {
            FileWriter file = new FileWriter("rolls.json");
            file.write(rolls.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
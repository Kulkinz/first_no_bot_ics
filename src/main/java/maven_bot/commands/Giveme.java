package maven_bot.commands;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Giveme {
    public Giveme(MessageReceivedEvent event, boolean admins, String request, String role) throws Exception {
        Guild guild = event.getGuild();
        MessageChannel channel = event.getChannel();
        role = role.toLowerCase();

        Path jsonFile = Paths.get("serverConfigs\\giveme\\"+guild.getId()+".json");
        if (!Files.exists(jsonFile)) {
            FileWriter fileWriter = new FileWriter(jsonFile.toFile());
            fileWriter.write("{ }");
            fileWriter.close();
            new Giveme(event, admins, request, role);
        } else {
            JSONObject giveMeList = (JSONObject) new JSONParser().parse(new FileReader(jsonFile.toFile()));
            List<Role> roles = event.getGuild().getRolesByName(role, true);
            switch (request) {
                case "list":
                    channel.sendMessage("The list of available roles are: " + giveMeList.keySet().toString())
                        .queue();
                    break;
            
                case "delete":
                    if (!admins) {
                        return;
                    }
                    if (giveMeList.containsKey(role)) {
                        giveMeList.remove(role);
                        channel.sendMessage("The role "+role+" has been removed from the list")
                            .queue();
                    }
                    break;
            
                case "add":
                    if (!admins) {
                        return;
                    }
                    if (roles.isEmpty()) {
                        System.out.println("empty");
                        channel.sendMessage("The specified role could not be found")
                            .queue();
                    } else {
                        System.out.println(roles);
                        giveMeList.put(role, roles.get(0).getId());
                        channel.sendMessage("Added the role " + role + " to the list.")
                            .queue();
                    }
                    break;

                default:
                    if (giveMeList.containsKey(request)) {
                        event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(giveMeList.get(request).toString()))
                            .complete();

                        channel.sendMessage("Given to user the role " + request)
                            .queue();
                    } else {
                        channel.sendMessage("Unable to give role")
                            .queue();
                    }
                    break;

                case "remove":
                    if (giveMeList.containsKey(role)) {
                        event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(giveMeList.get(role).toString()))
                            .complete();

                        channel.sendMessage("Removed from user the role " + role)
                            .queue();
                    } else {
                        channel.sendMessage("Unable to remove role")
                            .queue();
                    }
                    break;

                case "create":
                    if (!admins) {
                        return;
                    }
                    if (roles.isEmpty()) {
                        Role newRole = event.getGuild().createRole()
                            .setName(role)
                            .complete();
                        giveMeList.put(role, newRole.getId());
                        channel.sendMessage("Created the role " + role)
                            .queue();
                    } else {
                        channel.sendMessage("A role already exists with that name!")
                            .queue();
                    }
                    break;
                    
            }
            try {
                FileWriter fileWriter = new FileWriter(jsonFile.toFile());
                fileWriter.write(giveMeList.toJSONString());
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Giveme(MessageReceivedEvent event, boolean admins, String request) throws Exception {
        this(event,admins,request,"null");
    }
}
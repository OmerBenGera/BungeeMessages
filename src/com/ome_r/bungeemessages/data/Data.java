package com.ome_r.bungeemessages.data;

import com.ome_r.bungeemessages.GroupChat;
import com.ome_r.bungeemessages.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Data {

    private File file = new File("plugins/BungeeMessages/groups.yml");
    private List<GroupChat> groups;

    public Data(){
        groups = new ArrayList<>();
        loadData();
    }

    public void loadData(){
        try{
            createFile(file);

            Configuration cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);

            if(cfg.getSection("groups") != null){
                for(String group : cfg.getSection("groups").getKeys())
                    if(!group.contains("."))
                    {
                        String path = "groups." + group, command = cfg.getString(path + ".command"),
                                permission = cfg.getString(path + ".permission"),
                                format = translateColors(cfg.getString(path + ".format"));

                        groups.add(new GroupChat(group, command, permission, format));
                    }
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public GroupChat getGroupChat(String command){
        for(GroupChat groupChat : groups)
            if(groupChat.getCommand().equalsIgnoreCase(command))
                return groupChat;

        return null;
    }

    public GroupChat getGroupChat(ProxiedPlayer pl){
        for(GroupChat groupChat : groups)
            if(groupChat.getPlayers().contains(pl))
                return groupChat;

        return null;
    }

    public List<GroupChat> getGroups(){
        return new ArrayList<>(groups);
    }

    private String translateColors(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private void createFile(File file) throws IOException{
        if (!file.getParentFile().exists())
            file.getParentFile().mkdir();

        if (!file.exists()) {
            InputStream in = Main.getInstance().getResourceAsStream("groups.yml");
            Files.copy(in, file.toPath());
        }
    }

}

package com.ome_r.bungeemessages;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

public class GroupChat {

    private List<ProxiedPlayer> players;
    private String name, command, permission, format;

    public GroupChat(String name, String command, String permission, String format){
        this.name = name;
        this.command = command;
        this.permission = permission;
        this.format = format;
        players = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getCommand(){
        return command;
    }

    public String getPermission() {
        return permission;
    }

    public String getFormat(Object... objects){
        String format = this.format;
        int counter = 0;

        for(Object obj : objects){
            format = format.replace("{" + counter + "}", obj.toString());
            counter++;
        }

        return format;
    }

    public List<ProxiedPlayer> getPlayers(){
        return players;
    }

}

package com.ome_r.bungeemessages;

import com.ome_r.bungeemessages.commands.CmdMsg;
import com.ome_r.bungeemessages.commands.CmdRespond;
import com.ome_r.bungeemessages.commands.CmdSpy;
import com.ome_r.bungeemessages.commands.GroupCommandsHandler;
import com.ome_r.bungeemessages.data.Data;
import com.ome_r.bungeemessages.data.Messages;
import com.ome_r.bungeemessages.listeners.ChatListener;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Level;

public class Main extends Plugin {

    private static Main instance;
    private static Data data;

    @Override
    public void onEnable() {
        setupClasses();
        loadListeners();
        loadCommands();
        Messages.loadMessages();
    }

    private void loadListeners(){
        new ChatListener(this);
    }

    private void loadCommands(){
        new CmdMsg(this);
        new CmdRespond(this);
        new CmdSpy(this);
        for(GroupChat group : data.getGroups()) {
            new GroupCommandsHandler(this, group.getCommand());
            getLogger().log(Level.INFO, "Successfully loaded the group-chat " + group.getName() + ".");
        }
    }

    private void setupClasses(){
        instance = this;
        data = new Data();
    }

    public static Main getInstance(){
        return instance;
    }

    public static Data getData(){
        return data;
    }

}

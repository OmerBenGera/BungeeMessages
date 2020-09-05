package com.ome_r.bungeemessages.listeners;

import com.ome_r.bungeemessages.GroupChat;
import com.ome_r.bungeemessages.Main;
import com.ome_r.bungeemessages.MessagesHandler;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class ChatListener implements Listener {

    public ChatListener(Plugin plugin){
        ProxyServer.getInstance().getPluginManager().registerListener(plugin, this);
    }

    @EventHandler
    public void onChat(ChatEvent e){
        if(e.isCommand()) return;

        ProxiedPlayer p = (ProxiedPlayer) e.getSender();
        GroupChat group = Main.getData().getGroupChat(p);

        if(group != null)
        {
            e.setCancelled(true);
            MessagesHandler.sendGroupMessage(p, group, e.getMessage());
        }

        else if(MessagesHandler.playerGroups.containsKey(p)){
            e.setCancelled(true);
            MessagesHandler.sendPrivateMessage(p, MessagesHandler.playerGroups.get(p), e.getMessage());
        }

    }

}

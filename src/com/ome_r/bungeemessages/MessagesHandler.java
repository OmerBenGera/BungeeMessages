package com.ome_r.bungeemessages;

import com.ome_r.bungeemessages.data.Messages;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessagesHandler {

    public static Map<ProxiedPlayer, String> responds = new HashMap<>();
    public static Map<ProxiedPlayer, String> playerGroups = new HashMap<>();
    public static List<ProxiedPlayer> spyPlayers = new ArrayList<>();

    public static void sendPrivateMessage(ProxiedPlayer sender, String targetName, String message){
        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(targetName);
        if(target == null || !target.isConnected()){
            sender.sendMessage(new TextComponent(Messages.RESPOND_OFFLINE.getMessage(targetName)));

            if(playerGroups.containsKey(sender) && playerGroups.get(sender).equals(targetName)){
                playerGroups.remove(sender);
                sender.sendMessage(new TextComponent(Messages.UNLOCK_FROM_PLAYER.getMessage(targetName)));
            }

            return;
        }

        if(sender.hasPermission("bungeemessages.colors") || sender.hasPermission("bungeemessages.*"))
            message = ChatColor.translateAlternateColorCodes('&', message);

        sender.sendMessage(new TextComponent(Messages.SEND_PRIVATE_MESSAGE.getMessage(
                target.getServer().getInfo().getName(),
                target.getName(),
                message)));

        target.sendMessage(new TextComponent(Messages.RECIEVE_PRIVATE_MESSAGE.getMessage(
                sender.getServer().getInfo().getName(),
                sender.getName(),
                message)));

        for(ProxiedPlayer pl : spyPlayers){
            if(!pl.equals(sender) && !pl.equals(target))
                pl.sendMessage(new TextComponent(Messages.SPY_PRIVATE_MESSAGES.getMessage(
                        sender.getServer().getInfo().getName(),
                        sender.getName(),
                        target.getServer().getInfo().getName(),
                        target.getName(),
                        message)));
        }

        responds.put(sender, target.getName());
        responds.put(target, sender.getName());
    }

    public static void sendGroupMessage(CommandSender sender, GroupChat group, String message){
        if(sender.hasPermission("bungeemessages.colors") || sender.hasPermission("bungeemessages.*"))
            message = ChatColor.translateAlternateColorCodes('&', message);

        for(ProxiedPlayer pl : ProxyServer.getInstance().getPlayers()){
            if(pl.hasPermission(group.getPermission()) || pl.hasPermission("bungeemessages.*"))
                pl.sendMessage(new TextComponent(group.getFormat(sender.getName(), message)));
        }
    }

}

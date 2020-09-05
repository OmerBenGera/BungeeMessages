package com.ome_r.bungeemessages.commands;

import com.ome_r.bungeemessages.GroupChat;
import com.ome_r.bungeemessages.Main;
import com.ome_r.bungeemessages.MessagesHandler;
import com.ome_r.bungeemessages.data.Messages;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

public class GroupCommandsHandler extends Command{

    private String command;

    public GroupCommandsHandler(Plugin plugin, String command){
        super(command);
        this.command = command;
        ProxyServer.getInstance().getPluginManager().registerCommand(plugin, this);
    }

    /*
    /<group-chat> [message]
     */
    @Override
    public void execute(CommandSender sender, String[] args) {
        GroupChat groupChat = Main.getData().getGroupChat(command);

        if(!sender.hasPermission(groupChat.getPermission()) && !sender.hasPermission("bungeemessages.*")) {
            sender.sendMessage(Messages.NO_PERMISSION.getMessage());
            return;
        }

        if(args.length == 0){
            if(!(sender instanceof ProxiedPlayer)){
                sender.sendMessage(new TextComponent("§cYou cannot lock yourself in group-chats."));
                return;
            }

            ProxiedPlayer p = (ProxiedPlayer) sender;

            if(Main.getData().getGroupChat(p) != null){
                GroupChat group = Main.getData().getGroupChat(p);
                group.getPlayers().remove(p);
                p.sendMessage(new TextComponent(Messages.UNLOCK_FROM_GROUPCHAT.getMessage(group.getName())));
            }

            else if(groupChat.getPlayers().contains(p)){
                groupChat.getPlayers().remove(p);
                p.sendMessage(new TextComponent(Messages.UNLOCK_FROM_GROUPCHAT.getMessage(groupChat.getName())));
            }

            else{
                groupChat.getPlayers().add(p);
                p.sendMessage(new TextComponent(Messages.LOCK_IN_GROUPCHAT.getMessage(groupChat.getName())));

                if(MessagesHandler.playerGroups.containsKey(p))
                    MessagesHandler.playerGroups.remove(p);

            }

        }

        else{
            String message = "";

            for(int i = 0; i < args.length; i++){
                message += args[i] + " ";
            }

            MessagesHandler.sendGroupMessage(sender, groupChat, message);
        }

    }
}

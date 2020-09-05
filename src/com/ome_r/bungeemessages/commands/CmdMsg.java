package com.ome_r.bungeemessages.commands;

import com.ome_r.bungeemessages.Main;
import com.ome_r.bungeemessages.MessagesHandler;
import com.ome_r.bungeemessages.data.Messages;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

public class CmdMsg extends Command {

    public CmdMsg(Plugin plugin){
        super("msg", null, "tell");
        ProxyServer.getInstance().getPluginManager().registerCommand(plugin, this);
    }

    /*
    /msg <player-name> [message]
     */

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)){
            sender.sendMessage(new TextComponent("§cOnly players can use perform command."));
            return;
        }

        ProxiedPlayer p = (ProxiedPlayer) sender;

        if(!p.hasPermission("bungeemessages.msg") && !p.hasPermission("bungeemessages.*")){
            p.sendMessage(new TextComponent(Messages.NO_PERMISSION.getMessage()));
            return;
        }

        if(args.length == 0){
            p.sendMessage(new TextComponent(Messages.COMMAND_USAGE.getMessage("/msg <player-name> [message]")));
            return;
        }

        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);

        if(target == null || !target.isConnected()){
            sender.sendMessage(new TextComponent(Messages.INVALID_PLAYER.getMessage(args[0])));
            return;
        }

        if(p.equals(target)){
            sender.sendMessage(new TextComponent(Messages.SELF_MESSAGE.getMessage()));
            return;
        }

        if(args.length == 1){

            if(!MessagesHandler.playerGroups.containsKey(p) || !MessagesHandler.playerGroups.get(p).equals(target.getName())){
                MessagesHandler.playerGroups.put(p, target.getName());
                p.sendMessage(new TextComponent(Messages.LOCK_IN_PLAYER.getMessage(target.getName())));

                if(Main.getData().getGroupChat(p) != null)
                    Main.getData().getGroupChat(p).getPlayers().remove(p);

            }

            else{
                MessagesHandler.playerGroups.remove(p, target);
                p.sendMessage(new TextComponent(Messages.UNLOCK_FROM_PLAYER.getMessage(target.getName())));
            }
        }

        else{
            String message = "";

            for(int i = 1; i < args.length; i++){
                message += args[i] + " ";
            }

            MessagesHandler.sendPrivateMessage(p, target.getName(), message);
        }

    }
}

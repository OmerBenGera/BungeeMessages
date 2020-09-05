package com.ome_r.bungeemessages.commands;

import com.ome_r.bungeemessages.MessagesHandler;
import com.ome_r.bungeemessages.data.Messages;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

public class CmdSpy extends Command {

    public CmdSpy(Plugin plugin){
        super("spy");
        ProxyServer.getInstance().getPluginManager().registerCommand(plugin, this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)){
            sender.sendMessage(new TextComponent("§cOnly players can use perform command."));
            return;
        }

        ProxiedPlayer p = (ProxiedPlayer) sender;

        if(!p.hasPermission("bungeemessages.spy") && !p.hasPermission("bungeemessages.*")){
            p.sendMessage(new TextComponent(Messages.NO_PERMISSION.getMessage()));
            return;
        }

        if(args.length != 0){
            p.sendMessage(new TextComponent(Messages.COMMAND_USAGE.getMessage("/spy")));
            return;
        }

        if(!MessagesHandler.spyPlayers.contains(p)){
            p.sendMessage(new TextComponent(Messages.SPY_ENABLED.getMessage()));
            MessagesHandler.spyPlayers.add(p);
        }

        else{
            p.sendMessage(new TextComponent(Messages.SPY_DISABLED.getMessage()));
            MessagesHandler.spyPlayers.remove(p);
        }

    }
}

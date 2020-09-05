package com.ome_r.bungeemessages.data;

import com.ome_r.bungeemessages.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public enum Messages {

    NO_PERMISSION, LOCK_IN_GROUPCHAT, UNLOCK_FROM_GROUPCHAT, COMMAND_USAGE, LOCK_IN_PLAYER, UNLOCK_FROM_PLAYER,
    INVALID_PLAYER, SEND_PRIVATE_MESSAGE, RECIEVE_PRIVATE_MESSAGE, SPY_PRIVATE_MESSAGES, INVALID_RESPOND,
    RESPOND_OFFLINE, SPY_ENABLED, SPY_DISABLED, SELF_MESSAGE;

    private String message;

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(Object... objects) {
        String str = new String(message);
        int counter = 0;

        for(Object obj : objects) {
            str = str.replace("{" + counter + "}", obj.toString());
            counter++;
        }

        return str;
    }

    public String getMessage() {
        return message;
    }

    public static void loadMessages(){
        try{
            File file = new File("plugins/BungeeMessages/messages.yml");
            createFile(file);

            Configuration cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);

            for(String str : cfg.getKeys()) {
                Messages.valueOf(str).setMessage(translateColors(cfg.getString(str)));
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void createFile(File file) throws IOException {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdir();

        if (!file.exists()) {
            InputStream in = Main.getInstance().getResourceAsStream("messages.yml");
            Files.copy(in, file.toPath());
        }
    }

    private static String translateColors(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}

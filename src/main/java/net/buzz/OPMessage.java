package net.buzz;

import net.buzz.commands.*;
import net.buzz.listeners.QuitEvent;
import net.buzz.listeners.TabComplete;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public final class OPMessage extends Plugin {
    public static OPMessage instance;

    private Configuration mainConfiguration;

    public static List<ProxiedPlayer> staffChat = new ArrayList<>();

    public void onEnable() {
        instance = this;
        loadConfigurations();
        getProxy().getPluginManager().registerCommand(this, (Command)new Reply(this));
        getProxy().getPluginManager().registerCommand(this, (Command)new Message(this));
        getProxy().getPluginManager().registerCommand(this, (Command)new Toggle(this));
        getProxy().getPluginManager().registerCommand(this, (Command)new SocialSpy(this));
        getProxy().getPluginManager().registerCommand(this, (Command)new Reload(this));
        getProxy().getPluginManager().registerListener(this, (Listener)new QuitEvent());
        getProxy().getPluginManager().registerListener(this, (Listener)new TabComplete());
        getLogger().info("");
        getLogger().info("OPMessage has been successfully enabled!");
        getLogger().info("OPMessage by buzz#9999");
        getLogger().info("");
    }

    public void onDisable() {
        getLogger().info("OPMessage has been successfully disabled!");
        getLogger().info("OPMessage by buzz#9999");
    }

    public void loadConfigurations() {
        try {
            if (!getDataFolder().exists())
                getLogger().info("OPMessage Folder Does Not Exist! Attempting to create!");
            getDataFolder().mkdir();
            File configFile = new File(getDataFolder(), "config.yml");
            if (!configFile.exists()) {
                getLogger().info("Config File Does Not Exist... Attempting to create!");
                try (InputStream in = getResourceAsStream("config.yml")) {
                    Files.copy(in, configFile.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            mainConfiguration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Configuration getMainConfiguration() {
        return this.mainConfiguration;
    }

    public static OPMessage getInstance() {
        return instance;
    }

    public static void setInstance(OPMessage instance) {
        OPMessage.instance = instance;
    }
}

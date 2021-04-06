package com.oskarsmc.transfer;

import com.oskarsmc.transfer.configuration.ConfigManager;
import com.oskarsmc.transfer.listener.MessageListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class TransferSpigot extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        ConfigManager.reloadConfig();

        if (ConfigManager.getTransferConfig().isPluginEnabled()) {
            Bukkit.getPluginManager().registerEvents(new MessageListener(), this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

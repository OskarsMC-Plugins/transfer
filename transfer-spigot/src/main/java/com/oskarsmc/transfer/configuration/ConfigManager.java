package com.oskarsmc.transfer.configuration;

import com.oskarsmc.transfer.TransferSpigot;

public class ConfigManager {
    private static final TransferSpigot PLUGIN = TransferSpigot.getPlugin(TransferSpigot.class);
    private static TransferConfig transferConfig;

    public static void reloadConfig() {
        PLUGIN.getConfig().options().copyDefaults(true);
        PLUGIN.saveConfig();
        transferConfig = new TransferConfig(PLUGIN.getConfig());
    }

    public static TransferConfig getTransferConfig() {
        return transferConfig;
    }
}

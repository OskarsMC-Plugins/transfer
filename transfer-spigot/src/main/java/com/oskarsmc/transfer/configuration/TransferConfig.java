package com.oskarsmc.transfer.configuration;

import org.bukkit.configuration.file.FileConfiguration;

public class TransferConfig {
    private final boolean pluginEnabled;

    public TransferConfig(FileConfiguration fileConfiguration) {
        this.pluginEnabled = fileConfiguration.getBoolean("plugin-enabled");;
    }

    public boolean isPluginEnabled() {
        return pluginEnabled;
    }
}

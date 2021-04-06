package com.oskarsmc.transfer.configuration;

import com.moandjiezana.toml.Toml;
import net.kyori.adventure.text.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

public class TransferSettings {
    private final File dataFolder;
    private final File file;

    private final String newServerQuitMiniMessage;
    private final String serverQuitMiniMessage;

    private final List<String> blacklistedServers;

    private final Double configVersion;
    private boolean enabled;

    public TransferSettings(File dataFolder) {
        this.dataFolder = dataFolder;
        this.file = new File(this.dataFolder, "config.toml");

        saveDefaultConfig();
        Toml toml = loadConfig();

        this.enabled = toml.getBoolean("plugin.enabled");

        this.newServerQuitMiniMessage = toml.getString("messages.quit-message-new-server");
        this.serverQuitMiniMessage = toml.getString("messages.quit-message");

        this.blacklistedServers = toml.getList("servers.quit-message-disabled");

        this.configVersion = toml.getDouble("developer-info.config-version");
    }

    private void saveDefaultConfig() {
        if (!dataFolder.exists()) dataFolder.mkdir();
        if (file.exists()) return;

        try (InputStream in = TransferSettings.class.getResourceAsStream("/config.toml")) {
            Files.copy(in, file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File getConfigFile() {
        return new File(dataFolder, "config.toml");
    }

    private Toml loadConfig() {
        return new Toml().read(getConfigFile());
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Double getConfigVersion() {
        return configVersion;
    }

    public String getServerQuitMiniMessage() {
        return serverQuitMiniMessage;
    }

    public String getNewServerQuitMiniMessage() {
        return newServerQuitMiniMessage;
    }

    public List<String> getBlacklistedServers() {
        return blacklistedServers;
    }
}

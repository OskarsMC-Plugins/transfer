package com.oskarsmc.transfer;

import com.google.inject.Inject;
import com.oskarsmc.transfer.configuration.TransferSettings;
import com.oskarsmc.transfer.listener.QuitListener;
import com.oskarsmc.transfer.util.VersionUtils;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
        id = "transfer-velocity",
        name = "transfer",
        version = "0.1.0",
        description = "Move quit Messages to Velocity",
        url = "https://software.oskarsmc.com/",
        authors = {"OskarsMC", "OskarZyg"}
)
public class TransferVelocity {

    @Inject
    private Logger logger;

    @Inject
    private ProxyServer proxyServer;

    @Inject
    private @DataDirectory
    Path dataDirectory;

    private TransferSettings transferSettings;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        this.transferSettings = new TransferSettings(dataDirectory.toFile());

        if (!VersionUtils.isLatestConfigVersion(transferSettings)) {
            logger.warn("Your Config is out of date (Latest: " + VersionUtils.CONFIG_VERSION + ", Config Version: " + transferSettings.getConfigVersion() + ")!");
            logger.warn("Please backup your current config.toml, and delete the current one. A new config will then be created on the next proxy launch.");
            logger.warn("The plugin's functionality will not be enabled until the config is updated.");
            transferSettings.setEnabled(false);
        }

        if (transferSettings.isEnabled()) {
            this.proxyServer.getEventManager().register(this, new QuitListener(this.transferSettings));
        }
    }
}

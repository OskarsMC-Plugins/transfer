package com.oskarsmc.transfer.listener;

import com.oskarsmc.transfer.configuration.TransferSettings;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.HashMap;
import java.util.Map;

public class QuitListener {
    private final TransferSettings transferSettings;

    public QuitListener(TransferSettings transferSettings) {
        this.transferSettings = transferSettings;
    }

    @Subscribe
    public void serverConnected(ServerConnectedEvent event) {
        if (event.getPreviousServer().isPresent()) {
            RegisteredServer previousServer = event.getPreviousServer().get();

            if (!transferSettings.getBlacklistedServers().contains(previousServer.getServerInfo().getName())) {
                Player player = event.getPlayer();

                MiniMessage miniMessage = MiniMessage.get();

                Map<String, String> placeholders = new HashMap<String, String>();

                placeholders.put("player", player.getUsername());
                placeholders.put("old_server", previousServer.getServerInfo().getName());
                placeholders.put("new_server", event.getServer().getServerInfo().getName());

                previousServer.sendMessage(miniMessage.parse(transferSettings.getNewServerQuitMiniMessage(), placeholders));
            }
        }
    }

    @Subscribe
    public void serverDisconnected(DisconnectEvent event) {
        if (event.getLoginStatus().equals(DisconnectEvent.LoginStatus.SUCCESSFUL_LOGIN) && event.getPlayer().getCurrentServer().isPresent()) {
            RegisteredServer registeredServer = event.getPlayer().getCurrentServer().get().getServer();

            MiniMessage miniMessage = MiniMessage.get();

            Map<String, String> placeholders = new HashMap<String, String>();

            placeholders.put("player", event.getPlayer().getUsername());
            placeholders.put("old_server", registeredServer.getServerInfo().getName());
            placeholders.put("new_server", "");

            registeredServer.sendMessage(miniMessage.parse(transferSettings.getServerQuitMiniMessage(), placeholders));
        }
    }
}

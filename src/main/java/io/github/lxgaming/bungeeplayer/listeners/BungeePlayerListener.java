/*
 * Copyright 2018 Alex Thomson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.lxgaming.bungeeplayer.listeners;

import io.github.lxgaming.bungeeplayer.BungeePlayer;
import io.github.lxgaming.bungeeplayer.data.Player;
import io.github.lxgaming.bungeeplayer.managers.ChannelManager;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class BungeePlayerListener implements Listener {
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPostLogin(PostLoginEvent event) {
        Player player = new Player(event.getPlayer().getUniqueId(), event.getPlayer().getPendingConnection().getVersion());
        BungeePlayer.getInstance().getPlayers().add(player);
        ChannelManager.addChannel(player, event.getPlayer());
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onServerConnected(ServerConnectedEvent event) {
        Player player = BungeePlayer.getInstance().getPlayer(event.getPlayer().getUniqueId());
        if (player != null) {
            player.setServer(event.getServer().getInfo().getName());
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        BungeePlayer.getInstance().getPlayers().removeIf(player -> player.getUniqueId().equals(event.getPlayer().getUniqueId()));
        ChannelManager.removeChannel(event.getPlayer());
    }
}
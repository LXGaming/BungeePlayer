/*
 * Copyright 2017 Alex Thomson
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

package io.github.lxgaming.bungeeplayer;

import java.lang.reflect.InvocationTargetException;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class BungeePlayerListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPostLogin(PostLoginEvent event) {
		BungeePlayer.getInstance().debugMessage("Events", "PostLoginEvent");
		
		if (!BungeePlayer.getInstance().getConfig().getConfiguration().getIntList("BungeePlayer.SupportedProtocols").contains(event.getPlayer().getPendingConnection().getVersion())) {
			return;
		}
		
		try {
			if (BungeePlayer.getInstance().getData().getPlayer(event.getPlayer().getUniqueId()) != null) {
				BungeePlayer.getInstance().getBungeePacket().getChannelHandler().removeChannel(event.getPlayer(), "bungeeplayer_packet_handler");
				BungeePlayer.getInstance().getData().removePlayer(event.getPlayer().getUniqueId());
				BungeePlayer.getInstance().debugMessage("Events", "Player was removed from data as they already existed!");
			}
			
			BungeePlayer.getInstance().getBungeePacket().getChannelHandler().addChannel(event.getPlayer(), "inbound-boss", "bungeeplayer_packet_handler", new ChannelHandler(event.getPlayer().getUniqueId()));
			BungeePlayer.getInstance().getData().addPlayer(new Player(event.getPlayer()));
		} catch (ExceptionInInitializerError | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException | NoSuchMethodException | NullPointerException | SecurityException ex) {
			BungeePlayer.getInstance().getLogger().warning("An error occurred during player post login!");
			ex.printStackTrace();
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onServerConnected(ServerConnectedEvent event) {
		BungeePlayer.getInstance().debugMessage("Events", "ServerConnectedEvent");
		if (event.getPlayer() != null && event.getServer() != null && BungeePlayer.getInstance().getData().getPlayer(event.getPlayer().getUniqueId()) != null) {
			BungeePlayer.getInstance().getData().getPlayer(event.getPlayer().getUniqueId()).getLocation().setServer(event.getServer().getInfo().getName());
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerDisconnect(PlayerDisconnectEvent event) {
		BungeePlayer.getInstance().debugMessage("Events", "PlayerDisconnectedEvent");
		try {
			if (BungeePlayer.getInstance().getData().getPlayer(event.getPlayer().getUniqueId()) != null) {
	 			BungeePlayer.getInstance().getBungeePacket().getChannelHandler().removeChannel(event.getPlayer(), "bungeeplayer_packet_handler");
				BungeePlayer.getInstance().getData().removePlayer(event.getPlayer().getUniqueId());
				return;
			}
			
			BungeePlayer.getInstance().debugMessage("Events", "Data did not contain the player!");
		} catch (ExceptionInInitializerError | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException | NoSuchMethodException | NullPointerException | SecurityException ex) {
			BungeePlayer.getInstance().getLogger().warning("An error occurred during player disconnect!");
			ex.printStackTrace();
		}
	}
}

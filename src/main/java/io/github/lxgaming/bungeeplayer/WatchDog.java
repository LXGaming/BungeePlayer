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
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class WatchDog {
	
	private final int sleep = 300000;
	private final int clear = 300000;
	
	protected void loadWatchDog() {		
		ProxyServer.getInstance().getScheduler().schedule(BungeePlayer.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				if (BungeePlayer.getInstance().getData() == null || BungeePlayer.getInstance().getData().getPlayers().isEmpty()) {
					return;
				}
				
				for (Iterator<Entry<UUID, Player>> iterator = BungeePlayer.getInstance().getData().getPlayers().entrySet().iterator(); iterator.hasNext();) {
					Entry<UUID, Player> entry = iterator.next();
					if (entry.getValue() == null || entry.getValue().getLastUpdate() == 0) {
						continue;
					}
					
					if (entry.getValue().getProxiedPlayer() != null && !entry.getValue().getProxiedPlayer().isConnected()) {
						removePlayer(entry.getKey(), entry.getValue().getProxiedPlayer());
						BungeePlayer.getInstance().debugMessage("WatchDog", "Removed player '" + entry.getValue().getProxiedPlayer().getName() + "' As they are not connected!");
						continue;
					}
					
					if (entry.getValue().getLastUpdate() != 0 && entry.getValue().getLastUpdate() < (System.currentTimeMillis() - clear)) {
						removePlayer(entry.getKey(), entry.getValue().getProxiedPlayer());
						BungeePlayer.getInstance().debugMessage("WatchDog", "Removed player '" + entry.getValue().getProxiedPlayer().getName() + "' As they havn't been updated in a while!");
						continue;
					}
				}
			}
			
			private void removePlayer(UUID uniqueId, ProxiedPlayer proxiedPlayer) {
				try {
					BungeePlayer.getInstance().getBungeePacket().getChannelHandler().removeChannel(proxiedPlayer, "bungeeplayer_packet_handler");
					BungeePlayer.getInstance().getData().removePlayer(uniqueId);
				} catch (ExceptionInInitializerError | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException | NoSuchMethodException | NullPointerException | SecurityException ex) {
					BungeePlayer.getInstance().getLogger().warning("An error occurred during watchdog remove!");
					ex.printStackTrace();
				}
			}
		}, 0L, sleep, TimeUnit.MILLISECONDS);
		return;
	}
	
	protected void stopWatchDog() {
		ProxyServer.getInstance().getScheduler().cancel(BungeePlayer.getInstance());
	}
}

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

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.UUID;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Data {
	
	private final HashMap<UUID, Player> players;
	
	protected Data() {
		this.players = new LinkedHashMap<UUID, Player>();
	}
	
	protected HashMap<UUID, Player> getPlayers() {
		return this.players;
	}
	
	protected boolean addPlayer(Player player) {
		if (player.getProxiedPlayer() != null && !this.players.containsKey(player.getProxiedPlayer().getUniqueId())) {
			this.players.put(player.getProxiedPlayer().getUniqueId(), player);
			return true;
		}
		return false;
	}
	
	protected boolean addPlayer(UUID uniqueId, Player player) {
		if (!this.players.containsKey(uniqueId)) {
			this.players.put(uniqueId, player);
			return true;
		}
		return false;
	}
	
	protected boolean removePlayer(UUID uniqueId) {
		if (this.players.containsKey(uniqueId)) {
			this.players.remove(uniqueId);
			return true;
		}
		return false;
	}
	
	public Player getPlayer(UUID uniqueId) {
		return this.players.get(uniqueId);
	}
	
	public Player getPlayer(ProxiedPlayer proxiedPlayer) {
		return getPlayer(proxiedPlayer.getUniqueId());
	}
	
	public Player getPlayer(String name) {
		for (Iterator<Entry<UUID, Player>> iterator = this.players.entrySet().iterator(); iterator.hasNext();) {
			Entry<UUID, Player> entry = iterator.next();
			if (entry.getValue().getProxiedPlayer() != null && entry.getValue().getProxiedPlayer().getName().equals(name)) {
				return this.players.get(entry.getKey());
			}
		}
		return null;
	}
}

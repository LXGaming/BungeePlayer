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

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Player {
	
	private ProxiedPlayer proxiedPlayer;
	private Location location;
	private long lastUpdate = 0;
	
	/**
	 * Create a new Player with the given ProxiedPlayer.
	 * 
	 * @param proxiedPlayer the ProxiedPlayer
	 */
	protected Player(ProxiedPlayer proxiedPlayer) {
		update();
		this.proxiedPlayer = proxiedPlayer;
		this.location = new Location();
	}
	
	/**
	 * Create a new Player with the given ProxiedPlayer and Location.
	 * 
	 * @param proxiedPlayer the ProxiedPlayer
	 * @param location the Location
	 */
	protected Player(ProxiedPlayer proxiedPlayer, Location location) {
		update();
		this.proxiedPlayer = proxiedPlayer;
		this.location = location;
	}
	
	/**
	 * Set the ProxiedPlayer.
	 * 
	 * @param proxiedPlayer the ProxiedPlayer
	 */
	protected void setProxiedPlayer(ProxiedPlayer proxiedPlayer) {
		update();
		this.proxiedPlayer = proxiedPlayer;
	}
	
	/**
	 * Get the ProxiedPlayer.
	 * 
	 * @return ProxiedPlayer
	 */
	public ProxiedPlayer getProxiedPlayer() {
		update();
		return this.proxiedPlayer;
	}
	
	/**
	 * Get the Location.
	 * 
	 * @return Location
	 */
	public Location getLocation() {
		update();
		return this.location;
	}
	
	/**
	 * Update the lastUpdate.
	 */
	private void update() {
		this.lastUpdate = System.currentTimeMillis();
	}
	
	/**
	 * Get the lastUpdate epoch time.
	 * 
	 * @return long
	 */
	protected long getLastUpdate() {
		return this.lastUpdate;
	}
}

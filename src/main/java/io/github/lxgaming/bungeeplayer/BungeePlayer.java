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

import io.github.lxgaming.bungeepacket.BungeePacket;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeePlayer extends Plugin {
	
	private static BungeePlayer instance;
	private static BungeePlayerAPI bungeePlayerAPI;
	private BungeePlayerConfig config;
	private BungeePacket bungeePacket;
	private WatchDog watchDog;
	private Data data;
	
	@Override
	public void onEnable() {
		instance = this;
		bungeePlayerAPI = new BungeePlayerAPI();
		reloadConfig();
		bungeePacket = new BungeePacket();
		watchDog = new WatchDog();
		watchDog.loadWatchDog();
		data = new Data();
		getProxy().getPluginManager().registerCommand(this, new BungeePlayerCommand());
		getProxy().getPluginManager().registerListener(this, new BungeePlayerListener());
		getLogger().info("BungeePlayer has started.");
	}
	
	@Override
	public void onDisable() {
		data.getPlayers().clear();
		watchDog.stopWatchDog();
		getLogger().info("BungeePlayer has stopped.");
	}
	
	protected static BungeePlayer getInstance() {
		return instance;
	}
	
	public static BungeePlayerAPI getApi() {
		return bungeePlayerAPI;
	}
	
	protected BungeePlayerConfig getConfig() {
		return this.config;
	}
	
	protected void reloadConfig() {
		if (config == null) {
			this.config = new BungeePlayerConfig();
		}
		this.config.loadConfig();
	}
	
	protected void debugMessage(String type, String message) {
		if (config.getConfiguration() != null && config.getConfiguration().getBoolean("BungeePlayer.Debug." + type)) {
			getLogger().info(message);
		}
	}
	
	protected BungeePacket getBungeePacket() {
		return this.bungeePacket;
	}
	
	protected WatchDog getWatchDog() {
		return this.watchDog;
	}
	
	public Data getData() {
		return this.data;
	}
}

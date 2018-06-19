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

import io.github.lxgaming.bungeeplayer.api.IBungeePlayerAPI;
import io.github.lxgaming.bungeeplayer.commands.BungeePlayerCommand;
import io.github.lxgaming.bungeeplayer.configuration.Config;
import io.github.lxgaming.bungeeplayer.data.Player;
import io.github.lxgaming.bungeeplayer.listeners.BungeePlayerListener;
import io.github.lxgaming.bungeeplayer.managers.PacketManager;
import io.github.lxgaming.bungeeplayer.util.Toolbox;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BungeePlayer extends Plugin implements IBungeePlayerAPI {
    
    private static BungeePlayer instance;
    private final Config config = new Config();
    private final List<Player> players = Collections.synchronizedList(Toolbox.newArrayList());
    
    @Override
    public void onEnable() {
        instance = this;
        getConfig().loadConfig();
        PacketManager.registerPackets();
        getProxy().getPluginManager().registerCommand(getInstance(), new BungeePlayerCommand());
        getProxy().getPluginManager().registerListener(getInstance(), new BungeePlayerListener());
        getLogger().info("BungeePlayer has started.");
    }
    
    @Override
    public void onDisable() {
        instance = null;
        getPlayers().clear();
        getLogger().info("BungeePlayer has stopped.");
    }
    
    @Override
    public Player getPlayer(UUID uniqueId) {
        for (Player player : getPlayers()) {
            if (player.getUniqueId().equals(uniqueId)) {
                return player;
            }
        }
        
        return null;
    }
    
    public void debugMessage(String message) {
        if (getConfiguration().map(configuration -> configuration.getBoolean("BungeePlayer.Debug")).orElse(false)) {
            getLogger().info(message);
        }
    }
    
    public static BungeePlayer getInstance() {
        return instance;
    }
    
    public static IBungeePlayerAPI getAPI() {
        return getInstance();
    }
    
    public Config getConfig() {
        return config;
    }
    
    public Optional<Configuration> getConfiguration() {
        if (getConfig() != null) {
            return Optional.ofNullable(getConfig().getConfiguration());
        }
        
        return Optional.empty();
    }
    
    public List<Player> getPlayers() {
        return players;
    }
}

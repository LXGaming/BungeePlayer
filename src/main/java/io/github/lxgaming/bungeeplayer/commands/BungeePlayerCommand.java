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

package io.github.lxgaming.bungeeplayer.commands;

import io.github.lxgaming.bungeeplayer.BungeePlayer;
import io.github.lxgaming.bungeeplayer.api.IPlayer;
import io.github.lxgaming.bungeeplayer.util.Toolbox;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BungeePlayerCommand extends Command {
    
    public BungeePlayerCommand() {
        super("bungeeplayer");
    }
    
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1 && sender.hasPermission("bungeeplayer.admin")) {
            ProxiedPlayer proxiedPlayer = ProxyServer.getInstance().getPlayer(args[0]);
            if (proxiedPlayer == null || !proxiedPlayer.isConnected()) {
                sender.sendMessage(Toolbox.getTextPrefix().append(args[0]).append(" is not online").color(ChatColor.RED).create());
                return;
            }
            
            IPlayer player = BungeePlayer.getAPI().getPlayer(proxiedPlayer.getUniqueId());
            if (player == null) {
                sender.sendMessage(Toolbox.getTextPrefix().append(args[0]).append(" not found").color(ChatColor.RED).create());
                return;
            }
            
            ComponentBuilder componentBuilder = new ComponentBuilder("");
            componentBuilder.append(proxiedPlayer.getName()).color(ChatColor.BLUE).bold(true)
                    .append(" (" + player.getProtocolVersion() + ")", ComponentBuilder.FormatRetention.NONE).color(ChatColor.WHITE).append("\n");
            componentBuilder.append("Position: ", ComponentBuilder.FormatRetention.NONE).color(ChatColor.DARK_GRAY)
                    .append(player.getX() + ", " + player.getY() + ", " + player.getZ()).color(ChatColor.WHITE).append("\n");
            componentBuilder.append("Rotation: ", ComponentBuilder.FormatRetention.NONE).color(ChatColor.DARK_GRAY)
                    .append(player.getYaw() + ", " + player.getPitch()).color(ChatColor.WHITE).append("\n");
            componentBuilder.append("Server: ", ComponentBuilder.FormatRetention.NONE).color(ChatColor.DARK_GRAY)
                    .append(player.getServer() + " (" + player.getDimension() + ")").color(ChatColor.WHITE);
            
            sender.sendMessage(componentBuilder.create());
            return;
        }
        
        sender.sendMessage(Toolbox.getPluginInformation().create());
    }
}
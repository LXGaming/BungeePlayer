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

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

public class BungeePlayerCommand extends Command {
	
	public BungeePlayerCommand() {
		super("bungeeplayer");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length == 1 && sender.hasPermission("BungeePlayer.Debug") && BungeePlayer.getInstance().getConfig().getConfiguration().getBoolean("BungeePlayer.Debug.BungeePlayerCommand")) {
			Player player = BungeePlayer.getInstance().getData().getPlayer(args[0]);
			
			if (player == null || player.getProxiedPlayer() == null|| player.getLocation() == null) {
				sender.sendMessage(new ComponentBuilder("Player does not exist!").color(ChatColor.RED).create());
				return;
			}
			
			sender.sendMessage(new ComponentBuilder("===== ").color(ChatColor.DARK_GRAY).append(player.getProxiedPlayer().getName()).color(ChatColor.AQUA).append(" =====").color(ChatColor.DARK_GRAY).create());
			sender.sendMessage(new ComponentBuilder("Position - ").color(ChatColor.DARK_GRAY).append(player.getLocation().getX() + ", " + player.getLocation().getY() + ", " + player.getLocation().getZ()).color(ChatColor.AQUA).create());
			sender.sendMessage(new ComponentBuilder("Look - ").color(ChatColor.DARK_GRAY).append(player.getLocation().getYaw() + ", " + player.getLocation().getPitch()).color(ChatColor.AQUA).create());
			sender.sendMessage(new ComponentBuilder("Server - ").color(ChatColor.DARK_GRAY).append(player.getLocation().getDimension() + ", " + player.getLocation().getServer()).color(ChatColor.AQUA).create());
			return;
		}
		sender.sendMessage(new ComponentBuilder("===== ").color(ChatColor.DARK_GRAY).append("BungeePlayer").color(ChatColor.AQUA).append(" =====").color(ChatColor.DARK_GRAY).create());
		sender.sendMessage(new ComponentBuilder("Version - ").color(ChatColor.DARK_GRAY).append("1.0.0").color(ChatColor.AQUA).create());
		sender.sendMessage(new ComponentBuilder("Author - ").color(ChatColor.DARK_GRAY).append("LX_Gaming").color(ChatColor.AQUA).create());
		return;
	}
}

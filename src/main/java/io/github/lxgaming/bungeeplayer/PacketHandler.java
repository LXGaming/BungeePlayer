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

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.md_5.bungee.protocol.AbstractPacketHandler;
import net.md_5.bungee.protocol.packet.Login;

public class PacketHandler extends AbstractPacketHandler {
	
	private final UUID uniqueId;
	
	protected PacketHandler(UUID uniqueId) {
		this.uniqueId = uniqueId;
	}
	
	@Override
	public void handle(Login login) {
		BungeePlayer.getInstance().debugMessage("Packets", "Packet 0x23 (35) - Join Game.");
		BungeePlayer.getInstance().getData().getPlayer(this.uniqueId).getLocation().setDimension(login.getDimension());
	}
	
	protected void handleServerPlayerPositionAndLook(ByteBuf byteBuf) {
		BungeePlayer.getInstance().debugMessage("Packets", "Packet 0x2E (46) - Player Position And Look.");
		double x = byteBuf.readDouble();
		double y = byteBuf.readDouble();
		double z = byteBuf.readDouble();
		float yaw = byteBuf.readFloat();
		float pitch = byteBuf.readFloat();
		Byte flags = byteBuf.readByte();
		
		if (flags.intValue() == 0) {
			BungeePlayer.getInstance().getData().getPlayer(this.uniqueId).getLocation().setX(x);
			BungeePlayer.getInstance().getData().getPlayer(this.uniqueId).getLocation().setY(y);
			BungeePlayer.getInstance().getData().getPlayer(this.uniqueId).getLocation().setZ(z);
			BungeePlayer.getInstance().getData().getPlayer(this.uniqueId).getLocation().setYaw(yaw);
			BungeePlayer.getInstance().getData().getPlayer(this.uniqueId).getLocation().setPitch(pitch);
		}
	}
	
	protected void handleServerRespawn(ByteBuf byteBuf) {
		BungeePlayer.getInstance().debugMessage("Packets", "Packet 0x33 (51) - Respawn.");
		int dimension = byteBuf.readInt();
		
		BungeePlayer.getInstance().getData().getPlayer(this.uniqueId).getLocation().setDimension(dimension);
	}
	
	protected void handleClientPosition(ByteBuf byteBuf) {
		BungeePlayer.getInstance().debugMessage("Packets", "Packet 0x0C (12) - Player Position.");
		double x = byteBuf.readDouble();
		double y = byteBuf.readDouble();
		double z = byteBuf.readDouble();
		
		BungeePlayer.getInstance().getData().getPlayer(this.uniqueId).getLocation().setX(x);
		BungeePlayer.getInstance().getData().getPlayer(this.uniqueId).getLocation().setY(y);
		BungeePlayer.getInstance().getData().getPlayer(this.uniqueId).getLocation().setZ(z);
	}
	
	protected void handleClientPlayerPositionAndLook(ByteBuf byteBuf) {
		BungeePlayer.getInstance().debugMessage("Packets", "Packet 0x0D (13) - Player Position And Look.");
		double x = byteBuf.readDouble();
		double y = byteBuf.readDouble();
		double z = byteBuf.readDouble();
		float yaw = byteBuf.readFloat();
		float pitch = byteBuf.readFloat();
		
		BungeePlayer.getInstance().getData().getPlayer(this.uniqueId).getLocation().setX(x);
		BungeePlayer.getInstance().getData().getPlayer(this.uniqueId).getLocation().setY(y);
		BungeePlayer.getInstance().getData().getPlayer(this.uniqueId).getLocation().setZ(z);
		BungeePlayer.getInstance().getData().getPlayer(this.uniqueId).getLocation().setYaw(yaw);
		BungeePlayer.getInstance().getData().getPlayer(this.uniqueId).getLocation().setPitch(pitch);
	}
	
	protected void handleClientLook(ByteBuf byteBuf) {
		BungeePlayer.getInstance().debugMessage("Packets", "Packet 0x0E (14) - Player Look.");
		float yaw = byteBuf.readFloat();
		float pitch = byteBuf.readFloat();
		
		BungeePlayer.getInstance().getData().getPlayer(this.uniqueId).getLocation().setYaw(yaw);
		BungeePlayer.getInstance().getData().getPlayer(this.uniqueId).getLocation().setPitch(pitch);
	}
}

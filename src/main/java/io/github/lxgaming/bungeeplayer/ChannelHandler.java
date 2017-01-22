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
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.protocol.DefinedPacket;
import net.md_5.bungee.protocol.PacketWrapper;

public class ChannelHandler extends ChannelDuplexHandler {
	
	private final UUID uniqueId;
	
	protected ChannelHandler(UUID uniqueId) {
		this.uniqueId = uniqueId;
	}
	
	@Deprecated
	protected ChannelHandler(ProxiedPlayer proxiedPlayer) {
		this.uniqueId = proxiedPlayer.getUniqueId();
	}
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		ByteBuf byteBuf = null;
		try {
			if (DefinedPacket.class.isAssignableFrom(msg.getClass())) {
				((DefinedPacket) msg).handle(new PacketHandler(this.uniqueId));
			}
			
			if (ByteBuf.class.isAssignableFrom(msg.getClass())) {
				byteBuf = ((ByteBuf) msg).copy();
				int packetId = DefinedPacket.readVarInt(byteBuf);
				
				/*
				 * Packet 0x2E (46)
				 * Player Position And Look
				 */
				if (packetId == 46) {
					new PacketHandler(this.uniqueId).handleServerPlayerPositionAndLook(byteBuf);
				}
				
				/*
				 * Packet 0x33 (51)
				 * Respawn
				 */
				if (packetId == 51) {
					new PacketHandler(this.uniqueId).handleServerRespawn(byteBuf);
				}
			}
			super.write(ctx, msg, promise);
		} catch (Exception ex) {
			BungeePlayer.getInstance().getLogger().severe("Exception during write!");
			ex.printStackTrace();
		} finally {
			if (byteBuf != null) {
				byteBuf.release();
				byteBuf = null;
			}
		}
		return;
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf byteBuf = null;
		try {
			if (PacketWrapper.class.isAssignableFrom(msg.getClass())) {
				byteBuf = ((PacketWrapper) msg).buf.copy();
				int packetId = DefinedPacket.readVarInt(byteBuf);
				
				/*
				 * Packet 0x0C (12)
				 * Player Position
				 */
				if (packetId == 12) {
					new PacketHandler(this.uniqueId).handleClientPosition(byteBuf);
				}
				
				/*
				 * Packet 0x0D (13)
				 * Player Position And Look
				 */
				if (packetId == 13) {
					new PacketHandler(this.uniqueId).handleClientPlayerPositionAndLook(byteBuf);
				}
				
				/*
				 * Packet 0x0E (14)
				 * Player Look
				 */
				if (packetId == 14) {
					new PacketHandler(this.uniqueId).handleClientLook(byteBuf);
				}
			}
			super.channelRead(ctx, msg);
		} catch (Exception ex) {
			BungeePlayer.getInstance().getLogger().severe("Exception during channelRead!");
			ex.printStackTrace();
		} finally {
			if (byteBuf != null) {
				byteBuf.release();
				byteBuf = null;
			}
		}
		return;
	}
}

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

package io.github.lxgaming.bungeeplayer.managers;

import io.github.lxgaming.bungeeplayer.data.Packet;
import io.github.lxgaming.bungeeplayer.data.Player;
import io.github.lxgaming.bungeeplayer.util.Toolbox;
import io.netty.buffer.ByteBuf;
import net.md_5.bungee.protocol.DefinedPacket;
import net.md_5.bungee.protocol.ProtocolConstants;

import java.util.List;

public final class PacketManager {
    
    private static final List<Packet> CLIENT_PACKETS = Toolbox.newArrayList();
    private static final List<Packet> SERVER_PACKETS = Toolbox.newArrayList();
    
    public static void registerPackets() {
        
        // ClientPlayerLook
        getClientPackets().add(new Packet(0x12, 404, 389, PacketManager::handleClientPlayerLook));
        getClientPackets().add(new Packet(0x10, 388, 386, PacketManager::handleClientPlayerLook));
        getClientPackets().add(new Packet(0x0E, 385, 343, PacketManager::handleClientPlayerLook));
        getClientPackets().add(new Packet(0x0F, 342, 336, PacketManager::handleClientPlayerLook));
        getClientPackets().add(new Packet(0x10, 335, 332, PacketManager::handleClientPlayerLook));
        getClientPackets().add(new Packet(0x0F, 331, 318, PacketManager::handleClientPlayerLook));
        getClientPackets().add(new Packet(0x0E, 317, 77, PacketManager::handleClientPlayerLook));
        getClientPackets().add(new Packet(0x0D, 76, 67, PacketManager::handleClientPlayerLook));
        getClientPackets().add(new Packet(0x05, 66, 47, PacketManager::handleClientPlayerLook));
        
        // ClientPlayerPosition
        getClientPackets().add(new Packet(0x10, 404, 389, PacketManager::handleClientPlayerPosition));
        getClientPackets().add(new Packet(0x0E, 388, 386, PacketManager::handleClientPlayerPosition));
        getClientPackets().add(new Packet(0x0C, 385, 343, PacketManager::handleClientPlayerPosition));
        getClientPackets().add(new Packet(0x0D, 342, 336, PacketManager::handleClientPlayerPosition));
        getClientPackets().add(new Packet(0x0E, 335, 332, PacketManager::handleClientPlayerPosition));
        getClientPackets().add(new Packet(0x0D, 331, 318, PacketManager::handleClientPlayerPosition));
        getClientPackets().add(new Packet(0x0C, 317, 77, PacketManager::handleClientPlayerPosition));
        getClientPackets().add(new Packet(0x0B, 76, 67, PacketManager::handleClientPlayerPosition));
        getClientPackets().add(new Packet(0x04, 66, 47, PacketManager::handleClientPlayerPosition));
        
        // ClientPlayerPositionAndLook
        getClientPackets().add(new Packet(0x11, 404, 389, PacketManager::handleClientPlayerPositionAndLook));
        getClientPackets().add(new Packet(0x0F, 388, 386, PacketManager::handleClientPlayerPositionAndLook));
        getClientPackets().add(new Packet(0x0D, 385, 343, PacketManager::handleClientPlayerPositionAndLook));
        getClientPackets().add(new Packet(0x0E, 342, 336, PacketManager::handleClientPlayerPositionAndLook));
        getClientPackets().add(new Packet(0x0F, 335, 332, PacketManager::handleClientPlayerPositionAndLook));
        getClientPackets().add(new Packet(0x0E, 331, 318, PacketManager::handleClientPlayerPositionAndLook));
        getClientPackets().add(new Packet(0x0D, 317, 77, PacketManager::handleClientPlayerPositionAndLook));
        getClientPackets().add(new Packet(0x0C, 76, 67, PacketManager::handleClientPlayerPositionAndLook));
        getClientPackets().add(new Packet(0x06, 66, 47, PacketManager::handleClientPlayerPositionAndLook));
        
        // ServerJoinGame
        getServerPackets().add(new Packet(0x25, 404, 389, PacketManager::handleServerJoinGame));
        getServerPackets().add(new Packet(0x24, 388, 345, PacketManager::handleServerJoinGame));
        getServerPackets().add(new Packet(0x23, 344, 332, PacketManager::handleServerJoinGame));
        getServerPackets().add(new Packet(0x24, 331, 318, PacketManager::handleServerJoinGame));
        getServerPackets().add(new Packet(0x23, 317, 86, PacketManager::handleServerJoinGame));
        getServerPackets().add(new Packet(0x24, 85, 67, PacketManager::handleServerJoinGame));
        getServerPackets().add(new Packet(0x01, 66, 47, PacketManager::handleServerJoinGame));
        
        // ServerPlayerPositionAndLook
        getServerPackets().add(new Packet(0x32, 404, 389, PacketManager::handleServerPlayerPositionAndLook));
        getServerPackets().add(new Packet(0x31, 388, 352, PacketManager::handleServerPlayerPositionAndLook));
        getServerPackets().add(new Packet(0x30, 351, 345, PacketManager::handleServerPlayerPositionAndLook));
        getServerPackets().add(new Packet(0x2F, 344, 336, PacketManager::handleServerPlayerPositionAndLook));
        getServerPackets().add(new Packet(0x2E, 335, 332, PacketManager::handleServerPlayerPositionAndLook));
        getServerPackets().add(new Packet(0x2F, 331, 318, PacketManager::handleServerPlayerPositionAndLook));
        getServerPackets().add(new Packet(0x2E, 317, 86, PacketManager::handleServerPlayerPositionAndLook));
        getServerPackets().add(new Packet(0x2F, 85, 80, PacketManager::handleServerPlayerPositionAndLook));
        getServerPackets().add(new Packet(0x2E, 79, 67, PacketManager::handleServerPlayerPositionAndLook));
        getServerPackets().add(new Packet(0x08, 66, 47, PacketManager::handleServerPlayerPositionAndLook));
        
        // ServerRespawn
        getServerPackets().add(new Packet(0x38, 404, 389, PacketManager::handleServerRespawn));
        getServerPackets().add(new Packet(0x37, 388, 352, PacketManager::handleServerRespawn));
        getServerPackets().add(new Packet(0x36, 351, 345, PacketManager::handleServerRespawn));
        getServerPackets().add(new Packet(0x35, 344, 336, PacketManager::handleServerRespawn));
        getServerPackets().add(new Packet(0x34, 335, 332, PacketManager::handleServerRespawn));
        getServerPackets().add(new Packet(0x35, 331, 318, PacketManager::handleServerRespawn));
        getServerPackets().add(new Packet(0x33, 317, 86, PacketManager::handleServerRespawn));
        getServerPackets().add(new Packet(0x34, 85, 80, PacketManager::handleServerRespawn));
        getServerPackets().add(new Packet(0x33, 79, 67, PacketManager::handleServerRespawn));
        getServerPackets().add(new Packet(0x07, 66, 47, PacketManager::handleServerRespawn));
    }
    
    public static void processClientPacket(Player player, ByteBuf byteBuf) {
        int packetId = DefinedPacket.readVarInt(byteBuf);
        for (Packet packet : getClientPackets()) {
            if (packet.getId() != packetId) {
                continue;
            }
            
            if (packet.getMaxProtocol() >= player.getProtocolVersion() && packet.getMinProtocol() <= player.getProtocolVersion()) {
                packet.getBiConsumer().accept(player, byteBuf);
                return;
            }
        }
    }
    
    public static void processServerPacket(Player player, ByteBuf byteBuf) {
        int packetId = DefinedPacket.readVarInt(byteBuf);
        for (Packet packet : getServerPackets()) {
            if (packet.getId() != packetId) {
                continue;
            }
            
            if (packet.getMaxProtocol() >= player.getProtocolVersion() && packet.getMinProtocol() <= player.getProtocolVersion()) {
                packet.getBiConsumer().accept(player, byteBuf);
                return;
            }
        }
    }
    
    public static void handleClientPlayerLook(Player player, ByteBuf byteBuf) {
        float yaw = byteBuf.readFloat();
        float pitch = byteBuf.readFloat();
        
        player.setPitch(pitch);
        player.setYaw(yaw);
    }
    
    public static void handleClientPlayerPosition(Player player, ByteBuf byteBuf) {
        double x = byteBuf.readDouble();
        double y = byteBuf.readDouble();
        double z = byteBuf.readDouble();
        
        player.setX(x);
        player.setY(y);
        player.setZ(z);
    }
    
    public static void handleClientPlayerPositionAndLook(Player player, ByteBuf byteBuf) {
        double x = byteBuf.readDouble();
        double y = byteBuf.readDouble();
        double z = byteBuf.readDouble();
        float yaw = byteBuf.readFloat();
        float pitch = byteBuf.readFloat();
        
        player.setX(x);
        player.setY(y);
        player.setZ(z);
        player.setPitch(pitch);
        player.setYaw(yaw);
    }
    
    public static void handleServerJoinGame(Player player, ByteBuf byteBuf) {
        int entityId = byteBuf.readInt();
        short gamemode = byteBuf.readUnsignedByte();
        int dimension;
        if (player.getProtocolVersion() > ProtocolConstants.MINECRAFT_1_9) {
            dimension = byteBuf.readInt();
        } else {
            dimension = byteBuf.readByte();
        }
        
        player.setDimension(dimension);
    }
    
    public static void handleServerPlayerPositionAndLook(Player player, ByteBuf byteBuf) {
        double x = byteBuf.readDouble();
        double y = byteBuf.readDouble();
        double z = byteBuf.readDouble();
        float yaw = byteBuf.readFloat();
        float pitch = byteBuf.readFloat();
        byte flags = byteBuf.readByte();
        
        // X (0)
        if ((flags & 1) == 1) {
            x += player.getX();
        }
        
        // Y (1)
        if ((flags & 2) == 2) {
            y += player.getY();
        }
        
        // Z (2)
        if ((flags & 4) == 4) {
            z += player.getZ();
        }
        
        // Y_ROT (3)
        if ((flags & 8) == 8) {
            yaw += player.getYaw();
        }
        
        // X_ROT (4)
        if ((flags & 16) == 16) {
            pitch += player.getPitch();
        }
        
        player.setX(x);
        player.setY(y);
        player.setZ(z);
        player.setPitch(pitch);
        player.setYaw(yaw);
    }
    
    public static void handleServerRespawn(Player player, ByteBuf byteBuf) {
        int dimension = byteBuf.readInt();
        player.setDimension(dimension);
    }
    
    public static List<Packet> getClientPackets() {
        return CLIENT_PACKETS;
    }
    
    public static List<Packet> getServerPackets() {
        return SERVER_PACKETS;
    }
}
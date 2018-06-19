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

package io.github.lxgaming.bungeeplayer.util;

import io.github.lxgaming.bungeeplayer.data.Player;
import io.github.lxgaming.bungeeplayer.managers.PacketManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.md_5.bungee.protocol.DefinedPacket;
import net.md_5.bungee.protocol.PacketWrapper;

public class ChannelHandler extends ChannelDuplexHandler {
    
    private final Player player;
    
    public ChannelHandler(Player player) {
        this.player = player;
    }
    
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof DefinedPacket) {
            ((DefinedPacket) msg).handle(new PacketHandler(getPlayer()));
            super.write(ctx, msg, promise);
            return;
        }
        
        if (!(msg instanceof ByteBuf) || ((ByteBuf) msg).readableBytes() <= 0) {
            super.write(ctx, msg, promise);
            return;
        }
        
        ByteBuf byteBuf = ((ByteBuf) msg).copy();
        
        try {
            byteBuf.markReaderIndex();
            PacketManager.processServerPacket(getPlayer(), byteBuf);
            super.write(ctx, msg, promise);
        } finally {
            byteBuf.resetReaderIndex();
            byteBuf.release();
        }
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof PacketWrapper) || ((PacketWrapper) msg).buf.readableBytes() <= 0) {
            super.channelRead(ctx, msg);
            return;
        }
        
        ByteBuf byteBuf = ((PacketWrapper) msg).buf.copy();
        
        try {
            byteBuf.markReaderIndex();
            PacketManager.processClientPacket(getPlayer(), byteBuf);
            super.channelRead(ctx, msg);
        } finally {
            byteBuf.resetReaderIndex();
            byteBuf.release();
        }
    }
    
    public Player getPlayer() {
        return player;
    }
}
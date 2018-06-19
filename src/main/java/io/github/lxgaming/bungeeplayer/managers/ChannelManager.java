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

import io.github.lxgaming.bungeeplayer.BungeePlayer;
import io.github.lxgaming.bungeeplayer.data.Player;
import io.github.lxgaming.bungeeplayer.util.ChannelHandler;
import io.github.lxgaming.bungeeplayer.util.Reference;
import io.github.lxgaming.bungeeplayer.util.Toolbox;
import io.netty.channel.Channel;
import net.md_5.bungee.netty.ChannelWrapper;
import net.md_5.bungee.netty.PipelineUtils;

public final class ChannelManager {
    
    private static final String CHANNEL_HANDLER = Reference.PLUGIN_ID + "-handler";
    
    public static boolean addChannel(Player player, Object object) {
        Channel channel = Toolbox.getChannelWrapper(object.getClass(), object).map(ChannelWrapper::getHandle).orElse(null);
        if (channel != null && channel.pipeline().get(CHANNEL_HANDLER) == null) {
            
            BungeePlayer.getInstance().getLogger().info(String.join(",", channel.pipeline().names()));
            
            channel.pipeline().addBefore(PipelineUtils.BOSS_HANDLER, CHANNEL_HANDLER, new ChannelHandler(player));
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean removeChannel(Object object) {
        Channel channel = Toolbox.getChannelWrapper(object.getClass(), object).map(ChannelWrapper::getHandle).orElse(null);
        if (channel != null && channel.pipeline().get(CHANNEL_HANDLER) != null) {
            channel.pipeline().remove(CHANNEL_HANDLER);
            return true;
        } else {
            return false;
        }
    }
}
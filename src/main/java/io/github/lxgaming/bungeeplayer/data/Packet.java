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

package io.github.lxgaming.bungeeplayer.data;

import io.netty.buffer.ByteBuf;

import java.util.function.BiConsumer;

public final class Packet {
    
    private final int id;
    private final int maxProtocol;
    private final int minProtocol;
    private final BiConsumer<Player, ByteBuf> biConsumer;
    
    public Packet(int id, int maxProtocol, int minProtocol, BiConsumer<Player, ByteBuf> biConsumer) {
        this.id = id;
        this.maxProtocol = maxProtocol;
        this.minProtocol = minProtocol;
        this.biConsumer = biConsumer;
    }
    
    public int getId() {
        return id;
    }
    
    public int getMaxProtocol() {
        return maxProtocol;
    }
    
    public int getMinProtocol() {
        return minProtocol;
    }
    
    public BiConsumer<Player, ByteBuf> getBiConsumer() {
        return biConsumer;
    }
}
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

import io.github.lxgaming.bungeeplayer.api.IPlayer;

import java.util.UUID;

public final class Player implements IPlayer {
    
    private final UUID uniqueId;
    private final int protocolVersion;
    private double x;
    private double y;
    private double z;
    private float pitch;
    private float yaw;
    private int dimension;
    private String server;
    
    public Player(UUID uniqueId, int protocolVersion) {
        this.uniqueId = uniqueId;
        this.protocolVersion = protocolVersion;
    }
    
    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }
    
    @Override
    public int getProtocolVersion() {
        return protocolVersion;
    }
    
    @Override
    public double getX() {
        return x;
    }
    
    public void setX(double x) {
        this.x = x;
    }
    
    @Override
    public double getY() {
        return y;
    }
    
    public void setY(double y) {
        this.y = y;
    }
    
    @Override
    public double getZ() {
        return z;
    }
    
    public void setZ(double z) {
        this.z = z;
    }
    
    @Override
    public float getPitch() {
        return pitch;
    }
    
    public void setPitch(float pitch) {
        this.pitch = pitch % 360.0F;
    }
    
    @Override
    public float getYaw() {
        return yaw;
    }
    
    public void setYaw(float yaw) {
        this.yaw = yaw % 360.0F;
    }
    
    @Override
    public int getDimension() {
        return dimension;
    }
    
    public void setDimension(int dimension) {
        this.dimension = dimension;
    }
    
    @Override
    public String getServer() {
        return server;
    }
    
    public void setServer(String server) {
        this.server = server;
    }
}
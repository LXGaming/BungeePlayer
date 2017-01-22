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

public class Location {
	
	private double x;
	private double y;
	private double z;
	private float yaw;
	private float pitch;
	private int dimension;
	private String server;
	
	/**
	 * Sets the Locations x
	 * 
	 * @param x the x to set
	 */
	protected void setX(double x) {
		this.x = x;
	}
	
	/**
	 * Get the Location x
	 * 
	 * @return the x
	 */
	public double getX() {
		return this.x;
	}
	
	/**
	 * Sets the Locations y
	 * 
	 * @param y the y to set
	 */
	protected void setY(double y) {
		this.y = y;
	}
	
	/**
	 * Get the Location y
	 * 
	 * @return the y
	 */
	public double getY() {
		return this.y;
	}
	
	/**
	 * Sets the Locations z
	 * 
	 * @param z the z to set
	 */
	protected void setZ(double z) {
		this.z = z;
	}
	
	/**
	 * Get the Location z
	 * 
	 * @return the z
	 */
	public double getZ() {
		return this.z;
	}
	
	/**
	 * Sets the Locations yaw
	 * 
	 * @param yaw the yaw to set
	 */
	protected void setYaw(float yaw) {
		this.yaw = yaw;
	}
	
	/**
	 * Get the Location yaw
	 * 
	 * @return the yse
	 */
	public float getYaw() {
		return this.yaw;
	}
	
	/**
	 * Sets the Locations pitch
	 * 
	 * @param pitch the pitch to set
	 */
	protected void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	/**
	 * Get the Location pitch
	 * 
	 * @return the pitch
	 */
	public float getPitch() {
		return this.pitch;
	}
	
	/**
	 * Sets the Locations dimension
	 * 
	 * @param dimension the dimension to set
	 */
	protected void setDimension(int dimension) {
		this.dimension = dimension;
	}
	
	/**
	 * Get the Location dimension
	 * 
	 * @return the dimension
	 */
	public int getDimension() {
		return this.dimension;
	}
	
	/**
	 * Sets the Locations server
	 * 
	 * @param server the server to set
	 */
	protected void setServer(String server) {
		this.server = server;
	}
	
	/**
	 * Get the Location server
	 * 
	 * @return the server
	 */
	public String getServer() {
		return this.server;
	}
	
	/**
	 * Get the Location toString
	 * 
	 * @return x, y, z, dimension, server
	 */
	@Override
	public String toString() {
		return this.x + ", " + this.y + ", " + this.z + ", " + this.dimension + ", " + this.server;
	}
}

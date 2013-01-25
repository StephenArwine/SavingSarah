package com.SaL.SavingSarah.entity;

import java.awt.Graphics;

import com.SaL.SavingSarah.level.*;
import com.SaL.SavingSarah.screen.GameScreen;

public class Entity {

	public double xa, ya;
	public double x, y;
	public int w = 16, h = 16;

	protected Level level;
	public boolean removed = false;
	public int xSlot, ySlot;

	public boolean interactsWithWorld = false;

	public void init(Level level) {

		this.level = level;

	}

	public void tick() {

	}
    public void render(Graphics g, Camera camera) {
    }
    
	public void remove() {

		// Remove from level
		removed = true;
	}

	public boolean isRemoved() {

		return removed;

	}
    public void outOfBounds() {
        if (y < 0) return;
        remove();
    }


}

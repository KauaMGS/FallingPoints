package com.kgsm.Game;

import java.awt.Color;
import java.awt.Graphics;

public class Player {
	public int x, y;
	public boolean right, left;
	public final int WIDTH = 30, HEIGHT = 8;
	public int points = 0, life = 20;
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void tick() {
		if(right) {
			x++;
		}else if(left) {
			x--;
		}
		
		if(this.x + this.WIDTH > Game.WIDTH-50) {
			x--;
		}else if(this.x < 0) {
			x++;
		}
	}
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, WIDTH, HEIGHT);
	}
	
}

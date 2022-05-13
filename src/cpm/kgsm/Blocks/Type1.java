package cpm.kgsm.Blocks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.kgsm.Game.*;

public class Type1 extends Blocks {
	public Type1() {
		this.speed = 0.5;
		this.gvpoint = 1;
		this.width = 10;
		this.height = 10;
		this.y = 0 - this.height;
		
		x = 0;
		do {
			this.x = rnd.nextInt(Game.WIDTH);
		}while(this.x + this.width > Game.WIDTH-50 || this.x < 0);	
	}
	
	@Override
	public void tick() {
		this.y += speed;
		Rectangle gameRec = new Rectangle(0, 175-7, Game.WIDTH*Game.SCALE, 8);
		Rectangle blockRec = new Rectangle((int)x, (int)y, width, height);
		Rectangle playerRec = new Rectangle(Game.player.x, Game.player.y, Game.player.WIDTH, Game.player.HEIGHT);
		if(blockRec.intersects(playerRec)) {
			Game.player.points+=this.gvpoint;
			System.out.println("Removido : "+Game.block.get(Game.cont));
			Game.block.remove(this);
			Game.cont--;
		}else if(gameRec.intersects(blockRec)) {
			Game.player.life-=this.gvpoint;
			System.out.println("Removido : "+Game.block.get(Game.cont));
			Game.block.remove(this);
			Game.cont--;
		}
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect((int)x,(int)y, width, height);
	}
}

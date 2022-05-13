package com.kgsm.Game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import cpm.kgsm.Blocks.Type1;
import cpm.kgsm.Blocks.Type2;
import cpm.kgsm.Blocks.Type3;

public class Game extends Canvas implements Runnable, KeyListener {
	private static final long serialVersionUID = 1L;
	public static Player player;
	public static ArrayList<Blocks> block = new ArrayList<>();
	private static JFrame frame;
	private Thread thread;
	private boolean isRunning = false;
	private BufferedImage image;
	public static final int HEIGHT = 175, WIDTH = 200, SCALE = 3;
	public static int nivel = 0, cont = 0;
	public BlockGenerator gen;
	public static boolean pdGen = true;
	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		player = new Player(50,175-8);
		gen = new BlockGenerator();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		this.addKeyListener(this);
		initFrame();
		instanceBlock();
	}
	
	public static void instanceBlock() {
		if(cont==0 && pdGen==true) {
			nivel++;
			for(int i=0;i<=nivel;i++) {
				int t = 0;
				Random rnd = new Random();
				do {
					t = rnd.nextInt(4);
				}while(t==0);
				
				switch(t) {
				case 1:
						block.add(i, new Type1());
						System.out.println("Gerado : "+block.get(Game.cont)+" na posiçao x : "+block.get(i).x+" y : "+block.get(i).y);
						cont++;
				break;		
				case 2:
						block.add(i, new Type2());
						System.out.println("Gerado : "+block.get(Game.cont)+" na posiçao x : "+block.get(i).x+" y : "+block.get(i).y);
						cont++;
				break;	
				case 3:
						block.add(i, new Type3());
						System.out.println("Gerado : "+block.get(Game.cont)+" na posiçao x : "+block.get(i).x+" y : "+block.get(i).y);
						cont++;
				break;					
				}
	        }	
		}
	}
	
	public void initFrame() {
		frame = new JFrame("Falling Points");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public synchronized void start() {
		Thread thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		player.tick();
		for(int i=1;i<=nivel;i++) {
			if(cont>0) {
				if(block.get(i) != null)
					block.get(i).tick();
			}
		}
		if(cont>nivel) {
			cont--;
		}		
		gen.generate();
	}
	public void render() {
		BufferStrategy bs = this.getBufferStrategy(); 
		if(bs==null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		Font fonte = new Font("Arial", 18, 10);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
		
		g.setColor(Color.YELLOW);
		g.fillRect(0, 175-7, WIDTH*SCALE, player.HEIGHT);
		
		g.setColor(Color.BLACK);
		g.fillRect(Game.WIDTH-50, 0, 1, Game.HEIGHT);
		
		g.setColor(Color.lightGray);
		g.fillRect(Game.WIDTH-49, 0, 49, Game.HEIGHT);
		
		g.setColor(Color.BLACK);
		g.setFont(fonte);
		g.drawString("Vida : "+player.life, Game.WIDTH-46, 50);
		g.drawString("Pontos:"+player.points, Game.WIDTH-48, 70);
		
		player.render(g);	
		for(int i=1;i<=nivel;i++) {
			if(cont>0) {
				if(block.get(i) != null) 
					block.get(i).render(g);
			}
		}
		
		g.dispose();	
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);	
		bs.show();
		
	}
		
	@Override
	public void run(){
		requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		
		while(isRunning){
			long now = System.nanoTime();
			delta += (now-lastTime) / ns;
			lastTime = now;
		
			if(delta>=1){
				tick();
				render();
				frames++;	
				delta--;				
			}
			
			if((System.currentTimeMillis() - timer) >= 1000){
				frame.setTitle("Falling Points    FPS : "+frames);
				frames = 0;
				timer += 1000;
			}	
		}
		stop();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = true;
		}	
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		}		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
			
	}

}

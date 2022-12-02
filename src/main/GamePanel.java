package main;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;


public class GamePanel extends JPanel implements Runnable{
	//SCREEN SETTINGS FOR THE GAME
	final int originalTileSize = 16; //this is setting the game by 16 x 16 
	
	final int scale =3; //scales the pixels up so the char doesn't look small
	
	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol=16;
	public final int maxScreenRow =12;
	public final int screenWidth =tileSize * maxScreenCol; //768 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	
	//THE WORLD SETTINGS
	public final int maxWorldCol =50;
	public final int maxWorldRow =50;
	//public final int worldWidth = tileSize * maxWorldCol;
	//public final int worldHeight = tileSize * maxWorldRow;
	
	//FPS
	int FPS =60;
	
	//SYSTEM
	TileManager tileM = new TileManager(this);
	public KeyHandler keyH =new KeyHandler(this);
	Sound music =new Sound();
	Sound soundeffect = new Sound();
	Thread gameThread;
	public CollisionChecker colchecker = new CollisionChecker(this);
	public AssetSetter assetSetter = new AssetSetter(this);
	public UI ui =new UI(this);
	
	// ENTITY AND OBJECT
	public Player player = new Player(this,keyH);
	public SuperObject obj[] = new SuperObject[10];
	public Entity npc[] = new Entity[10];
	
	//Game State
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialougeState = 3;
	
	
	public GamePanel () {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void setupGame() {
		assetSetter.setObject();
		assetSetter.setNPC();
		//playMusic(0);
		gameState = titleState;
	}

	public void sstartGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void run() {
		double drawInterval = 1000000000/FPS; //0.01666 seconds
		double nextDrawTime= System.nanoTime()+ drawInterval;
		
		while(gameThread != null) {
			
	
			update();
			
			repaint();
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000;
				if (remainingTime < 0) {
					remainingTime = 0;
				}
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawInterval;
			}
			
			catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		
	}
	public void update() {
		
		if (gameState == playState) {
			player.update();
			
			//npc 
			for(int i = 0; i < npc.length; i++) {
				if (npc [i] != null) {
					npc[i].update();
				}
			}
		}
		if( gameState == pauseState) {
			//nothing
		}
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		//TILE
		if(gameState == titleState) {
			ui.draw(g2);
		}
		else {
			tileM.draw(g2);
			//Objects
			for(int i = 0; i < obj.length; i++){
				if(obj[i] != null) {
					obj[i].draw(g2, this);
				}
			}
			//NPC
			for(int i = 0; i < npc.length; i++){
				if(npc[i] != null) {
					npc[i].draw(g2);
				}
			}
			//Player
			player.draw(g2);
			
			//UI
			ui.draw(g2);
		}

		
		g2.dispose();
	}
	public void playMusic(int i)	{
		music.setFile(i);
		music.play();
		music.loop();	
	}
	public void stopMusic() {
		music.stop();
	}
	public void playSE(int i) {
		soundeffect.setFile(i);
		soundeffect.play();
	}
}















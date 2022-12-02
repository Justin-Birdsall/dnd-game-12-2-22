package main;

import java.io.IOException;
import java.io.InputStream;
import java.awt.*;
import java.text.DecimalFormat;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	Font maruMonica;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		
		try {
			InputStream is = getClass().getResourceAsStream("/fonts/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showMessage (String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		g2.setFont(maruMonica);
		g2.setColor(Color.white);
		//TITLE STATE
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();

		}
		//PLAY STATE
		if(gp.gameState == gp.playState) {
			//Do playState stuff later
		}
		//PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		//DIALOUGE STATE
		if(gp.gameState == gp.dialougeState) {
			drawDialougeScreen();
		}
	}
	public void drawTitleScreen() {
		//TITLE NAME
		g2.setFont (g2.getFont().deriveFont(Font.BOLD,96F));
		String text = "2D ADVENTURE!";
		int x = getXforCenteredText(text);
		int y = gp.tileSize *3;
		
		//SHADOW FOR LETTERING
		g2.setColor(Color.gray);
		g2.drawString( text , x+5 , y+5 );
		
		//MAIN COLOR AND TITLE
		g2.setColor(Color.white);
		g2.drawString( text , x , y );
		
		//IMAGE BELOW TITLE
		x = gp.screenWidth/2 - (gp.tileSize*3)/2;
		y += gp.tileSize;
		g2.drawImage(gp.player.down1, x , y , gp.tileSize*3, gp.tileSize*3, null);
		
		//MENU
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
		
		text = "NEW GAME";
		x = getXforCenteredText(text);
		y += gp.tileSize*4;
		g2.drawString(text , x , y);
		if(commandNum ==0) {
			g2.drawString(">", x- gp.tileSize, y);
			
		}
		
		text = "LOAD GAME";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text , x , y);
		if(commandNum ==1) {
			g2.drawString(">", x- gp.tileSize, y);
			
		}
		text = "QUIT";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text , x , y);
		if(commandNum ==2) {
			g2.drawString(">", x- gp.tileSize, y);
			
		}
	}

	public void drawPauseScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80f));
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text , x , y);
	}
	public void drawDialougeScreen() {
		//WINDOW
		int x = gp.tileSize *2;
		int y= gp.tileSize/2;
		int width = gp.screenWidth - (gp.tileSize*4);
		int height = gp.tileSize*4;
		
		drawSubWindow( x , y , width , height);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,28F));
		x += gp.tileSize;
		y += gp.tileSize;
		
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, x ,y );
			y +=40;
		}
		
	}
	
	public void drawSubWindow (int x , int y , int width , int height) {
		Color c = new Color (0 , 0 , 0, 200);
		g2.setColor(c);
		g2.fillRoundRect(x , y , width , height , 35 , 35);
		
		c = new Color ( 255 , 255 , 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x +5  , y+5 , width-10 , height-10 , 25 , 25);
	}
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text , g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
	
}

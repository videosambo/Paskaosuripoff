package osuripoff;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;


public class Main {

	private static ArrayList<Tile> tiles = new ArrayList<Tile>();
	private static int hp = 100;
	private static int score = 0;
	private static KeyManager listener = new KeyManager();
	
	public static void main(String[] args){
	    final String title = "Test Window";
	    final int width = 1200;
	    final int height = width / 16 * 9;

	    //Creating the frame.
	    JFrame frame = new JFrame(title);

	    frame.setSize(width, height);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLocationRelativeTo(null);
	    frame.setResizable(false);
	    frame.setVisible(true);
	    frame.addKeyListener(listener);

	    //Creating the canvas.
	    Canvas canvas = new Canvas();

	    canvas.setSize(width, height);
	    canvas.setBackground(Color.BLACK);
	    canvas.setVisible(true);
	    canvas.setFocusable(false);

	    
	    //init

	    //Putting it all together.
	    frame.add(canvas);

	    canvas.createBufferStrategy(3);

	    boolean running = true;

	    BufferStrategy bufferStrategy;
	    Graphics graphics;
	    
	    while (running) {
	    	if (hp < 0) running = false;
	    	calculate();
	        bufferStrategy = canvas.getBufferStrategy();
	        graphics = bufferStrategy.getDrawGraphics();
	        graphics.clearRect(0, 0, width, height);
	        //aloita
	        //linjat
	        graphics.setColor(Color.RED);
	        graphics.drawLine(0, 470, 80, 470);
	        graphics.drawLine(0, 500, 80, 500);

	        graphics.drawLine(20, 0, 20, 500);
	        graphics.drawLine(40, 0, 40, 500);
	        graphics.drawLine(60, 0, 60, 500);
	        graphics.drawLine(80, 0, 80, 500);
	        //linjat loppu
	        graphics.setColor(Color.GREEN);
	        
	        //hp
	        graphics.fillRect(120, 30, hp * 2, 20);
	        
	        for (Tile til : tiles) {
	        	graphics.fillRect(til.getX() * 20 - 20, til.getY(), 20, 10);
	        }
	        
	        graphics.setColor(Color.WHITE);
	        graphics.drawString("HP: ", 98, 43);
	        graphics.drawString("Score: " + score, 100, 100);
	        
	        //lopeta
	        bufferStrategy.show();
	        graphics.dispose();
	    }
	    
	}
	
	
	public static void calculate() {
		listener.tick();
		if (Math.random() * 10 > 9.8)
			tiles.add(new Tile((int) (Math.random() * 4 + 1),0));
		try {
			TimeUnit.MILLISECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (Tile til : tiles) {
			til.dropDown();
		}
		Iterator<Tile> iter = tiles.iterator();
		while(iter.hasNext()) {
			Tile til = iter.next();
			if (til.getY() > 470) {
				if (listener.a) {
					if (til.getX() == 1) {
						score = score + 20;
						hp++;
						iter.remove();
					} else {
						hp--;
					}
				}
				if (listener.s) {
					if (til.getX() == 2) {
						score = score + 20;
						hp++;
						iter.remove();
					} else {
						hp--;
					}
				}
				if (listener.d) {
					if (til.getX() == 3) {
						score = score + 20;
						hp++;
						iter.remove();
					} else {
						hp--;
					}
				}
				if (listener.f) {
					if (til.getX() == 4) {
						score = score + 20;
						hp++;
						iter.remove();
					} else {
						hp--;
					}
				}
			}
			if (til.getY() > 510) {
				iter.remove();
				hp--;
			}
		}
	}
}

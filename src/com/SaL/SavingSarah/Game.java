package com.SaL.SavingSarah;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;
import javax.imageio.ImageIO;

import com.SaL.SavingSarah.input.Input;
import com.SaL.SavingSarah.screen.Screen;

public class Game extends Canvas implements Runnable, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int SCALE = 3;
	public static String title = "CaveScroll";


	private boolean running = false;
	private Screen screen;
	private Input input = new Input();
	private boolean started = false;

	public Game() {

		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		this.addKeyListener(this);
		this.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent arg0) {

			}

			public void focusLost(FocusEvent arg0) {

				input.releaseAllKeys();
			}
		});
	}

	public void start() {

		running = true;
		new Thread(this).start();
	}

	public void stop() {

		running = false;
	}

	public void setScreen(Screen newScreen) {

		if (screen != null) screen.removed();
		screen = newScreen;
		if (screen != null) screen.init(this);
	}

	private Image splashImage;

	public void update(Graphics g) {

		paint(g);
	}

	public void paint(Graphics g) {

		if (started) return;
		if (splashImage == null) {
			try {
				splashImage = ImageIO.read(Game.class.getResource("/mojang.png"));
				splashImage = splashImage.getScaledInstance(640, 480, Image.SCALE_AREA_AVERAGING);
			} catch (IOException e) {
			}
		}
		g.drawImage(splashImage, 0, 0, null);
	}

	public void run() {

		requestFocus();
		Image image = new BufferedImage(320, 240, BufferedImage.TYPE_INT_RGB);
		setScreen(new Screen());
		long lastTime = System.nanoTime();
		long unprocessedTime = 0;
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		while (running) {
			Graphics g = image.getGraphics();

			long now = System.nanoTime();
			unprocessedTime += now - lastTime;
			lastTime = now;

			int max = 10;
			while (unprocessedTime > 0) {
				unprocessedTime -= 1000000000 / 60;
				screen.tick(input);
				input.tick();
				if (max-- == 0) {
					unprocessedTime = 0;
					break;
				}
			}

			screen.render(g);
			if (!hasFocus()) {
				String msg = "CLICK TO FOCUS!";
				int w = msg.length();
				int xp = 160 - w * 3;
				int yp = 120 - 3;
				g.setColor(Color.BLACK);
				g.fillRect(xp - 6, yp - 6, 6 * (w + 2), 6 * 3);
				if (System.currentTimeMillis() / 500 % 2 == 0) screen.drawString(msg, g, xp, yp);
			}

			g.dispose();
			try {
				started = true;
				g = getGraphics();
				g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, 0, 0, WIDTH, HEIGHT, null);
				g.dispose();
			} catch (Throwable e) {
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {

		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {

		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {

		JFrame frame = new JFrame("Metagun");
		Game game = new Game();
		frame.setLayout(new BorderLayout());
		frame.add(game, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		game.start();
	}

}

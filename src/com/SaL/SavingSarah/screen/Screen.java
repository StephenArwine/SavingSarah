package com.SaL.SavingSarah.screen;

import java.awt.Graphics;

import com.SaL.SavingSarah.*;
import com.SaL.SavingSarah.input.Input;

public class Screen {

	private Game game;

	public final void init(Game game) {

		this.game = game;
	}

	protected void setScreen(Screen screen) {

		game.setScreen(screen);
	}

	String[] chars = { "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", ".,!?:;\"'+-=/\\< " };

	public void drawString(String string, Graphics g, int x, int y) {

		string = string.toUpperCase();
		for (int i = 0; i < string.length(); i++) {
			char ch = string.charAt(i);
			for (int ys = 0; ys < chars.length; ys++) {
				int xs = chars[ys].indexOf(ch);
				if (xs >= 0) {
					//                   g.drawImage(Art.guys[xs][ys+9], x+i*6, y, null);
				}
			}
		}
	}

	public void render(Graphics g) {

	}

	public void tick(Input input) {

	}

	public void removed() {

		
	}

}

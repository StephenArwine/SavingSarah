package com.SaL.SavingSarah.screen;

import java.awt.Graphics;

import com.SaL.SavingSarah.*;
import com.SaL.SavingSarah.input.Input;
import com.SaL.SavingSarah.level.*;

public class GameScreen extends Screen {

	private static final boolean DEBUG_MODE = false;
	private int xLevel = DEBUG_MODE ? 8 : 0;
	private int yLevel = DEBUG_MODE ? 4 : 0;
	
	
	Level level = new Level(this, 32, 24, xLevel, yLevel, 0, 0);
	private Camera camera = new Camera(Game.WIDTH, Game.HEIGHT);
	
	public GameScreen(){
	
//	Art.level = Art.load("map/spawnroom.png")
//			Stats.reset()
	}
	
	public void tick(Input input){
  //      Stats.instance.time++;
 //       if (!input.oldButtons[Input.ESCAPE] && input.buttons[Input.ESCAPE]) {
 //           setScreen(new PauseScreen(this));
 //           return;
 //       }
 //       if (!level.player.removed) level.player.tick(input);
 //       else if (mayRespawn) {
  //          if (input.buttons[Input.SHOOT] && !input.oldButtons[Input.SHOOT]) {
//                respawnRoom();
 //               mayRespawn = false;
   //         }
  //      }
        level.tick();
		
	}
	
	public void render(Graphics g) {
        level.render(g, camera);
	}

}

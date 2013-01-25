package com.SaL.SavingSarah.level;

import java.awt.*;
import java.util.List;
import java.util.*;

import com.SaL.SavingSarah.entity.*;
import com.SaL.SavingSarah.screen.GameScreen;
import com.SaL.SavingSarah.*;

public class Level {

	public byte[] World;
	private int width, height;
	public Player player;
	public int xSpawn, ySpawn;
	//	private Random random = new Random(1000);
	private GameScreen screen;
	private int tick;

	public List<Entity> entities = new ArrayList<Entity>();
	public List<Entity>[] entityMap;

	@SuppressWarnings("unchecked")
	public Level(GameScreen screen, int w, int h, int xo, int yo, int xSpawn, int ySpawn) {

		this.screen = screen;
		int[] pixels = new int[32 * 24];
		this.xSpawn = xSpawn;
		this.ySpawn = ySpawn;
		Textures.spawnroom.getRGB(xo * 31, yo * 23, 32, 24, pixels, 0, 32);
		World = new byte[w * h];
		entityMap = new ArrayList[w * h];
		this.width = w;
		this.height = h;

		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				entityMap[x + y * w] = new ArrayList<Entity>();

				int col = pixels[x + y * w] & 0xffffffff;
				byte spot = 0;

				if (col == 0x808077) spot = 1;
				else if (col == 0x808077) spot = 2;
				else if (col == 0xFF00FF) spot = 2;
				else if (col == 0xFFFFD800) {
					Torch e = new Torch(x, y);
					add(e);

				}
				World[x + y * w] = spot;
			}
		}
		player = new Player(this.xSpawn, this.ySpawn);
		add(player);

	}

	public void add(Entity e) {

		entities.add(e);
		e.init(this);

		e.xSlot = (int) ((e.x + e.w / 2.0) / 10);
		e.ySlot = (int) ((e.y + e.h / 2.0) / 10);
		if (e.xSlot >= 0 && e.ySlot >= 0 && e.xSlot < width && e.ySlot < height) {
			entityMap[e.xSlot + e.ySlot * width].add(e);
		}
	}

	public void tick() {

		tick++;
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			int xSlotOld = e.xSlot;
			int ySlotOld = e.ySlot;
			if (!e.removed) e.tick();
			e.xSlot = (int) ((e.x + e.w / 2.0) / 10);
			e.ySlot = (int) ((e.y + e.h / 2.0) / 10);
			if (e.removed) {
				if (xSlotOld >= 0 && ySlotOld >= 0 && xSlotOld < width && ySlotOld < height) {
					entityMap[xSlotOld + ySlotOld * width].remove(e);
				}
				entities.remove(i--);
			} else {
				if (e.xSlot != xSlotOld || e.ySlot != ySlotOld) {
					if (xSlotOld >= 0 && ySlotOld >= 0 && xSlotOld < width && ySlotOld < height) {
						entityMap[xSlotOld + ySlotOld * width].remove(e);
					}
					if (e.xSlot >= 0 && e.ySlot >= 0 && e.xSlot < width && e.ySlot < height) {
						entityMap[e.xSlot + e.ySlot * width].add(e);
					} else {
						e.outOfBounds();
					}

				}
			}
		}

	}

	private List<Entity> hits = new ArrayList<Entity>();

	public List<Entity> getEntities(double xc, double yc, double w, double h) {

		hits.clear();
		int r = 20;
		int x0 = (int) ((xc - r) / 10);
		int y0 = (int) ((yc - r) / 10);
		int x1 = (int) ((xc + w + r) / 10);
		int y1 = (int) ((yc + h + r) / 10);
		for (int x = x0; x <= x1; x++)
			for (int y = y0; y <= y1; y++) {
				if (x >= 0 && y >= 0 && x < width && y < height) {
					List<Entity> es = entityMap[x + y * width];
					for (int i = 0; i < es.size(); i++) {
						Entity e = es.get(i);
						double xx0 = e.x;
						double yy0 = e.y;
						double xx1 = e.x + e.w;
						double yy1 = e.y + e.h;
						if (xx0 > xc + w || yy0 > yc + h || xx1 < xc || yy1 < yc) continue;

						hits.add(e);
					}
				}
			}
		return hits;
	}

	public void render(Graphics g, Camera camera) {

		g.translate(-camera.x, -camera.y);
		int xo = camera.x / 16;
		int yo = camera.y / 16;
		for (int x = xo; x <= xo + camera.width / 16; x++) {
			for (int y = yo; y <= yo + camera.height / 16; y++) {
				if (x >= 0 && y >= 0 && x < width && y < height) {
					int ximg = 0;
					int yimg = 0;
					byte w = World[x + y * width];
					if (w == 0) {
						ximg = 6;
						yimg = 0;
					}

					if (w == 1)

					g.drawImage(Textures.indoortiles[ximg][yimg], x * 10, y * 16, null);
				}
			}
		}
		for (int i = entities.size() - 1; i >= 0; i--) {
			Entity e = entities.get(i);
			e.render(g, camera);
		}

	}
}

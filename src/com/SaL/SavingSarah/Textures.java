package com.SaL.SavingSarah;
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Textures {
    public static BufferedImage spawnroom = load("/spawnroom.png");
public static BufferedImage[][] indoortiles = split(load("/indoortiles"), 16, 16);

public static BufferedImage load(String name) {
    try {
        BufferedImage org = ImageIO.read(Textures.class.getResource(name));
        BufferedImage res = new BufferedImage(org.getWidth(), org.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = res.getGraphics();
        g.drawImage(org, 0, 0, null, null);
        g.dispose();
        return res;
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}

@SuppressWarnings("unused")
private static BufferedImage[][] mirrorsplit(BufferedImage src, int xs, int ys) {
    int xSlices = src.getWidth() / xs;
    int ySlices = src.getHeight() / ys;
    BufferedImage[][] res = new BufferedImage[xSlices][ySlices];
    for (int x = 0; x < xSlices; x++) {
        for (int y = 0; y < ySlices; y++) {
            res[x][y] = new BufferedImage(xs, ys, BufferedImage.TYPE_INT_ARGB);
            Graphics g = res[x][y].getGraphics();
            g.drawImage(src, xs, 0, 0, ys, x * xs, y * ys, (x + 1) * xs, (y + 1) * ys, null);
            g.dispose();
        }
    }
    return res;
}
private static BufferedImage[][] split(BufferedImage src, int xs, int ys) {
    int xSlices = src.getWidth() / xs;
    int ySlices = src.getHeight() / ys;
    BufferedImage[][] res = new BufferedImage[xSlices][ySlices];
    for (int x = 0; x < xSlices; x++) {
        for (int y = 0; y < ySlices; y++) {
            res[x][y] = new BufferedImage(xs, ys, BufferedImage.TYPE_INT_ARGB);
            Graphics g = res[x][y].getGraphics();
            g.drawImage(src, -x * xs, -y * ys, null);
            g.dispose();
        }
    }
    return res;
}

}

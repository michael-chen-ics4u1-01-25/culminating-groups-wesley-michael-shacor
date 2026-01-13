package game;

import java.awt.Graphics2D;

public interface Drawable {
    public void draw(Graphics2D g, double xoffset, double yoffset);
    public double getX();
    public double getY();
    public int getWidth();
    public int getHeight();
} 
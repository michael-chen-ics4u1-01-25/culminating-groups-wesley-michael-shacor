package game;
import java.awt.Image;
import java.awt.Graphics2D;
public class NPC implements Drawable {
    /**
     * @version 1.0
     * @author Wesley, Michael, Shacor
     */
    private double x;
    private double y;
    private int width;
    private int height;
    private Image image;
    
    /**
     * Constructor for NPC
     * @param filename The image filename
     * @param x The x coordinate
     * @param y The y coordinate
     * @param width The width of the NPC
     * @param height The height of the NPC
     */
    public NPC(String filename, int x, int y, int width, int height) {
        this.image = Utils.loadImage(filename, 0, 0, 1);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void draw(Graphics2D g, double xoffset, double yoffset) {
        g.drawImage(image, (int)(x - xoffset), (int)(y - yoffset), null);
    }
    
}

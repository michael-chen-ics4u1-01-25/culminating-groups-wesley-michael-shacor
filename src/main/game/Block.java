package game;
import java.awt.Graphics2D;

public class Block implements Drawable{
    public enum Type{
        RECTANGLE,
        CIRCLE
    }
    
    private double x;
    private double y;
    private int width;
    private int height;
    private Type type;
    
    public Block(Type type, double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
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
        switch(type){
            case RECTANGLE:
                g.fillRect((int)(this.x-xoffset), (int)(this.y-yoffset), this.width, this.height);
                break;
            case CIRCLE:
                g.fillOval((int)(this.x-xoffset), (int)(this.y-yoffset), this.width, this.height);
                break;
        }
    }
}

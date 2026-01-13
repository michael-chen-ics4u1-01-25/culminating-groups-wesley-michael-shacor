package game.menu;
import java.awt.Graphics2D;

public interface Menu {
    public void draw(Graphics2D g);
    public void select();
    public void cancel();
    public void update();
    public void moveUp();
    public void moveDown();
    public void moveLeft();
    public void moveRight();
}
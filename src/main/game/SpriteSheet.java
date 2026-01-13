package game;
import java.awt.image.BufferedImage;

public class SpriteSheet {
    private BufferedImage[][] sprites;
    
    public SpriteSheet(String filename, int width, int height, double scale, int offset){
        this.sprites = Utils.loadSpriteSheet(filename, width, height, scale, offset);
    }
    
    public BufferedImage getDefault(){
        return this.sprites[0][1];
    }

    public BufferedImage[] getAnim(int n){
        return this.sprites[n];
    }
}
package game;
import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.imageio.ImageIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.FileWriter;

public final class Utils{
    private static int BOX_THICKNESS = 2;
    private static int TEXT_PADDING_X = 10;
    private static int TEXT_PADDING_Y = 2;
    // hit an alternate font in crazy ahh moments
    public static Font mainFont;
    public static BufferedImage loadImage(String filename){
        try {
            return ImageIO.read(new File("./resources/" + filename));
        } catch (Exception e) {
            System.out.println("One (or more) resource files failed to load");
            System.exit(1);
            return null;
        }
    }

    public static Image loadImage(String filename, int x, int y, double scale){
        try {
            BufferedImage all = ImageIO.read(new File("./resources/" + filename));
            BufferedImage bimg = all.getSubimage(x*32, y*32, 32, 32);
            Image scaled = bimg.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            return scaled;
        } catch (Exception e) {
            System.out.println("One (or more) resource files failed to load");
            System.exit(1);
            return null;
        }
    }

    public static BufferedImage[][] loadSpriteSheet(String filename, int width, int height, double scale, int offset){
        try {
            BufferedImage all = ImageIO.read(new File("./resources/" + filename));
            BufferedImage[][] sprites = new BufferedImage[4][3];
            for(int i = 0; i < 3*width; i += width){
                for(int j = 0; j < 4*height; j += height){
                    BufferedImage bimg = all.getSubimage(3*width*offset+i, j, width, height);
                    int newWidth = (int)(width*scale);
                    int newHeight = (int)(height*scale);
                    Image scaled = bimg.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                    sprites[j/height][i/width] = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
                    Graphics temp = sprites[j/height][i/width].createGraphics();
                    temp.drawImage(scaled, 0, 0, null);
                    temp.dispose();
                }
            }
            return sprites;
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(filename);
            System.out.println("One (or more) resource files failed to load");
            System.exit(1);
            return null;
        }
    }

    public static void loadFonts(){
        try {
            mainFont = Font.createFont(Font.TRUETYPE_FONT, new File("./resources/Gamefont-Regular.ttf"));
        } catch (Exception e) {
            System.out.println("One (or more) resource files failed to load");
            System.exit(1);
        }
    }

    public static Rectangle2D drawTextBox(Graphics2D g, String text, int x, int y, int width, int height, int location){
        Stroke temp = g.getStroke();
        g.setStroke(new BasicStroke(BOX_THICKNESS));
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
        g.setColor(Color.WHITE);
        g.drawRect(x, y, width, height);
        Rectangle2D rect = g.getFontMetrics().getStringBounds(text, g);
        int textWidth = (int)rect.getWidth();
        int textHeight = (int)rect.getHeight();
        g.setStroke(temp);
        switch(location){
            case 0: // top-left
                g.drawString(text, x+TEXT_PADDING_X, y+textHeight+TEXT_PADDING_Y);
                rect.setRect(x+TEXT_PADDING_X, y+textHeight+TEXT_PADDING_Y, textWidth, textHeight);
                break;
            case 1: // top-center
                g.drawString(text, x+(width-textWidth)/2, y+textHeight+TEXT_PADDING_Y);
                rect.setRect(x+(width-textWidth)/2, y+textHeight+TEXT_PADDING_Y, textWidth, textHeight);
                break;
            case 2: // top-right
                g.drawString(text, x+width-textWidth-TEXT_PADDING_X, y+textHeight+TEXT_PADDING_Y);
                rect.setRect(x+width-textWidth-TEXT_PADDING_X, y+textHeight+TEXT_PADDING_Y, textWidth, textHeight);
                break;
        }
        return rect;
    }

    public static void savePlayerPosition(int playerX, int playerY, String filename) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(filename);
            fw.write(playerX + "\n");
            fw.write(playerY + "\n");
            System.out.println("Game saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }   finally {
                try {
                    if (fw != null) {
                    fw.close();
                    }
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        
    }

    public static int[] loadPlayerPosition(String filename) {
        int[] position = new int[2];
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filename));
            position[0] = Integer.parseInt(br.readLine());
            position[1] = Integer.parseInt(br.readLine());
            System.out.println("Game loaded!");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Save failed to load");
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return position;
    }
}
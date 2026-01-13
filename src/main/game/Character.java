package game;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Character implements Movable, Drawable{
    public static final Character I = new Character(new SpriteSheet("mc.png", 32, 32, 2.0, 0), 48, 58, 10, 6);
    public static final Character JULIE = new Character(new SpriteSheet("mc.png", 32, 32, 2.0, 1), 48, 58, 10, 6);
    public static final Character COLIN = new Character(new SpriteSheet("mc.png", 32, 32, 2.0, 2), 48, 58, 10, 6);
    public static final Character DENI = new Character(new SpriteSheet("mc.png", 32, 32, 2.0, 3), 48, 58, 10, 6);
    
    protected double x;
    protected double y;
    protected double xOffset;
    protected double yOffset;
    protected int height;
    protected int width;
    protected BufferedImage sprite;
    protected SpriteSheet spriteSheet;
    protected double speed = 5;
    protected double diagSpeed = 2*Math.sqrt(speed);
    protected static final int ANIMATION_FRAMES = 30;
    protected static final int TRAIL_SEPARATION = 10;
    protected int animationCycle;
    protected boolean back;
    protected int elevation;
    private boolean isJumping;
    // private boolean isMoving;
    private double yVel = 0;
    // private double xVel = 0;
    private static final double GRAVITY = 0.98;
    private double startY = 0;
    private double startX = 0;
    //private int xMovement = 0;
    //private int yMovement = 0;
    
    protected int facing;
    protected Player following;

    private int trailIndex;

    public Character(SpriteSheet spriteSheet, int width, int height, int xoff, int yoff) {
        this.x = 300;
        this.y = 600;
        this.height = height;
        this.width = width;
        this.xOffset = xoff;
        this.yOffset = yoff;
        this.spriteSheet = spriteSheet; //Utils.loadSpriteSheet(spritefile, 32, 32, 2.0, offset);
        this.sprite = spriteSheet.getDefault();
        this.animationCycle = ANIMATION_FRAMES/3;
        this.back = true;
        this.facing = 0;
        this.following = null;
        this.trailIndex = 0;
        this.elevation = 0;
        this.isJumping = false;
        // this.isMoving = false;
    }

    public double getX(){return this.x;}
    public double getY(){return this.y;}
    public void setX(double x){this.x = x;}
    public void setY(double y){this.y = y;}
    public int getWidth(){return this.width;}
    public int getHeight(){return this.height;}

    public void draw(Graphics2D g, double xoffset, double yoffset) {
        g.drawImage(sprite, (int)(x-xoffset-xOffset), (int)(y-yoffset-yOffset), null);
        g.setColor(new Color(0, 0, 255));
        g.fillRect((int)(x-xoffset), (int)(y-yoffset), (int)10, (int)10);
        g.drawRect((int)(x-xoffset), (int)(y-yoffset), (int)width, (int)height);
        g.setColor(new Color(255, 0, 255));
        g.drawRect((int)(x-xoffset), (int)(y-yoffset), (int)64, (int)64);
    }

    public void followPlayer(Player player, int offset){
        this.following = player;
        this.trailIndex = offset*TRAIL_SEPARATION;
    }

    public void jump(){
        System.out.println("DSFSD");
        this.isJumping = true;
        this.startY = y;
        this.yVel = -10;
    }
    //was going to be used for cutscenes, but after scrapping a major portion of the game, it was no longer necessary
    //public void xMove(int xMovement) {
    //    this.isMoving = true;
    //    this.startX = x;
    //    this.xMovement = xMovement;
    //    if (xMovement>0) {
    //        this.xVel = 5;
    //    } else {
    //        this.xVel = -5;
    //    }
    //}
    //public void yMove(int yMovement) {
    //    this.isMoving = true;
    //    this.startY = y;
    //    this.yMovement = yMovement;
    //    if (yMovement>0) {
    //        this.yVel = 5;
    //    } else {
    //        this.yVel = -5;
    //    }
//
//
    //}

    protected double collisionTime(Drawable object, boolean xAxis, double speed, boolean plusminus){
        double axisMinThis = xAxis ? this.x : this.y;
        double axisMaxThis = xAxis ? this.x + this.width : this.y + this.height;
        double axisMinOther = xAxis ? object.getX() : object.getY();
        double axisMaxOther = xAxis ? object.getX() + object.getWidth() : object.getY() + object.getHeight();
        double orthoMinThis = xAxis ? this.y : this.x;
        double orthoMaxThis = xAxis ? this.y + this.height : this.x + this.width;
        double orthoMinOther = xAxis ? object.getY() : object.getX();
        double orthoMaxOther = xAxis ? object.getY() + object.getHeight() : object.getX() + object.getWidth();
        if ((orthoMinOther < orthoMinThis && orthoMinThis < orthoMaxOther) || 
            (orthoMinOther < orthoMaxThis && orthoMaxThis < orthoMaxOther) || 
            (orthoMinThis <= orthoMinOther && orthoMinOther < orthoMaxThis)) {
            if (plusminus && axisMinThis < axisMinOther && axisMaxThis + speed > axisMinOther) { // down/right
                return (axisMinOther - axisMaxThis)/speed;
            } else if (!plusminus && axisMinOther < axisMinThis && axisMinThis - speed < axisMaxOther) { // up/left
                return (axisMinThis - axisMaxOther)/speed;
            }
        }
        return 1;
    }

    public void update(ArrayList<Drawable> objects){
        //if (this.isMoving) {
        //    if (this.xVel>0) {
        //        x += xVel;
        //        if (x >= startX+xMovement) {
        //            this.isMoving = false;
        //            xVel = 0;
        //        }
        //    }
        //    if (this.yVel>0) {
        //        y += yVel;
        //        if (y >= startY+yMovement) {
        //            this.isMoving = false;
        //            yVel = 0;
        //        }
        //    }
        //}
        if(this.isJumping){
            y += yVel;
            yVel += GRAVITY;
            if(y > startY){
                this.isJumping = false;
                yVel = 0;
                y = startY;
            }
        }else if(this.following != null){
            double[] next = this.following.trail[this.trailIndex];
            this.x = next[0];
            this.y = next[1];
            this.facing = (int)next[2];
            this.animationCycle = this.following.animationCycle;
            this.sprite = spriteSheet.getAnim(facing)[2-this.following.animationCycle/(ANIMATION_FRAMES/3)];
        }
    }
}

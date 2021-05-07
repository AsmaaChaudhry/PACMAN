package basecode;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Ghost {
    public int ghostX, ghostY;
    public String imagePath;
    public int speed = 2;
    private int ghostDir;
    private int minDir = 1;
    private int maxDir = 4;
    
    public Ghost(String imagePath, int ghostX, int ghostY) {
        this.ghostX = ghostX;
        this.ghostY = ghostY;
        this.imagePath = imagePath;     
    }
    public Ghost(String imagePath, int ghostX, int ghostY, int speed) {
        this.ghostX = ghostX;
        this.ghostY = ghostY;
        this.imagePath = imagePath;
        this.speed = speed;
    }
    public int getGhostX() {
        return this.ghostX;
        }
        
    public int getGhostY() {
        return this.ghostY;
    }
    /**
     * Make a new image Icon for a ghost
     * @return image Icon for the ghost
     */
    public Image getIcon() {
        Image ghostIcon = new ImageIcon(imagePath).getImage();
        return ghostIcon;
    }
    /**
     * Sets the ghost's direction to be a random int between 1 and 4
     * @return
     */
    private int setGhostDir() {
        ghostDir = (int) Math.floor(Math.random()*(maxDir-minDir+1)+minDir);
        return ghostDir;
    }
    /**
     * Moves the ghost autonomously at random
     */
    public void move(int counter) {
        if(counter%5 == 0) {
            setGhostDir(); 
        }
        if (ghostDir == 1) {
            //move right
            ghostX+=this.speed;
        }else if (ghostDir ==2) {
            //move down 
            ghostY+=this.speed;
        }else if (ghostDir == 3) {
            //move left 
            ghostX-=this.speed;
        }else if (ghostDir == 4) {
            //move up
            ghostY -=this.speed;
        }
    }
}

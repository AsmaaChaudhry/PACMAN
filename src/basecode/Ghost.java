package basecode;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Ghost {
    public int ghostX;
    public int ghostY;
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
     * Checks to make sure that a move is valid for the ghost, obstacle avoidance
     * @param x - int x position for move in pixels
     * @param y - int y position for move in pixels
     * @param grid - a grid that contains valid and invalid positions
     * @return true if move is valid
     */
    private boolean checkValid(int x, int y, int[][] grid) {
        //find the size in grid
        int bSize = Math.min(360 / 15, 360 / 15);
        int i = x/bSize;
        int j = y/bSize;
        int i2 = (x+22)/bSize;
        int j2 = (y+22)/bSize;
        try {
            if(grid[i][j]!= -1 && grid[i2][j]!= -1 && grid[i][j2]!= -1 && grid[i2][j2]!= -1) {
                return true;
            }
        }catch(ArrayIndexOutOfBoundsException e) {
            return false;
        }
        
        return false;
    }
    
    /**
     * Moves the ghost autonomously at random
     */
    public void move(int counter) {
        if(counter%5 == 0) {
            setGhostDir(); 
        }
        int newPos = 0;
        if (ghostDir == 1) {
            //move right
            newPos = ghostX + this.speed;
            if(checkValid(newPos, this.ghostY, Board.getGrid()) == true) {
                ghostX+=this.speed;
            }
        }else if (ghostDir ==2) {
            //move down 
            newPos = ghostY + this.speed;
            if(checkValid(this.ghostX, newPos, Board.getGrid())== true) {
                ghostY+=this.speed;
            }
        }else if (ghostDir == 3) {
            //move left 
            newPos = ghostX - this.speed;
            if(checkValid(newPos, this.ghostY, Board.getGrid())== true) {
                ghostX-=this.speed;
            }          
        }else if (ghostDir == 4) {
            //move up
            newPos = ghostY - this.speed;
            if(checkValid(this.ghostX, newPos, Board.getGrid())== true) {
                ghostY-=this.speed;
            }
        }
    }
}

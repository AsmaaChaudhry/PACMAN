package basecode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private Dimension d;
    private int livesLeft, score;
    private int posX, posY, nextPosX, nextPosY; // put in PacMan class

    private Timer t;

    private Image pacMan = new ImageIcon("src/images/PacManRight.gif").getImage();
    private Image livesIcon = new ImageIcon("src/images/PacManResting.png").getImage();
    private Image pellet = new ImageIcon("src/images/Pellet.png").getImage();
    private Image removedPellet = new ImageIcon("src/images/removedPellet.png").getImage();
    // make a test ghost
    private Ghost testGhost = new Ghost("src/images/whiteGhost.gif", 50, 150, 5);

    private Ghost blueGhost = new Ghost("src/images/blueGhost1.gif", 20, 20, 5);

    private Ghost testGhost2 = new Ghost("src/images/blueGhost1.gif", 60, 160, 6);

    private int bSize, movement;
    private int gridC, gridR;
    private final int NUM_BLOCKS = 15;
    private final int MAX_GHOSTS = 13;
    public int counter = 0;
    private int numGhost = 2;
    public boolean inGame = false;
    public boolean paused = false;

    private static int[][] grid = { { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, 1, 1, 1 },
            { 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
            { 1, 1, 1, -1, -1, -1, 1, 1, 1, -1, -1, -1, -1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, -1, 1, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, -1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, -1, 1, 1 },
            { 1, 1, 1, -1, -1, -1, 1, 1, 1, -1, -1, -1, -1, 1, 1 },
            { 1, 1, 1, -1, -1, -1, 1, 1, 1, -1, -1, -1, -1, 1, 1 }, { 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };
    private boolean[][] grid2 = {
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, false, true, true, true },
            { true, true, true, false, false, false, true, true, true, true, true, true, true, true, true },
            { true, true, true, false, false, false, true, true, true, true, true, true, true, true, true },
            { true, true, true, false, false, false, true, true, true, false, false, false, false, true, true },
            { true, true, true, true, true, true, true, true, true, false, false, false, false, true, true },
            { true, true, true, true, true, true, true, true, true, false, false, false, false, true, true },
            { true, true, true, true, true, true, true, true, true, false, false, false, false, true, true },
            { true, true, true, false, false, false, true, true, true, false, false, false, false, true, true },
            { true, true, true, false, false, false, true, true, true, false, false, false, false, true, true },
            { true, true, true, false, false, false, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, false, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true } };

    private final boolean[][] GRID3 = {
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, false, true, true, true },
            { true, true, true, false, false, false, true, true, true, true, true, true, true, true, true },
            { true, true, true, false, false, false, true, true, true, true, true, true, true, true, true },
            { true, true, true, false, false, false, true, true, true, false, false, false, false, true, true },
            { true, true, true, true, true, true, true, true, true, false, false, false, false, true, true },
            { true, true, true, true, true, true, true, true, true, false, false, false, false, true, true },
            { true, true, true, true, true, true, true, true, true, false, false, false, false, true, true },
            { true, true, true, false, false, false, true, true, true, false, false, false, false, true, true },
            { true, true, true, false, false, false, true, true, true, false, false, false, false, true, true },
            { true, true, true, false, false, false, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, false, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true } };

    public Board(int w, int h) {

        d = new Dimension(w, h);
        livesLeft = 3;
        posX = 10;
        posY = 10;

        addKeyListener(new Keyboard());
        setFocusable(true);
        setBackground(Color.white);
        t = new Timer(40, this);
        // t.start();

        bSize = Math.min(360 / 15, 360 / 15);
        movement = bSize / 4;
        gridC = posX / bSize;
        gridR = posY / bSize;
    }

    public static int[][] getGrid() {
        return grid;
    }

    private boolean checkGridMove(int x, int y, int[][] grid) {

        int column = x / bSize;
        int row = y / bSize;
        if (grid[column][row] == -1) {
            return false;
        }
        return true;
    }

    class Keyboard extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && posX > 10 && t.isRunning()) {
                nextPosX = posX - movement - 12;
                if (checkGridMove(nextPosX, posY, grid)) {
                    grid[gridC][gridR] -= 2;
                    //
                    posX -= movement;
                    gridC = posX / bSize;
                    grid[gridC][gridR] += 2;
                }
                if ((checkGridMove(nextPosX, posY, grid)) && (grid2[gridC][gridR])) {
                    grid2[gridC][gridR] = false;
                    score++;

                }

                // is pellet eaten?
                pacMan = new ImageIcon("src/images/PacManLeft.gif").getImage();

            } else if (key == KeyEvent.VK_RIGHT && posX < d.width - 35 && t.isRunning()) {
                nextPosX = posX + movement + 11;
                System.out.println(gridC);
                System.out.println(gridR);
                System.out.println(grid2[gridC][gridR]);
                System.out.println(checkGridMove(nextPosX, posY, grid));
                if (checkGridMove(nextPosX, posY, grid)) {
                    grid[gridC][gridR] -= 2;
                    posX += movement;
                    gridC = posX / bSize;
                    grid[gridC][gridR] += 2;

                }
                if ((checkGridMove(nextPosX, posY, grid)) && (grid2[gridC][gridR])) {
                    grid2[gridC][gridR] = false;
                    score++;
                    System.out.println(gridC);
                    System.out.println(gridR);
                    System.out.println(grid2[gridC][gridR]);
                    System.out.println(checkGridMove(nextPosX, posY, grid));
                }
                pacMan = new ImageIcon("src/images/PacManRight.gif").getImage();

            } else if (key == KeyEvent.VK_UP && posY > 10 && t.isRunning()) {
                nextPosY = posY - movement - 12;
                if (checkGridMove(posX, nextPosY, grid)) {
                    grid[gridC][gridR] -= 2;
                    posY -= movement;
                    gridR = posY / bSize;
                    grid[gridC][gridR] += 2;
                }
                if ((checkGridMove(nextPosX, posY, grid)) && (grid2[gridC][gridR])) {
                    grid2[gridC][gridR] = false;
                    score++;

                }
                pacMan = new ImageIcon("src/images/PacManUp.gif").getImage();

            } else if (key == KeyEvent.VK_DOWN && posY < d.height - 75 && t.isRunning()) {
                nextPosY = posY + movement + 10;
                if (checkGridMove(posX, nextPosY, grid)) {
                    grid[gridC][gridR] -= 2;
                    posY += movement;
                    gridR = posY / bSize;
                    grid[gridC][gridR] += 2;
                }
                if ((checkGridMove(nextPosX, posY, grid)) && (grid2[gridC][gridR])) {
                    grid2[gridC][gridR] = false;
                    score++;

                }
                pacMan = new ImageIcon("src/images/PacManDown.gif").getImage();

            } else if (key == KeyEvent.VK_ESCAPE) {
                inGame = false;

            } else if (key == KeyEvent.VK_SPACE) {
                if (paused == false) {
                    t.stop();
                    paused = true;

                } else {
                    t.start();
                    paused = false;
                }
                repaint();
            }

            else if (key == KeyEvent.VK_S) {
                if (inGame == false) {
                    startGame();
                    inGame = true;
                }

                // if ((posX == 10) && (posY == 10) && (t.isRunning())){
                // t.stop();
                //
                // }
                //

            }

            repaint();
        }

    }

    public void showOntoScreen(Graphics2D g2d) {
        // String on screen to play game
        String start = "PRESS S TO START";
        g2d.setColor(Color.white);
        g2d.drawString(start, 130, 180);

    }

    public void startGame() {
        t.start();
        score = 0;
        livesLeft = 3;
        numGhost = 1;
        initLevel();

    }

    public void initLevel() {
        for (int i = 0; i < NUM_BLOCKS; i++) {
            for (int j = 0; j < NUM_BLOCKS; j++) {
                grid2[i][j] = GRID3[i][j];
            }
        }
        posX = 10;
        posY = 10;

        gridC = posX / bSize;
        gridR = posY / bSize;
        numGhost++;

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Font smallFont = new Font("Helvetica", Font.BOLD, 14);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        if (score > 1 && score % 181 == 0) {
            initLevel();
            System.out.println("I am here!");
            if (GRID3[0][0] == false) {
                System.out.println("oh no!");
            }
        }

        if (numGhost == 13) {
            g2d.drawString("Congrats, you won!!", 130, 180);
        }

        // draw grid
        g2d.setColor(Color.green);
        for (int i = 0; i < NUM_BLOCKS; i++) {
            for (int j = 0; j < NUM_BLOCKS; j++) {

                if (grid2[i][j] == true) {
                    g2d.drawImage(pellet, (i * bSize) + 10, (j * bSize) + 10, this);
                } // draw pellet
                else if (grid2[i][j] == false) {
                    g2d.drawImage(removedPellet, (i * bSize) + 10, (j * bSize) + 10, this);
                } // removes pellet

            }
        }
        grid[0][0] = 1;// Remove the first block at the start
        for (int i = 0; i < NUM_BLOCKS; i++) {
            for (int j = 0; j < NUM_BLOCKS; j++) {

                if (grid[i][j] == -1) {
                    g2d.fillRoundRect((i * bSize) + 10, (j * bSize) + 10, bSize, bSize, 1, 1);

                } // draw wall
            }
        }

        g2d.drawImage(pacMan, posX, posY, this);
        // draw the test ghost
        g2d.drawImage(testGhost.getIcon(), testGhost.getGhostX(), testGhost.getGhostY(), this);

        g2d.drawImage(blueGhost.getIcon(), blueGhost.getGhostX(), blueGhost.getGhostY(), this);

        g2d.drawImage(testGhost2.getIcon(), testGhost2.getGhostX(), testGhost2.getGhostY(), this);

        g2d.setFont(smallFont);
        g2d.setColor(Color.white);
        g2d.drawString("Score: " + String.valueOf(score), 276, 380);
        g2d.drawString("Lives Left: ", 10, 380);

        g2d.setColor(Color.green);

        for (short i = 0; i < livesLeft; i++) {
            g2d.drawImage(livesIcon, i * 28 + 90, 365, this);
        }

        if (inGame == false) {
            showOntoScreen(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // make sure that the counter does not overload
        if (counter > 500) {
            counter = 0;
        }
        // update ghost position
        testGhost.move(counter);

        blueGhost.move(counter);

        testGhost2.move(counter);

        counter++;
        repaint();

        //
        // if (checkCollision(posX,posY, Ghost.getGhostX(), Ghost.getGhostY()) == true){
        // livesLeft--;
        // if (livesLeft == 0) {
        // inGame = false;
        // startGame();
        //
        // }
        // posX = 10;
        // posY = 10;
        // gridC = posX/bSize;
        // gridR = posY/bSize;
        // testGhost = new Ghost("src/images/whiteGhost.gif", 50, 150, 5);
        //
        // }

    }

    private boolean checkCollision(int pManX, int pManY, Ghost ghost) {
        int ghostX = ghost.getGhostX();
        int ghostY = ghost.getGhostY();
        int pCX = (pManX + pManX + (pManX + 22) + (pManX + 22)) / 4;
        int pCY = (pManY + pManY + (pManY - 22) + (pManY - 22)) / 4;
        int gCX = (ghostX + ghostX + (ghostX + 22) + (ghostX + 22)) / 4;
        int gCY = (ghostY + ghostY + (ghostY - 22) + (ghostY - 22)) / 4;
        int squared = ((pCX - gCX) * (pCX - gCX)) + ((pCY - gCY) * (pCY - gCY));
        double distance = Math.sqrt(squared);
        // System.out.println(distance);
        if (distance < 20.0) {
            System.out.println("Collision");
            return true;
        }
        return false;
    }

}

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
	private int livesLeft;
	private int posX, posY, nextPosX, nextPosY; // put in PacMan class

	private Timer t;

	private Image pacMan = new ImageIcon("src/images/PacManRight.gif").getImage();
	private Image livesIcon = new ImageIcon("src/images/PacManResting.png").getImage();
	private Image pellet = new ImageIcon("src/images/Pellet.png").getImage();
	//make a test ghost
	private Ghost testGhost = new Ghost("src/images/whiteGhost.gif", 100, 100);
	private int bSize, movement;
	private int gridC, gridR;
	private final int NUM_BLOCKS = 15;
	private final int MAX_GHOSTS = 13;

	private int[][] grid = { 
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, 
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, 1, 1, 1 },
			{ 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, -1, -1, -1, 1, 1, 1, -1, -1, -1, -1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, -1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, -1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, -1, 1, 1 },
			{ 1, 1, 1, -1, -1, -1, 1, 1, 1, -1, -1, -1, -1, 1, 1 },
			{ 1, 1, 1, -1, -1, -1, 1, 1, 1, -1, -1, -1, -1, 1, 1 }, { 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

	public Board(int w, int h) {

		d = new Dimension(w, h);
		livesLeft = 3;
		posX = 10;
		posY = 10;

		addKeyListener(new Keyboard());
		setFocusable(true);
		setBackground(Color.white);
		t = new Timer(40, this);
		t.start();

		bSize = Math.min(360 / 15, 360 / 15);
		movement = bSize/4;
		gridC = posX/bSize;
		gridR = posY/bSize;
	}
	
	private boolean checkGridMove(int x, int y, int[][] grid) {
		
		int column = x/bSize;
		int row = y/bSize;
		if(grid[column][row] == -1) { return false; }
		return true;
	}

	class Keyboard extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			if (key == KeyEvent.VK_LEFT && posX > 10) {
				nextPosX = posX - movement - 12;
				if(checkGridMove(nextPosX, posY, grid)) {
					grid[gridC][gridR] -= 2;
					posX -= movement;
					grid[gridC][gridR] += 2;
				}
				// is pellet eaten?
				pacMan = new ImageIcon("src/images/PacManLeft.gif").getImage();
			} else if (key == KeyEvent.VK_RIGHT && posX < d.width - 35) {
				nextPosX = posX + movement + 11;
				if(checkGridMove(nextPosX, posY, grid)) {
					grid[gridC][gridR] -= 2;
					posX += movement;
					grid[gridC][gridR] += 2;
				}
				
				pacMan = new ImageIcon("src/images/PacManRight.gif").getImage();
			} else if (key == KeyEvent.VK_UP && posY > 10) {
				nextPosY = posY - movement - 12;
				if(checkGridMove(posX, nextPosY, grid)) {
					grid[gridC][gridR] -= 2;
					posY -= movement;
					grid[gridC][gridR] += 2;
				}
				pacMan = new ImageIcon("src/images/PacManUp.gif").getImage();
			} else if (key == KeyEvent.VK_DOWN && posY < d.height - 75) {
				nextPosY = posY + movement + 10;
				if(checkGridMove(posX, nextPosY, grid)) {
					grid[gridC][gridR] -= 2;
					posY += movement;
					grid[gridC][gridR] += 2;
				}
				pacMan = new ImageIcon("src/images/PacManDown.gif").getImage();
//			else if(key == KeyEvent.VK_ESCAPE) {
//				//quit the game
//			}
//			else if(key == KeyEvent.VK_SPACE) {
//				//pause game
//				//stop timer
//				//pop up window saying game is paused??
//			}
			}
			repaint();
		}

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Font smallFont = new Font("Helvetica", Font.BOLD, 14);
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, d.width, d.height);

		// draw grid
		g2d.setColor(Color.green);
		for (int i = 0; i < NUM_BLOCKS; i++) {
			for (int j = 0; j < NUM_BLOCKS; j++) {
				if (grid[i][j] == -1) {
					g2d.fillRoundRect((i * bSize) + 10, (j * bSize) + 10, bSize, bSize, 1, 1);
					;
				} // draw wall
				else if (grid[i][j] == 1) {
					g2d.drawImage(pellet, (i * bSize) + 10, (j * bSize) + 10, this);
				} // draw pellet
			}
		}

		g2d.drawImage(pacMan, posX, posY, this);
		//draw the test ghost
		g2d.drawImage(testGhost.getIcon(), testGhost.getGhostX(), testGhost.getGhostY(), this);

		g2d.setFont(smallFont);
		g2d.setColor(Color.white);
		g2d.drawString("Score: 0 ", 276, 380);
		g2d.drawString("Lives Left: ", 10, 380);

		g2d.setColor(Color.green);

		for (short i = 0; i < livesLeft; i++) {
			g2d.drawImage(livesIcon, i * 28 + 90, 365, this);
		}

		Toolkit.getDefaultToolkit().sync();
		g2d.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// update ghost position
	    testGhost.move();
		repaint();
	}
}
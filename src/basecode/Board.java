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
import java.util.ArrayList;
import java.util.List;

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
	
	private Ghost blueGhost1 = new Ghost("src/images/blueGhost1.gif", 50, 150, 5);
    private Ghost blueGhost2 = new Ghost("src/images/blueGhost2.gif", 60, 160, 6);
    private Ghost greenGhost1 = new Ghost("src/images/greenGhost1.gif", 70, 170, 5);
    private Ghost greenGhost2 = new Ghost("src/images/greenGhost2.gif", 80, 180, 4);
    private Ghost mauveGhost = new Ghost("src/images/mauveGhost.gif", 90, 190, 3);
    private Ghost orangeGhost = new Ghost("src/images/orangeGhost.gif", 100, 200, 2);
    private Ghost pinkGhost = new Ghost("src/images/pinkGhost.gif", 110, 210, 5);
    private Ghost purpleGhost = new Ghost("src/images/purpleGhost.gif", 120, 220, 6);
    private Ghost redGhost = new Ghost("src/images/redGhost.gif", 130, 230, 4);
    private Ghost tealGhost = new Ghost("src/images/tealGhost.gif", 140, 240, 3);
    private Ghost whiteGhost = new Ghost("src/images/whiteGhost.gif", 150, 250, 5);
    private Ghost yellowGhost = new Ghost("src/images/yellowGhost.gif", 160, 150, 5);
	
	private List<Ghost> ghosts = new ArrayList<>();

	private int bSize, movement;
	private int gridC, gridR;
	private final int NUM_BLOCKS = 15;
	private final int MAX_GHOSTS = 12;
	public int counter = 0;
	private int numLevel;
	private int numGhost;
	private int newGhosts;
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
		numLevel = 1;
		numGhost = 2;
		newGhosts = -1;
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
		newGhosts++;
		
		if (newGhosts == 1) {
			mauveGhost.setActive(true);
		} else if (newGhosts == 2) {
			tealGhost.setActive(true);
		} else if (newGhosts == 3) {
			greenGhost2.setActive(true);
		} else if (newGhosts == 4) {
			whiteGhost.setActive(true);
		} else if (newGhosts == 5) {
			blueGhost2.setActive(true);
		} else if (newGhosts == 6) {
			pinkGhost.setActive(true);
		} else if (newGhosts == 7) {
			purpleGhost.setActive(true);
		} else if (newGhosts == 8) {
			redGhost.setActive(true);
		} else if (newGhosts == 9) {
			greenGhost2.setActive(true);
		} else if (newGhosts == 10) {
			orangeGhost.setActive(true);
		}
		
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
			numLevel++;
			initLevel();
		}

		if (numGhost > MAX_GHOSTS) {
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
		g2d.drawImage(blueGhost1.getIcon(), blueGhost1.getGhostX(), blueGhost1.getGhostY(), this);
		blueGhost1.setActive(true);

		g2d.drawImage(yellowGhost.getIcon(), yellowGhost.getGhostX(), yellowGhost.getGhostY(), this);
		yellowGhost.setActive(true);
		
		ghosts.add(blueGhost1);
		ghosts.add(yellowGhost);
		
		if (mauveGhost.getActive()) {
			ghosts.add(mauveGhost);
			g2d.drawImage(mauveGhost.getIcon(), mauveGhost.getGhostX(), mauveGhost.getGhostY(), this);
		} if (tealGhost.getActive()) {
			ghosts.add(tealGhost);
			g2d.drawImage(tealGhost.getIcon(), tealGhost.getGhostX(), tealGhost.getGhostY(), this);
		}
		
		/*if (newGhosts >= 1) {
			g2d.drawImage(mauveGhost.getIcon(), mauveGhost.getGhostX(), mauveGhost.getGhostY(), this);
			mauveGhost.setActive(true);
			ghosts.add(mauveGhost);
		} if (newGhosts >= 2) {
			g2d.drawImage(tealGhost.getIcon(), tealGhost.getGhostX(), tealGhost.getGhostY(), this);
			tealGhost.setActive(true);
			ghosts.add(tealGhost);
		} if (newGhosts >= 3) {
			g2d.drawImage(greenGhost1.getIcon(), greenGhost1.getGhostX(), greenGhost1.getGhostY(), this);
			greenGhost1.setActive(true);
			ghosts.add(greenGhost1);
		} if (newGhosts >= 4) {
			g2d.drawImage(whiteGhost.getIcon(), whiteGhost.getGhostX(), whiteGhost.getGhostY(), this);
			whiteGhost.setActive(true);
			ghosts.add(whiteGhost);
		} if (newGhosts >= 5) {
			g2d.drawImage(blueGhost2.getIcon(), blueGhost2.getGhostX(), blueGhost2.getGhostY(), this);
			blueGhost2.setActive(true);
			ghosts.add(blueGhost2);
		} if (newGhosts >= 6) {
			g2d.drawImage(pinkGhost.getIcon(), pinkGhost.getGhostX(), pinkGhost.getGhostY(), this);
			pinkGhost.setActive(true);
			ghosts.add(pinkGhost);
		} if (newGhosts >= 7) {
			g2d.drawImage(purpleGhost.getIcon(), purpleGhost.getGhostX(), purpleGhost.getGhostY(), this);
			purpleGhost.setActive(true);
			ghosts.add(purpleGhost);
		} if (newGhosts >= 8) {
			g2d.drawImage(redGhost.getIcon(), redGhost.getGhostX(), redGhost.getGhostY(), this);
			redGhost.setActive(true);
			ghosts.add(redGhost);
		} if (newGhosts >= 9) {
			g2d.drawImage(greenGhost2.getIcon(), greenGhost2.getGhostX(), greenGhost2.getGhostY(), this);
			greenGhost2.setActive(true);
			ghosts.add(greenGhost2);
		} if (newGhosts >= 10) {
			g2d.drawImage(orangeGhost.getIcon(), orangeGhost.getGhostX(), orangeGhost.getGhostY(), this);
			orangeGhost.setActive(true);
			ghosts.add(orangeGhost);
		}*/

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
		
		if (blueGhost1.getActive()) {
			blueGhost1.move(counter);
		} if (blueGhost2.getActive()) {
			blueGhost2.move(counter);
		} if (greenGhost1.getActive()) {
			greenGhost1.move(counter);
		} if (greenGhost2.getActive()) {
			greenGhost2.move(counter);
		} if (mauveGhost.getActive()) {
			mauveGhost.move(counter);
		} if (orangeGhost.getActive()) {
			orangeGhost.move(counter);
		} if (pinkGhost.getActive()) {
			pinkGhost.move(counter);
		} if (purpleGhost.getActive()) {
			purpleGhost.move(counter);
		} if (redGhost.getActive()) {
			redGhost.move(counter);
		} if (tealGhost.getActive()) {
			tealGhost.move(counter);
		} if (whiteGhost.getActive()) {
			whiteGhost.move(counter);
		} if (yellowGhost.getActive()) {
			yellowGhost.move(counter);
		}

		counter++;
		repaint();

		for (Ghost ghost: ghosts) {
			if (checkCollision(posX, posY, ghost)) {
				livesLeft--;
				if (livesLeft == 0) {
				inGame = false;
				ghost.setActive(false);
				//break;
				startGame();
				}
				
				posX = 10;
				posY = 10;
				gridC = posX/bSize;
				gridR = posY/bSize;
				
				if (blueGhost1.getActive()) {
					blueGhost1 = new Ghost("src/images/blueGhost1.gif", 50, 150, 5);
				} if (blueGhost2.getActive()) {
					blueGhost2 = new Ghost("src/images/blueGhost2.gif", 60, 160, 6);
				} if (greenGhost1.getActive()) {
					greenGhost1 = new Ghost("src/images/greenGhost1.gif", 70, 170, 5);
				} if (greenGhost2.getActive()) {
					greenGhost2 = new Ghost("src/images/greenGhost2.gif", 80, 180, 4);
				} if (mauveGhost.getActive()) {
					mauveGhost = new Ghost("src/images/mauveGhost.gif", 90, 190, 3);
				} if (orangeGhost.getActive()) {
					orangeGhost = new Ghost("src/images/orangeGhost.gif", 100, 200, 2);
				} if (pinkGhost.getActive()) {
					pinkGhost = new Ghost("src/images/pinkGhost.gif", 110, 210, 5);
				} if (purpleGhost.getActive()) {
					purpleGhost = new Ghost("src/images/purpleGhost.gif", 120, 220, 6);
				} if (redGhost.getActive()) {
					redGhost = new Ghost("src/images/redGhost.gif", 130, 230, 4);
				} if (tealGhost.getActive()) {
					tealGhost = new Ghost("src/images/tealGhost.gif", 140, 240, 3);
				} if (whiteGhost.getActive()) {
					whiteGhost = new Ghost("src/images/whiteGhost.gif", 150, 250, 5);
				} if (yellowGhost.getActive()) {
					yellowGhost = new Ghost("src/images/yellowGhost.gif", 160, 150, 5);
				}
			}
		}
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

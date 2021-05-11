package basecode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Board class manages the GUI, game interactions, and takes in keyboard
 * actions.
 * 
 * * @author TIGERFISH TEAM
 */
public class Board extends JPanel implements ActionListener {

	// width and height of the board
	private Dimension d;
	// Number of times able to play after collision with ghost and number of pellets
	// eaten
	private int livesLeft, score;
	// pacman x and y positions
	private int posX, posY;

	private Timer t;

	// pacman, pacman life, and pellet icons
	private Image pacMan = new ImageIcon("src/images/PacManRight.gif").getImage();
	private Image livesIcon = new ImageIcon("src/images/PacManResting.png").getImage();
	private Image pellet = new ImageIcon("src/images/Pellet.png").getImage();
	private Image removedPellet = new ImageIcon("src/images/removedPellet.png").getImage();

	// Create ghosts
	private Ghost blueGhost1 = new Ghost("src/images/blueGhost1.gif", 50, 150, 1);
	private Ghost blueGhost2 = new Ghost("src/images/blueGhost2.gif", 60, 160, 8);
	private Ghost greenGhost1 = new Ghost("src/images/greenGhost1.gif", 70, 170, 5);
	private Ghost greenGhost2 = new Ghost("src/images/greenGhost2.gif", 80, 180, 4);
	private Ghost mauveGhost = new Ghost("src/images/mauveGhost.gif", 90, 190, 3);
	private Ghost orangeGhost = new Ghost("src/images/orangeGhost.gif", 100, 150, 2);
	private Ghost pinkGhost = new Ghost("src/images/pinkGhost.gif", 110, 150, 5);
	private Ghost purpleGhost = new Ghost("src/images/purpleGhost.gif", 120, 150, 6);
	private Ghost redGhost = new Ghost("src/images/redGhost.gif", 130, 150, 4);
	private Ghost tealGhost = new Ghost("src/images/tealGhost.gif", 140, 150, 3);
	private Ghost whiteGhost = new Ghost("src/images/whiteGhost.gif", 150, 150, 7);
	private Ghost yellowGhost = new Ghost("src/images/yellowGhost.gif", 160, 150, 5);

	// List to hold active ghosts
	private List<Ghost> ghosts = new ArrayList<>();

	// size of blocks in maze
	private int bSize, movement;
	private int gridC, gridR;

	// Number of pellets across and down the board
	private final int NUM_BLOCKS = 15;

	// Maximum number of ghosts allowed in the game
	private final int MAX_GHOSTS = 12;
	public int counter = 0;

	// number of ghosts on current level
	private int numGhost;

	// inGame and pauses boolean variables
	public boolean inGame = false;
	public boolean paused = false;

	// Maze for pacman game
	// 1 = blank space, -1 = wall
	private static int[][] grid = { { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, 1, 1, 1 },
			{ 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, -1, -1, -1, 1, 1, 1, -1, -1, -1, -1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, -1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, -1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, -1, 1, 1 },
			{ 1, 1, 1, -1, -1, -1, 1, 1, 1, -1, -1, -1, -1, 1, 1 },
			{ 1, 1, 1, -1, -1, -1, 1, 1, 1, -1, -1, -1, -1, 1, 1 }, { 1, 1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

	// true = pellet location
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

	// grid of pellet locations for grid2 to reset to each level
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
		// initial lives for pacman
		livesLeft = 3;

		// spawn position for pacman
		posX = 10;
		posY = 10;

		addKeyListener(new Keyboard());
		setFocusable(true);
		setBackground(Color.white);

		// timer for game
		t = new Timer(40, this);

		// standardizes block and grid values
		bSize = Math.min(360 / 15, 360 / 15);
		movement = bSize / 4;
		gridC = posX / bSize;
		gridR = posY / bSize;
	}

	/**
	 * Getter for the integer grid
	 *
	 * @return grid
	 */
	public static int[][] getGrid() {
		return grid;
	}

	/**
	 * Iterates over grid2, checking if all pellets were eaten.
	 *
	 * @return true/false
	 */
	private boolean checkLevelClear() {
		for (int c = 0; c < 15; c++) {
			for (int r = 0; r < 15; r++) {
				if (grid2[c][r] == true) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * This method checks if a proposed move for Pac-Man is valid.
	 * 
	 * @param x    the x-coordinate
	 * @param y    the y-coordinate
	 * @param grid the game board
	 * @return true or false if the move is valid or not
	 */
	private boolean checkGridMove(int x, int y, int[][] grid) {

		int column = x / bSize;
		int row = y / bSize;
		// if something is -1 it is the maze, it's an invalid moves
		try {

			if (grid[column][row] != -1) {
				return true;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}

		return false;
	}

	class Keyboard extends KeyAdapter {
		/**
		 * This method checks the key event that are inputed by the user as the game
		 * conitues. It controls the directional movement for Pacman, it checks for
		 * pellets so they be eaten, and it gives special actions to the space key,
		 * escape key, and the s key
		 * 
		 * @param KeyEvent e
		 */
		@Override
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			if (key == KeyEvent.VK_LEFT && posX > 10 && t.isRunning()) {// For the left arrow key
				if (checkGridMove(posX - movement - 12, posY, grid)) {// checks to if the newest left position is a
																		// wall, if not a wall it allow pacman to move
																		// into the new left space
					grid[gridC][gridR] -= 2;
					posX -= movement;
					gridC = posX / bSize;
					grid[gridC][gridR] += 2;
				}
				if ((checkGridMove(posX - movement - 12, posY, grid)) && (grid2[gridC][gridR])) {// checks to if the
																									// newest right
					// position is a wall, if not a
					// wall and if the grid position
					// in grid 2 is true, there's
					// a pellet in that space
					grid2[gridC][gridR] = false;
					score++;

				}
				pacMan = new ImageIcon("src/images/PacManLeft.gif").getImage();// creates a pacman icon for the new
																				// postion

			} else if (key == KeyEvent.VK_RIGHT && posX < d.width - 35 && t.isRunning()) {// For the right arrow key
				if (checkGridMove(posX + movement + 11, posY, grid)) {// checks to if the newest right position is a
																		// wall, if not a wall it allow pacman to move
																		// into the new right space
					grid[gridC][gridR] -= 2;// updates the current grid position with Pacman's value
					posX += movement;
					gridC = posX / bSize;
					grid[gridC][gridR] += 2;
				}
				if ((checkGridMove(posX + movement + 11, posY, grid)) && (grid2[gridC][gridR])) {// checks to if the
																									// newest right
					// position is a wall, if not a
					// wall and if the grid position
					// in grid 2 is true, there's
					// a pellet in that space
					grid2[gridC][gridR] = false;
					grid2[gridC][gridR] = false;
					score++;
				}
				pacMan = new ImageIcon("src/images/PacManRight.gif").getImage();// creates a pacman icon for the new
																				// position

			} else if (key == KeyEvent.VK_UP && posY > 10 && t.isRunning()) {// For the up arrow key
				if (checkGridMove(posX, posY - movement - 12, grid)) { // checks to if the newest up position is a wall,
																		// if not a wall it allow pacman to move into
																		// the new up space
					grid[gridC][gridR] -= 2;// updates the current grid position with Pacman's vaule
					posY -= movement;
					gridR = posY / bSize;
					grid[gridC][gridR] += 2;
				}
				if ((checkGridMove(posX, posY - movement - 12, grid)) && (grid2[gridC][gridR])) {// checks to if the
																									// newest right
					// position is a wall, if not a
					// wall and if the grid position
					// in grid 2 is true, there's
					// a pellet in that space
					grid2[gridC][gridR] = false;

					grid2[gridC][gridR] = false;
					score++;
				}
				pacMan = new ImageIcon("src/images/PacManUp.gif").getImage();// creates a pacman icon for the new
																				// postion

			} else if (key == KeyEvent.VK_DOWN && posY < d.height - 75 && t.isRunning()) {// For the down arrow key
				if (checkGridMove(posX, posY + movement + 10, grid)) {// checks to if the newest up position is a wall,
																		// if not a wall it allow pacman to move into
																		// the new up space
					grid[gridC][gridR] -= 2;// updates the current grid position with Pacman's value
					posY += movement;
					gridR = posY / bSize;
					grid[gridC][gridR] += 2;
				}
				if ((checkGridMove(posX, posY + movement + 10, grid)) && (grid2[gridC][gridR])) {// checks to if the
																									// newest up
					// position is a wall, if not a
					// wall and if the grid position
					// in grid 2 is true, there's
					// a pellet in that space
					grid2[gridC][gridR] = false;
					score++;
				}
				pacMan = new ImageIcon("src/images/PacManDown.gif").getImage();// creates a pacman icon for the new
																				// postion

			} else if (key == KeyEvent.VK_ESCAPE) { // for escape key, ingame - false, reset pacman game
				inGame = false;

			} else if (key == KeyEvent.VK_SPACE) { // for the space value, pause the game and unpause if space if hit
													// again (toggle)
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
				if (inGame == false) { // for the S key value, start game
					startGame();
					inGame = true;
				}
			}
			repaint();
		}

	}

	/**
	 * Paints "PRESS S TO START" message on the grid.
	 */
	public void showOntoScreen(Graphics2D g2d) {
		// String on screen to play game
		String start = "PRESS S TO START";
		g2d.setColor(Color.white);
		g2d.drawString(start, 130, 180);

	}

	/**
	 * Initializes variables for the start of the game, and initializes the first
	 * level.
	 */
	public void startGame() {
		t.start();
		score = 0;
		livesLeft = 3;
		numGhost = 2;
		ghosts.clear();
		initLevel();
	}

	/**
	 * Initializes a new level by repainting pellets onto the grid.
	 */
	public void initLevel() {
		// reset grid2
		for (int i = 0; i < NUM_BLOCKS; i++) {
			for (int j = 0; j < NUM_BLOCKS; j++) {
				grid2[i][j] = GRID3[i][j];
			}
		}

		// reset pacman's position
		posX = 10;
		posY = 10;
		gridC = posX / bSize;
		gridR = posY / bSize;

		// add +1 ghost for each level
		if (numGhost == 3) {
			mauveGhost.setActive(true);
		} else if (numGhost == 4) {
			tealGhost.setActive(true);
		} else if (numGhost == 5) {
			greenGhost2.setActive(true);
		} else if (numGhost == 6) {
			whiteGhost.setActive(true);
		} else if (numGhost == 7) {
			blueGhost2.setActive(true);
		} else if (numGhost == 8) {
			pinkGhost.setActive(true);
		} else if (numGhost == 9) {
			purpleGhost.setActive(true);
		} else if (numGhost == 10) {
			redGhost.setActive(true);
		} else if (numGhost == 11) {
			greenGhost2.setActive(true);
		} else if (numGhost == 12) {
			orangeGhost.setActive(true);
		}
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Font smallFont = new Font("Helvetica", Font.BOLD, 14);
		Graphics2D g2d = (Graphics2D) g;

		// draw the board background
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, d.width, d.height);

		// draw grid
		g2d.setColor(Color.green);
		for (int i = 0; i < NUM_BLOCKS; i++) {
			for (int j = 0; j < NUM_BLOCKS; j++) {
				// draw pellet
				if (grid2[i][j] == true) {
					g2d.drawImage(pellet, (i * bSize) + 10, (j * bSize) + 10, this);
				}
				// removed pellet
				else if (grid2[i][j] == false) {
					g2d.drawImage(removedPellet, (i * bSize) + 10, (j * bSize) + 10, this);
				}

			}
		}

		// remove random block that appears at the first index
		grid[0][0] = 1;
		for (int i = 0; i < NUM_BLOCKS; i++) {
			for (int j = 0; j < NUM_BLOCKS; j++) {
				if (grid[i][j] == -1) {
					g2d.fillRoundRect((i * bSize) + 10, (j * bSize) + 10, bSize, bSize, 1, 1);
				}
			}
		}

		// draw pac man
		g2d.drawImage(pacMan, posX, posY, this);

		// add and draw ghosts
		ghosts.add(blueGhost1);
		ghosts.add(yellowGhost);
		g2d.drawImage(blueGhost1.getIcon(), blueGhost1.getGhostX(), blueGhost1.getGhostY(), this);
		blueGhost1.setActive(true);
		g2d.drawImage(yellowGhost.getIcon(), yellowGhost.getGhostX(), yellowGhost.getGhostY(), this);
		yellowGhost.setActive(true);

		// add and draw additional ghosts for each level
		if (numGhost == 3) {
			ghosts.add(blueGhost2);

			for (Ghost ghost : ghosts) {
				g2d.drawImage(ghost.getIcon(), ghost.getGhostX(), ghost.getGhostY(), this);
				ghost.setActive(true);
			}
		} else if (numGhost == 4) {
			ghosts.add(blueGhost2);
			ghosts.add(greenGhost1);

			for (Ghost ghost : ghosts) {
				g2d.drawImage(ghost.getIcon(), ghost.getGhostX(), ghost.getGhostY(), this);
				ghost.setActive(true);
			}
		} else if (numGhost == 5) {
			ghosts.add(blueGhost2);
			ghosts.add(greenGhost1);
			ghosts.add(greenGhost2);

			for (Ghost ghost : ghosts) {
				g2d.drawImage(ghost.getIcon(), ghost.getGhostX(), ghost.getGhostY(), this);
				ghost.setActive(true);
			}
		} else if (numGhost == 6) {
			ghosts.add(blueGhost2);
			ghosts.add(greenGhost1);
			ghosts.add(greenGhost2);
			ghosts.add(mauveGhost);

			for (Ghost ghost : ghosts) {
				g2d.drawImage(ghost.getIcon(), ghost.getGhostX(), ghost.getGhostY(), this);
				ghost.setActive(true);
			}
		} else if (numGhost == 7) {
			ghosts.add(blueGhost2);
			ghosts.add(greenGhost1);
			ghosts.add(greenGhost2);
			ghosts.add(mauveGhost);
			ghosts.add(orangeGhost);

			for (Ghost ghost : ghosts) {
				g2d.drawImage(ghost.getIcon(), ghost.getGhostX(), ghost.getGhostY(), this);
				ghost.setActive(true);
			}
		} else if (numGhost == 8) {
			ghosts.add(blueGhost2);
			ghosts.add(greenGhost1);
			ghosts.add(greenGhost2);
			ghosts.add(mauveGhost);
			ghosts.add(orangeGhost);
			ghosts.add(pinkGhost);

			for (Ghost ghost : ghosts) {
				g2d.drawImage(ghost.getIcon(), ghost.getGhostX(), ghost.getGhostY(), this);
				ghost.setActive(true);
			}
		} else if (numGhost == 9) {
			ghosts.add(blueGhost2);
			ghosts.add(greenGhost1);
			ghosts.add(greenGhost2);
			ghosts.add(mauveGhost);
			ghosts.add(orangeGhost);
			ghosts.add(pinkGhost);
			ghosts.add(purpleGhost);

			for (Ghost ghost : ghosts) {
				g2d.drawImage(ghost.getIcon(), ghost.getGhostX(), ghost.getGhostY(), this);
				ghost.setActive(true);
			}
		} else if (numGhost == 10) {
			ghosts.add(blueGhost2);
			ghosts.add(greenGhost1);
			ghosts.add(greenGhost2);
			ghosts.add(mauveGhost);
			ghosts.add(orangeGhost);
			ghosts.add(pinkGhost);
			ghosts.add(purpleGhost);
			ghosts.add(redGhost);

			for (Ghost ghost : ghosts) {
				g2d.drawImage(ghost.getIcon(), ghost.getGhostX(), ghost.getGhostY(), this);
				ghost.setActive(true);
			}
		} else if (numGhost == 11) {
			ghosts.add(blueGhost2);
			ghosts.add(greenGhost1);
			ghosts.add(greenGhost2);
			ghosts.add(mauveGhost);
			ghosts.add(orangeGhost);
			ghosts.add(pinkGhost);
			ghosts.add(purpleGhost);
			ghosts.add(redGhost);
			ghosts.add(tealGhost);

			for (Ghost ghost : ghosts) {
				g2d.drawImage(ghost.getIcon(), ghost.getGhostX(), ghost.getGhostY(), this);
				ghost.setActive(true);
			}
		} else if (numGhost == 12) {
			ghosts.add(blueGhost2);
			ghosts.add(greenGhost1);
			ghosts.add(greenGhost2);
			ghosts.add(mauveGhost);
			ghosts.add(orangeGhost);
			ghosts.add(pinkGhost);
			ghosts.add(purpleGhost);
			ghosts.add(redGhost);
			ghosts.add(tealGhost);
			ghosts.add(whiteGhost);

			for (Ghost ghost : ghosts) {
				g2d.drawImage(ghost.getIcon(), ghost.getGhostX(), ghost.getGhostY(), this);
				ghost.setActive(true);
			}
		}

		// draw the score and number of lives left
		g2d.setFont(smallFont);
		g2d.setColor(Color.white);
		g2d.drawString("Score: " + String.valueOf(score), 276, 380);
		g2d.drawString("Lives Left: ", 10, 380);

		g2d.setColor(Color.green);

		for (short i = 0; i < livesLeft; i++) {
			g2d.drawImage(livesIcon, i * 28 + 90, 365, this);
		}

		// start game message
		if (inGame == false) {
			showOntoScreen(g2d);
		}

		// end and reset the game if the max level has been completed
		if (numGhost > MAX_GHOSTS) {
			t.stop();
			inGame = false;
			String win = "Congrats, you won!";
			g2d.setColor(Color.white);
			g2d.drawString(win, 130, 200);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// make sure that the counter does not overload
		if (counter > 500) {
			counter = 0;
		}

		// level up when board is cleared by adding ghost and initializing level
		if (checkLevelClear()) {
			numGhost++;
			initLevel();
		}

		// update ghost position
		if (blueGhost1.getActive()) {
			blueGhost1.move(counter);
		}
		if (blueGhost2.getActive()) {
			blueGhost2.move(counter);
		}
		if (greenGhost1.getActive()) {
			greenGhost1.move(counter);
		}
		if (greenGhost2.getActive()) {
			greenGhost2.move(counter);
		}
		if (mauveGhost.getActive()) {
			mauveGhost.move(counter);
		}
		if (orangeGhost.getActive()) {
			orangeGhost.move(counter);
		}
		if (pinkGhost.getActive()) {
			pinkGhost.move(counter);
		}
		if (purpleGhost.getActive()) {
			purpleGhost.move(counter);
		}
		if (redGhost.getActive()) {
			redGhost.move(counter);
		}
		if (tealGhost.getActive()) {
			tealGhost.move(counter);
		}
		if (whiteGhost.getActive()) {
			whiteGhost.move(counter);
		}
		if (yellowGhost.getActive()) {
			yellowGhost.move(counter);
		}

		// add to the counter to pass into ghost so it doesn't move erratically
		counter++;
		repaint();

		for (Ghost ghost : ghosts) {
			if (checkCollision(posX, posY, ghost)) {
				livesLeft--;
				if (livesLeft == 0) {
					inGame = false;
					ghost.setActive(false);
					t.stop();
				}

				posX = 10;
				posY = 10;
				gridC = posX / bSize;
				gridR = posY / bSize;

				for (Ghost g : ghosts) {
					if (g.getActive()) {
						g.setSpawn();
					}
				}

			}
		}
	}

	/**
	 * Checks if there is a collision between pacman and a ghost
	 * 
	 * @param pManX - the x position of the pacman
	 * @param pManY - the y position of the pacman
	 * @param ghost - the ghost object to check if there is a collision with pacman
	 * @return true if there is a collision
	 */
	private boolean checkCollision(int pManX, int pManY, Ghost ghost) {
		int ghostX = ghost.getGhostX();
		int ghostY = ghost.getGhostY();
		// get the center point of pacman
		int pCX = (pManX + pManX + (pManX + 22) + (pManX + 22)) / 4;
		int pCY = (pManY + pManY + (pManY - 22) + (pManY - 22)) / 4;
		// get the center points of ghost
		int gCX = (ghostX + ghostX + (ghostX + 22) + (ghostX + 22)) / 4;
		int gCY = (ghostY + ghostY + (ghostY - 22) + (ghostY - 22)) / 4;
		// whats going to go in square root
		int squared = ((pCX - gCX) * (pCX - gCX)) + ((pCY - gCY) * (pCY - gCY));
		// find distance between centers
		double distance = Math.sqrt(squared);
		// if close together then there is a collision
		if (distance < 20.0) {
			return true;
		}
		return false;
	}

}

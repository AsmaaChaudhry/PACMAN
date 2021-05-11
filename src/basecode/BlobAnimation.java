package basecode;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 * This class creates a JFrame window for the PACMAN game.
 */
public class BlobAnimation extends JFrame {
	// window dimensions
	private final int WINDOW_WIDTH = 390;
	private final int WINDOW_HEIGHT = 420;

	// create new instance of Board class
	private Board myBoard = new Board(WINDOW_WIDTH, WINDOW_HEIGHT);

	/**
	 * Set the Title and Window For BlobAnimation.
	 */
	public BlobAnimation() {

		setTitle("PAC-MAN");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		setLocationRelativeTo(null);
		add(myBoard, BorderLayout.CENTER);
		setVisible(true);
	}

	public static void main(String[] args) {
		// call on BlobAnimation instnace to create new Window for game play
		BlobAnimation myGame = new BlobAnimation();

	}
}

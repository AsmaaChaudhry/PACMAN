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


public class Board extends JPanel implements ActionListener {

    private Dimension d;
    private int livesLeft;
    private int posX, posY;
    
    private Image blob = new ImageIcon("src/images/blob.png").getImage();    
    
    

    public Board(int w, int h) {
        
        d = new Dimension(w, h);
        livesLeft = 3; 
        posX = 10;
        posY = 10;
            
        addKeyListener(new Keyboard());
        setFocusable(true);
        setBackground(Color.white);  
    }


    @Override
    public void addNotify() {
        super.addNotify();
    }

    
    class Keyboard extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && posX > 0) {
                posX = posX - 2;
            } else if (key == KeyEvent.VK_RIGHT && posX < d.width - 36) {
                posX = posX + 2;
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
        g2d.drawImage(blob, posX, posY, this);
        
        g2d.setFont(smallFont);
        g2d.setColor(Color.white);
        g2d.drawString("Score: 0 ", 276, 355);
        g2d.drawString("Lives Left: ", 10, 355);
        
        for (short i = 0; i < livesLeft; i++) {
            g2d.drawImage(blob, i*28+ 90, 340, this);
        }
        
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }
}

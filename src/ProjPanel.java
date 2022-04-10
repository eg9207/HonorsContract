import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ProjPanel extends JPanel {
    // PROPERTIES
    // Projectile/Ball Coordinates
    public int intBallX = 85;
    public int intBallY = 340;

    Random rand = new Random();
    int targetX = rand.nextInt(900-150)+150;
    int targetY = rand.nextInt(335-5) + 5;
    BufferedImage target;

    // METHODS
    public void paintComponent(Graphics g) {
        try {
            target = ImageIO.read((new File("images/target.png")));
        } catch (IOException e){

        }

        Graphics2D g2 = (Graphics2D)g;
        super.paintComponent(g2);

        // Draw Sidebar
        g2.setColor(new Color(240,240,240));
        g2.fillRect(0,0,83,360);

        // Draw Border Lines
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLACK);
        g2.drawLine(84,0,84,360);
        g2.drawLine(0,360,960,360);

        // Grid
        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.DARK_GRAY);

            for (int j = 0; j < 18; j++) {
                g2.drawLine(85+10+(j*50), 354, 85+10+(j*50), 360);
                g2.drawLine(85, (j*50), 90, (j*50));
                g2.drawString(j*50 + "",85+10+(j*50), 349);
                g2.drawString(j*50 + "",95,349-(j*50));
            }


        // Draw Ball/Projectile
        g2.setColor(Color.RED);
        g2.fillOval(intBallX, intBallY, 20, 20);

        g2.drawImage(target, targetX, targetY, 50,30,this);


    }
    // CONSTRUCTORS
    public ProjPanel () {
        super();
        setBackground(Color.WHITE);
    }
}

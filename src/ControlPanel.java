import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ControlPanel extends JPanel {
    // PROPERTIES
    public int intProjType = 1;

    // METHODS
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    // CONSTRUCTOR
    public ControlPanel () {
        super();
    }
}

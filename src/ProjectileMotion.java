

import javax.swing.*;

public class ProjectileMotion {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Modern Look&Feel UI
        } catch (Exception e) {
        }
        new GUI(); // Open the projectile motion gui
    }
}

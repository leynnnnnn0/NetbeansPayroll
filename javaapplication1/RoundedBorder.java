package javaapplication1;

import javax.swing.border.Border;
import java.awt.*;

public class RoundedBorder implements Border {
    // This is the code for the borders of each panel
    // Chat gpt code
    private int radius;
    private Color borderColor; // Added variable for border color

    public RoundedBorder(int radius, Color borderColor) {
        this.radius = radius;
        this.borderColor = borderColor; // Set border color
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(borderColor); // Use specified border color
        g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(radius, radius, radius, radius);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
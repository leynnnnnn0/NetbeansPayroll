package javaapplication1;

import javax.swing.SwingUtilities;

public class Application {

    public static void main(String[] args) {
        // Multithreading so other thing will still run even this is not still finish running
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
    
}

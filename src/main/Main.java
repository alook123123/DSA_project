package main;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        // Set up the frame with CardLayout
        this.setTitle("Last Day On Earth");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Custom exit handling
        this.setResizable(false);

        // Create a CardLayout to switch between panels
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        // Create the menu and game panels
        MenuPanel menuPanel = new MenuPanel(mainPanel, cardLayout);
        Panel gamePanel = new Panel();

        // Add panels to the main panel
        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(gamePanel, "Game");

        // Add main panel to the frame
        this.add(mainPanel);
        this.pack();
        this.setPreferredSize(new Dimension(960, 640)); // Ensure consistent size
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // Custom exit confirmation
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to exit?",
                        "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION
                );
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    public static void main(String[] args) {
        new Main();
    }
}

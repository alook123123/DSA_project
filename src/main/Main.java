package main;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private JLayeredPane layeredPane;
    private Panel gamePanel;
    private GameOverPanel gameOverPanel;
    private MenuPanel menuPanel;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public Main() {
        // Set up the frame with CardLayout
        this.setTitle("Last Day On Earth");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);

        // Create a CardLayout to switch between panels
        cardLayout = new CardLayout(); // GÁN cho biến THÀNH VIÊN
        mainPanel = new JPanel(cardLayout);

        menuPanel = new MenuPanel(mainPanel, cardLayout, this);
        MapPanel mapPanel = new MapPanel(mainPanel, cardLayout, this);
        gamePanel = new Panel(mainPanel, cardLayout, this);
        // Add panels to the main panel
        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(gamePanel, "Game");
        mainPanel.add(mapPanel, "Map");

        layeredPane = new JLayeredPane();
        int width = 1008, height = 720;
        mainPanel.setBounds(0, 0, width, height);
        layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int scaledWidth = (int) (screenSize.width * 0.9);
        int scaledHeight = (int) (screenSize.height * 1);

        gameOverPanel = new GameOverPanel(this, () -> {
            gamePanel.resetGame();
            hideGameOver();
            cardLayout.show(mainPanel, "Game");
            gamePanel.requestFocusInWindow();
        });
        gameOverPanel.setBounds(0, 0, width, height);
        gameOverPanel.setVisible(false);
        layeredPane.add(gameOverPanel, JLayeredPane.PALETTE_LAYER);

        // Add main panel to the frame
        this.setContentPane(layeredPane);
        this.pack();
        this.setPreferredSize(new Dimension(scaledWidth, scaledHeight));
        this.setSize(1008, 720);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        cardLayout.show(mainPanel, "Menu");

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
    public void showMapPanel(JPanel mainPanel, CardLayout cardLayout) {
        cardLayout.show(mainPanel, "Map");
    }
    public void startGameWithMap(JPanel mainPanel, CardLayout cardLayout, String mapType) {
        gamePanel.setMapType(mapType);
        gamePanel.resetGame();
        cardLayout.show(mainPanel, "Game");
        gamePanel.requestFocusInWindow();
        gamePanel.startGameThread();
    }
    public void startGame() {
        gamePanel.resetGame();
        cardLayout.show(mainPanel, "Game");
        gamePanel.requestFocusInWindow();
        gamePanel.startGameThread();
    }
    public void showGameOver() {
        gameOverPanel.setVisible(true);
        gameOverPanel.repaint();
    }
    public void hideGameOver() {
        gameOverPanel.setVisible(false);
        gamePanel.requestFocusInWindow();
    }

    public void backToMenu() {
        gameOverPanel.setVisible(false);
        gamePanel.resetGame();
        cardLayout.show(mainPanel, "Menu");
    }
    public static void main(String[] args) {
        new Main();
    }
}

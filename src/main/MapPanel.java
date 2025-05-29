package main;

import Tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class MapPanel extends JPanel {
    private Image backgroundImage;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    private static final int BUTTON_WIDTH = 220;
    private static final int BUTTON_HEIGHT = 48;
    private static final Dimension BUTTON_SIZE = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);

    public MapPanel(JPanel mainPanel, CardLayout cardLayout, Main mainFrame) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        // Load background image
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/background/main_bg.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        JLabel title = new JLabel("Select Map Size", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 38));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(title);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Add Tiny Button
        JButton tinyButton = new JButton("Tiny");
        styleButton(tinyButton);
        tinyButton.addActionListener(e -> handleMapSelection("Tiny"));
        centerPanel.add(tinyButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Add Medium Button
        JButton mediumButton = new JButton("Medium");
        styleButton(mediumButton);
        mediumButton.addActionListener(e -> handleMapSelection("Medium"));
        centerPanel.add(mediumButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Add Big Button
        JButton bigButton = new JButton("Big");
        styleButton(bigButton);
        bigButton.addActionListener(e -> handleMapSelection("Big"));
        centerPanel.add(bigButton);

        // Back to Main Menu
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        JButton backButton = new JButton("Back");
        styleButton(backButton);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));
        centerPanel.add(backButton);

        add(centerPanel, gbc);
    }

    private void handleMapSelection(String mapType) {
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof Panel) {
                Panel gamePanel = (Panel) comp;
                gamePanel.setMapType(mapType);
                gamePanel.startGameThread();
            }
        }
        cardLayout.show(mainPanel, "Game");
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBackground(Color.DARK_GRAY);
        button.setOpaque(true);
        button.setMargin(new Insets(10, 20, 10, 20));
        button.setForeground(Color.WHITE);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.setMinimumSize(BUTTON_SIZE);
        button.setMaximumSize(BUTTON_SIZE);
        button.setPreferredSize(BUTTON_SIZE);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(80, 80, 80));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(50, 50, 50));
            }
        });
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
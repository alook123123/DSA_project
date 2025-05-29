package main;

import Tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class MenuPanel extends JPanel {
    private Image backgroundImage;
    private CardLayout cardLayout; // Biến CardLayout
    private JButton startButton; // Biến nút Start Game

    private static final int BUTTON_WIDTH = 220;
    private static final int BUTTON_HEIGHT = 48;
    private static final Dimension BUTTON_SIZE = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);

    public MenuPanel(JPanel mainPanel, CardLayout cardLayout, Main mainFrame) {
        this.cardLayout = cardLayout; // Gán CardLayout

        // Load background image
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/background/main_bg.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set layout for centering components
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        // Center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false); // Make the center panel transparent

        // Glue for vertical centering
        centerPanel.add(Box.createVerticalGlue());

        // Title label
        JLabel titleLabel = new JLabel("Last Day On Earth", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Impact", Font.BOLD, 60));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(titleLabel);

        // Start Game Button
        startButton = new JButton("Start Game"); // Khởi tạo nút
        styleButton(startButton);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Game"); // Hiển thị màn hình Game
                for (Component comp : mainPanel.getComponents()) {
                    if (comp instanceof Panel) {
                        Panel gamePanel = (Panel) comp;

                        gamePanel.setMapType("Tiny");

                        gamePanel.setBackgroundImage("/background/main3_bg.png");

                        gamePanel.startGameThread();
                    }
                }
            }
        });
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add spacing
        centerPanel.add(startButton);

        // Select Map Button
        JButton mapButton = new JButton("Select Map");
        styleButton(mapButton);
        mapButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Map");
            }
        });
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(mapButton);

        // Exit Game Button
        JButton exitButton = new JButton("Exit Game");
        styleButton(exitButton);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(e -> System.exit(0));
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing
        centerPanel.add(exitButton);
        centerPanel.add(Box.createVerticalGlue());

        // Add center panel to the main layout
        this.add(centerPanel, gbc);
    }

    // Paint the background image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Style the buttons to match the theme
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
}

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


    public MenuPanel(JPanel mainPanel, CardLayout cardLayout) {
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
        gbc.insets = new Insets(10, 10, 10, 10); // Spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; // Single column layout

        // Panel for buttons and title
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false); // Make the center panel transparent

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
                cardLayout.show(mainPanel, "Game");

                // Thay đổi hình nền và bắt đầu game
                for (Component comp : mainPanel.getComponents()) {
                    if (comp instanceof Panel) {
                        Panel gamePanel = (Panel) comp;
                        gamePanel.setBackgroundImage("/background/main3_bg.png"); // Đổi hình nền
                        gamePanel.startGameThread(); // Bắt đầu game
                    }
                }
            }
        });
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add spacing
        centerPanel.add(startButton);

        // Exit Game Button
        JButton exitButton = new JButton("Exit Game");
        styleButton(exitButton);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(e -> System.exit(0));
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing
        centerPanel.add(exitButton);

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
        button.setBackground(new Color(50, 50, 50));
        button.setOpaque(true);
        button.setMargin(new Insets(10, 20, 10, 20)); // Padding
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

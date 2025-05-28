package main;

import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel {
    public GameOverPanel(Main mainFrame, Runnable onReset) {
        setOpaque(false);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 0, 15, 0);

        JLabel title = new JLabel("Game Over", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 60));
        title.setForeground(Color.RED);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        JButton resetButton = new JButton("Reset");
        styleButton(resetButton);
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetButton.addActionListener(e -> onReset.run());
        buttonPanel.add(resetButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton backButton = new JButton("Back to Menu");
        styleButton(backButton);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> mainFrame.backToMenu());
        buttonPanel.add(backButton);

        add(title, gbc);
        add(buttonPanel, gbc);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 28));
        button.setFocusPainted(false);
        button.setBackground(new Color(50, 50, 50, 230));
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(220, 48));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Drawn overlay
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(0, 0, 0, 120));
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}
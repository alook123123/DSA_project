package main;

import Tile.TileManager;
import enity.Background.Heart;
import enity.Bullet;
import enity.Gun;
import enity.Monsters.Boss;
import enity.Monsters.Warrior;
import enity.Player;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Objects;

public class Panel extends JPanel implements Runnable {
    private Main mainFrame;
    // SCREEN SETTING
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 30;//20 for smaller view but BUG
    public final int maxScreenRow = 15;//14 for smaller view but BUG
    public final int boardWidth = maxScreenCol * tileSize;
    public final int boardHeight = maxScreenRow * tileSize;

    // Tiles
    public TileManager tileM = new TileManager(this);

    // viewpoint (Camera)
    public int viewpointX = 0;
    public int viewpointY = 0;

    // FPS
    final int FPS = 60;

    // System
    KeyHander keyHander = new KeyHander();
    Thread gameThread;

    // Check collision
    public CollisionChecker cChecker = new CollisionChecker(this);

    // Entity and object
    Player player = new Player(this, keyHander);
    Heart heart = new Heart(player);
    Gun gun = new Gun(player);
    Bullet bullet = new Bullet(gun);
    Warrior warrior = new Warrior(player);

    public static ArrayList<Bullet> bullets;
    public static ArrayList<Warrior> warriors;
    public static Boss activeBoss = null;
    private long startTime = 0;
    private boolean stopWarriorCreation = false;
    private boolean bossCreated = false;
    private boolean gameOver = false;
    private boolean gameWon = false;

    // Background image
    private Image backgroundImage;

    private Sound sound = new Sound();

    private boolean showBossMessage = false; // Trạng thái hiển thị thông báo
    private long bossMessageStartTime = 0;
    private boolean bossMessageShownOnce = false;

    public Panel(JPanel mainPanel, CardLayout cardLayout, Main mainFrame) {
        this.mainFrame = mainFrame;

        tileM = new TileManager(this);
        this.setPreferredSize(new Dimension(boardWidth, boardHeight));
        this.setBackground(Color.darkGray);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHander);
        this.setFocusable(true);
        // sound.playLoopedSound("game-music.wav");

        // Load the background image
        try {
            backgroundImage = ImageIO
                    .read(Objects.requireNonNull(getClass().getResourceAsStream("/background/main_bg.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNotify() {
        super.addNotify();
        this.requestFocusInWindow();
    }

    public void startGameThread() {
        if (gameThread == null || !gameThread.isAlive()) {
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    public void setMapType(String mapType) {
        if (tileM != null) {
            tileM.setMap(mapType);
        } else {
            System.err.println("tileM is null in setMapType!");
        }
    }

    @Override
    public void run() {
        bullets = new ArrayList<Bullet>();
        warriors = new ArrayList<Warrior>();

        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update(delta / FPS); // Pass deltaTime as seconds per frame
                repaint();
                delta--;
            }
        }
    }

    public void update(double deltaTime) {
        if (gameOver || gameWon) {
            if (keyHander.enter_Pressed) {
                resetGame();
            }
            return;
        }

        player.update();
        heart.update();

        if (player.spriteNum_14Frame == 2) {
            heart.started_action = false;
        }

        // When player is alive
        if (!player.action.equals("death")) {
            gun.update();
            bullet.update1();

            if (!stopWarriorCreation) {
                warrior.update1();
            }

            if (showBossMessage) {
                if (System.currentTimeMillis() - bossMessageStartTime >= 2000) { // Hiển thị trong 3 giây
                    showBossMessage = false; // Ẩn thông báo
                }
            }

            for (int i = 0; i < bullets.size(); i++) {
                Bullet bullet = bullets.get(i);
                // Kiểm tra va chạm với warriors
                for (Warrior warrior : warriors) {
                    warrior.checkCollisionWithBullet(bullet);
                }
                // Kiểm tra va chạm với boss
                if (activeBoss != null) {
                    activeBoss.checkCollisionWithBullet(bullet);
                }
                // Cập nhật và xóa đạn nếu cần
                if (bullet.isProcessed() || bullet.update2()) {
                    System.out.println("Bullet removed at index " + i + ", x=" + bullet.x + ", y=" + bullet.y); // Debug
                    bullets.remove(i);
                    i--;
                }
            }

            for (int i = 0; i < warriors.size(); i++) {
                Warrior warrior = warriors.get(i);
                if (warrior.update2(deltaTime)) { // Pass deltaTime to Warrior.update2
                    warriors.remove(i);
                    i--;
                }
            }

            if (startTime == 0) {
                startTime = System.currentTimeMillis();
            }

            if (System.currentTimeMillis() - startTime >= 15000) { // Boss: 200
//                if (!stopWarriorCreation) {
//                    showBossMessage = true; // Kích hoạt thông báo
//                    bossMessageStartTime = System.currentTimeMillis();
//
//                    //clearWarriors();
//                    //stopWarriorCreation = true;
//                    createBoss();
//                }
                if (!bossMessageShownOnce) {
                    showBossMessage = true;
                    bossMessageStartTime = System.currentTimeMillis();
                    bossMessageShownOnce = true; // prevents repeating the message
                }
                if (!bossCreated) {
                    createBoss();
                }
            }
        } else {
            sound.stopSound();
            sound.playSound("gameover_music.wav");
            // When player dies
            warriors.clear();
            activeBoss = null;
            gameOver = true; // Đánh dấu game over
            if (mainFrame != null)
                mainFrame.showGameOver();
        }
        if (activeBoss != null) {
            for (Bullet bullet : bullets) {
                activeBoss.checkCollisionWithBullet(bullet);
            }

            if (activeBoss.update2()) {
                activeBoss = null; // Xóa Boss khi hoạt ảnh chết hoàn tất.
                bossCreated = false; // Cho phép tạo Boss mới nếu cần.
                warriors.clear();
                gameWon = true; // Player wins
                sound.stopSound();
                sound.playSound("victorymale.wav"); // Play victory sound
            }
        }
        updateViewpoint();
    }

    public int getMapWidth() {
        return tileM.mapCol * tileSize;
    }

    public int getMapHeight() {
        return tileM.mapRow * tileSize;
    }

    public void updateViewpoint() {
        int mapWidth = getMapWidth();
        int mapHeight = getMapHeight();

        viewpointX = player.x + player.width / 2 - boardWidth / 2;
        viewpointY = player.y + player.height / 2 - boardHeight / 2;

        if (mapWidth <= boardWidth)
            viewpointX = 0;
        else
            viewpointX = Math.max(0, Math.min(viewpointX, mapWidth - boardWidth));

        if (mapHeight <= boardHeight)
            viewpointY = 0;
        else
            viewpointY = Math.max(0, Math.min(viewpointY, mapHeight - boardHeight));
    }

    private void clearWarriors() {
        warriors.clear();
    }

    private void createBoss() {
        if (!bossCreated) {
            activeBoss = new Boss(player);
            bossCreated = true;
        }
    }

    public void setBackgroundImage(String imagePath) {
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetGame() {
        player.reset();
        heart.reset();
        bullets.clear();
        warriors.clear();

        activeBoss = null;
        stopWarriorCreation = false;
        bossCreated = false;
        startTime = 0;
        gameOver = false;
        gameWon = false;
        // sound.playLoopedSound("game-music.wav");
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());

        int mapWidth = getMapWidth();
        int mapHeight = getMapHeight();
        int drawWidth = Math.min(boardWidth, mapWidth - viewpointX);
        if (mapWidth <= boardWidth)
            drawWidth = mapWidth;
        int drawHeight = Math.min(boardHeight, mapHeight - viewpointY);
        if (mapHeight <= boardHeight)
            drawHeight = mapHeight;

        // Draw the background image
        if (backgroundImage != null) {
            g2.drawImage(
                    backgroundImage,
                    0, 0, drawWidth, drawHeight,
                    viewpointX, viewpointY,
                    viewpointX + drawWidth, viewpointY + drawHeight,
                    null);
        }

        tileM.draw(g2, viewpointX, viewpointY, boardWidth, boardHeight);
        tileM.drawCollisionAreas(g2, viewpointX, viewpointY);

        // Draw other game elements
        player.draw(g2, viewpointX, viewpointY);
        heart.draw(g2, viewpointX, viewpointY);
        gun.draw(g2, viewpointX, viewpointY);

        if (bullets != null) {
            for (int i = 0; i < bullets.size(); i++) {
                bullets.get(i).draw(g2, viewpointX, viewpointY);
            }
        }

        if (warriors != null) {
            for (int i = 0; i < warriors.size(); i++) {
                warriors.get(i).draw(g2);
            }
        }

        if (activeBoss != null) {
            activeBoss.draw(g2, viewpointX, viewpointY);
        }

        if (showBossMessage) {
            g2.setColor(Color.LIGHT_GRAY);
            Font bossFont = new Font("Arial", Font.BOLD, 30);
            g2.setFont(bossFont);
            String bossMessage = "Boss is coming!";
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(bossMessage);
            int textHeight = fm.getHeight();
            int x = (getWidth() - textWidth) / 2;
            int y = (getHeight() - textHeight) / 2;
            g2.drawString(bossMessage, x, y);
        }

        if (gameWon) {
            g2.setColor(Color.YELLOW);
            Font victoryFont = new Font("Arial", Font.BOLD, 60);
            g2.setFont(victoryFont);
            String victoryMessage = "VICTORY";
            FontMetrics fmVictory = g2.getFontMetrics();
            int victoryWidth = fmVictory.stringWidth(victoryMessage);
            int victoryHeight = fmVictory.getHeight();
            int victoryX = (getWidth() - victoryWidth) / 2;
            int victoryY = (getHeight() - victoryHeight) / 2;
            g2.drawString(victoryMessage, victoryX, victoryY);

            Font restartFont = new Font("Arial", Font.ITALIC, 30);
            g2.setFont(restartFont);
            String restartMessage = "Enter to restart";
            FontMetrics fmRestart = g2.getFontMetrics();
            int restartWidth = fmRestart.stringWidth(restartMessage);
            int restartHeight = fmRestart.getHeight();
            int restartX = (getWidth() - restartWidth) / 2;
            int restartY = victoryY + victoryHeight + restartHeight;
            g2.drawString(restartMessage, restartX, restartY);
        }
        g2.dispose();
    }
}

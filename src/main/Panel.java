
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

    // SCREEN SETTING
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 30;
    public final int maxScreenRow = 15;
    public final int boardWidth = maxScreenCol * tileSize;
    public final int boardHeight = maxScreenRow * tileSize;

    //Tiles
    public TileManager tileM = new TileManager(this);

    // FPS
    final int FPS = 60;

    // System
    KeyHander keyHander = new KeyHander();
    Thread gameThread;

    //Check collision
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
    
    public TileManager tileManager;
    public Panel() {
        tileManager = new TileManager(this);
        this.setPreferredSize(new Dimension(boardWidth, boardHeight));
        this.setBackground(Color.darkGray);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHander);
        this.setFocusable(true);
        if (tileManager.mapTileNum == null) {
            System.out.println("mapTileNum is null in TileManager!");
        } else {
            System.out.println("mapTileNum initialized successfully.");
        }

//sound.playLoopedSound("game-music.wav");

        // Load the background image
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/background/main_bg.png")));
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

    @Override
    public void run() {
        bullets = new ArrayList<Bullet>();
        warriors = new ArrayList<Warrior>();

        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
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
                for (int j = 0; j < warriors.size(); j++) {
                    Warrior warrior = warriors.get(j);
                    warrior.checkCollisionWithBullet(bullet);
                }

                if (bullet.isProcessed() || bullet.update2()) {
                    bullets.remove(i);
                    i--;
                }
            }

            for (int i = 0; i < bullets.size(); i++) {
                if (bullets.get(i).update2()) {
                    bullets.remove(i);
                }
            }

            for (int i = 0; i < warriors.size(); i++) {
                Warrior warrior = warriors.get(i);
                if (warrior.update2()) {
                    warriors.remove(i);
                    i--;
                }
            }

            if (startTime == 0) {
                startTime = System.currentTimeMillis();
            }


            if (System.currentTimeMillis() - startTime >= 200) {
                if (!stopWarriorCreation) {
                    showBossMessage = true; // Kích hoạt thông báo
                    bossMessageStartTime = System.currentTimeMillis();
                    clearWarriors();
                    stopWarriorCreation = true;
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
        }
        if (activeBoss != null) {
            for (Bullet bullet : bullets) {
                activeBoss.checkCollisionWithBullet(bullet);
            }

            if (activeBoss.update2()) {
                activeBoss = null; // Xóa Boss khi hoạt ảnh chết hoàn tất.
                bossCreated = false; // Cho phép tạo Boss mới nếu cần.
                gameWon = true; // Player wins
                sound.stopSound();
                sound.playSound("victorymale.wav"); // Play victory sound
            }
        }
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
//sound.playLoopedSound("game-music.wav");
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;



        // Draw the background image
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, boardWidth, boardHeight, null);

        }


        tileM.draw(g2);
        tileM.drawCollisionAreas(g2);

        // Draw other game elements
        player.draw(g2);
        heart.draw(g2);
        gun.draw(g2);


        if (bullets != null) {
            for (int i = 0; i < bullets.size(); i++) {
                bullets.get(i).draw(g2);
            }
        }

        if (warriors != null) {
            for (int i = 0; i < warriors.size(); i++) {
                warriors.get(i).draw(g2);
            }
        }

        if (activeBoss != null) {
            activeBoss.draw(g2);
        }

        if (showBossMessage) {
            g2.setColor(Color.LIGHT_GRAY);
            g2.setFont(new Font("Arial", Font.BOLD, 30));
            g2.drawString("Boss is coming!", boardWidth / 2 - 100, boardHeight / 2);
        }

        if (gameOver) {
            g2.setColor(Color.LIGHT_GRAY);
            g2.setFont(new Font("Arial", Font.BOLD, 60));
            g2.drawString("GAME OVER", boardWidth / 2 - 180, boardHeight / 2);
            g2.setFont(new Font("Arial", Font.ITALIC, 30));
            g2.drawString("Enter to restart", boardWidth / 2 - 100, boardHeight / 2+70);
        }

        if (gameWon) {
            g2.setColor(Color.YELLOW);
            g2.setFont(new Font("Arial", Font.BOLD, 60));
            g2.drawString("VICTORY", boardWidth / 2 - 180, boardHeight / 2);
            g2.setFont(new Font("Arial", Font.ITALIC, 30));
            g2.drawString("Enter to restart", boardWidth / 2 - 145, boardHeight / 2+70);
        }

        g2.dispose();
    }
}


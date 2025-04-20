package enity;


import main.KeyHander;
import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Enity {
    public int heart = 10000;
    public int initialX, initialY, maxHealth;
    private int health;


    public Panel panel;
    public KeyHander keyHander;



    public Player(Panel panel, KeyHander keyHander) {
        this.panel = panel;
        this.keyHander = keyHander;

        // Initialize initial values for reset
        this.initialX = panel.boardWidth / 2 - panel.tileSize / 2;
        this.initialY = panel.boardHeight / 2 - panel.tileSize / 2;
        this.maxHealth = 4; // Default max health
        this.health = maxHealth;

        setDefaultValues_Player();
        getPlayerImage();
    }

    public void setDefaultValues_Player() {
        String direction = "standRight";
        x = initialX;
        y = initialY;
        worldX = x;
        worldY = y;

        width = (panel.tileSize * 3 / 2);
        height = panel.tileSize * 3 / 2;
        speedX = 4;
        speedY = 4;
        //speed = 4;
        direction_horizontal = "right";

        collisionArea =  new Rectangle();
        collisionArea.x = 20;
        collisionArea.y = 35;
        collisionArea.width = 32;
        collisionArea.height = 32;

        damageArea = new Rectangle(x, y, width, height);
        action = "standRight";
    }

    public void getPlayerImage() {
        try {
            standRight1 = ImageIO.read(getClass().getResourceAsStream("/player/Stand_Right-1.png.png"));
            standRight2 = ImageIO.read(getClass().getResourceAsStream("/player/Stand_Right-2.png.png"));

            standLeft1 = ImageIO.read(getClass().getResourceAsStream("/player/Stand_Left-1.png.png"));
            standLeft2 = ImageIO.read(getClass().getResourceAsStream("/player/Stand_Left-2.png.png"));

            moveRight1 = ImageIO.read(getClass().getResourceAsStream("/player/Right-1.png.png"));
            moveRight2 = ImageIO.read(getClass().getResourceAsStream("/player/Right-2.png.png"));
            moveRight3 = ImageIO.read(getClass().getResourceAsStream("/player/Right-3.png.png"));

            moveLeft1 = ImageIO.read(getClass().getResourceAsStream("/player/Left-1.png.png"));
            moveLeft2 = ImageIO.read(getClass().getResourceAsStream("/player/Left-2.png.png"));
            moveLeft3 = ImageIO.read(getClass().getResourceAsStream("/player/Left-3.png.png"));

            hurtRight1 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Right-1.png.png"));
            hurtRight2 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Right-2.png.png"));
            hurtRight3 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Right-3.png.png"));
            hurtRight4 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Right-4.png.png"));
            hurtRight5 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Right-5.png.png"));
            hurtRight6 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Right-6.png.png"));
            hurtRight7 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Right-7.png.png"));
            hurtRight8 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Right-8.png.png"));
            hurtRight9 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Right-9.png.png"));
            hurtRight10 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Right-10.png.png"));
            hurtRight11 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Right-11.png.png"));
            hurtRight12 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Right-12.png.png"));
            hurtRight13 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Right-13.png.png"));
            hurtRight14 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Right-14.png.png"));

            hurtLeft1 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Left-1.png.png"));
            hurtLeft2 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Left-2.png.png"));
            hurtLeft3 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Left-3.png.png"));
            hurtLeft4 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Left-4.png.png"));
            hurtLeft5 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Left-5.png.png"));
            hurtLeft6 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Left-6.png.png"));
            hurtLeft7 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Left-7.png.png"));
            hurtLeft8 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Left-8.png.png"));
            hurtLeft9 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Left-9.png.png"));
            hurtLeft10 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Left-10.png.png"));
            hurtLeft11 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Left-11.png.png"));
            hurtLeft12 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Left-12.png.png"));
            hurtLeft13 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Left-13.png.png"));
            hurtLeft14 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Left-14.png.png"));

            deathRight1 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Right-2.png.png"));
            deathRight2 = ImageIO.read(getClass().getResourceAsStream("/player/Death_Right-2.png.png"));
            deathRight3 = ImageIO.read(getClass().getResourceAsStream("/player/Death_Right-3.png.png"));
            deathRight4 = ImageIO.read(getClass().getResourceAsStream("/player/Death_Right-4.png.png"));
            deathRight5 = ImageIO.read(getClass().getResourceAsStream("/player/Death_Right-5.png.png"));

            deathLeft1 = ImageIO.read(getClass().getResourceAsStream("/player/Hurt_Left-2.png.png"));
            deathLeft2 = ImageIO.read(getClass().getResourceAsStream("/player/Death_Left-2.png.png"));
            deathLeft3 = ImageIO.read(getClass().getResourceAsStream("/player/Death_Left-3.png.png"));
            deathLeft4 = ImageIO.read(getClass().getResourceAsStream("/player/Death_Left-4.png.png"));
            deathLeft5 = ImageIO.read(getClass().getResourceAsStream("/player/Death_Left-5.png.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int count;
    public void update() {
        damageArea = new Rectangle(x,y,width,height);


        //check tile collision
        collisionOn = false;
        panel.cChecker.checkTile(this);
        //if collision is false, player can move


        if (keyHander.w_Pressed == true && action != "death" && !panel.cChecker.isCollisionUp()) {
            count = 1;
            if (y - speedY >= 0) {
                y -= speedY;
                worldY = y;
            }
            direction_vertical = "up";
            if (action != "hurt") {
                if (direction_horizontal == "left") {
                    action = "moveLeft";
                }
                if (direction_horizontal == "right") {
                    action = "moveRight";
                }
            }
        } else if (keyHander.w_Pressed == false && count == 1 && action != "death") {
            if (action != "hurt") {
                if (action == "moveLeft") {
                    action = "standLeft";
                }
                if (action == "moveRight") {
                    action = "standRight";
                }
            }
        }

        if (keyHander.s_Pressed == true && action != "death" && !panel.cChecker.isCollisionDown()) {
            count = 2;
            if (y + speedY + height <= panel.boardHeight) { // Kiểm tra giới hạn dưới
                y += speedY;
                worldY = y;
            }
            direction_vertical = "down";
            if (action != "hurt") {
                if (direction_horizontal == "left") {
                    action = "moveLeft";
                }
                if (direction_horizontal == "right") {
                    action = "moveRight";
                }
            }
        } else if (keyHander.s_Pressed == false && count == 2 && action != "death") {
            if (action != "hurt") {
                if (action == "moveLeft") {
                    action = "standLeft";
                }
                if (action == "moveRight") {
                    action = "standRight";
                }
            }
        }

        if (keyHander.a_Pressed == true && action != "death" && !panel.cChecker.isCollisionLeft()) {
            count = 3;
            if (x - speedX >= 0) { // Kiểm tra giới hạn bên trái
                x -= speedX;
                worldX = x;
            }
            direction_horizontal = "left";
            if (action != "hurt") {
                action = "moveLeft";
            }
        } else if (keyHander.a_Pressed == false && count == 3 && action != "death") {
            if (action != "hurt") {
                action = "standLeft";
            }
        }

        if (keyHander.d_Pressed == true && action != "death" && !panel.cChecker.isCollisionRight()) {
            count = 4;
            if (x + speedX + width <= panel.boardWidth) { // Kiểm tra giới hạn bên phải
                x += speedX;
                worldX = x;
            }
            direction_horizontal = "right";
            if (action != "hurt") {
                action = "moveRight";
            }
        } else if (keyHander.d_Pressed == false && count == 4 && action != "death") {
            if (action != "hurt") {
                action = "standRight";
            }
        }


        if (action == "hurt"){
            spriteCounter_14Frame++;
            if (spriteCounter_14Frame > 8) {
                if ( spriteNum_14Frame == 1){
                    heart = heart - 1;
                    spriteNum_14Frame = 2;
                }
                else if (spriteNum_14Frame == 2){
                    spriteNum_14Frame = 3;
                }
                else if (spriteNum_14Frame == 3){
                    spriteNum_14Frame = 4;
                }
                else if (spriteNum_14Frame == 4){
                    spriteNum_14Frame = 5;
                }
                else if (spriteNum_14Frame == 5){
                    spriteNum_14Frame = 6;
                }
                else if (spriteNum_14Frame == 6){
                    spriteNum_14Frame = 7;
                }
                else if (spriteNum_14Frame == 7){
                    spriteNum_14Frame = 8;
                }
                else if (spriteNum_14Frame == 8){
                    spriteNum_14Frame = 9;
                }
                else if (spriteNum_14Frame == 9) {
                    spriteNum_14Frame = 10;
                }
                else if (spriteNum_14Frame == 10){
                    spriteNum_14Frame = 11;
                }
                else if (spriteNum_14Frame == 11){
                    spriteNum_14Frame = 12;
                }
                else if (spriteNum_14Frame == 12){
                    spriteNum_14Frame = 13;
                }
                else if (spriteNum_14Frame == 13){
                    spriteNum_14Frame = 14;
                }
                else if (spriteNum_14Frame == 14){
                    spriteNum_14Frame = 1;
                }
                spriteCounter_14Frame = 0;
            }
        }

        if( action == "death" ){
            spriteCounter_5Frame ++;
            if (spriteCounter_5Frame > 15) {
                if ( spriteNum_5Frame == 1){
                    spriteNum_5Frame = 2;
                }
                else if (spriteNum_5Frame == 2){
                    spriteNum_5Frame = 3;
                }
                else if (spriteNum_5Frame == 3){
                    spriteNum_5Frame = 4;
                }
                else if (spriteNum_5Frame == 4){
                    spriteNum_5Frame = 5;
                }
                spriteCounter_5Frame = 0;
            }
        }

        if (action == "moveRight" || action == "moveLeft") {
            spriteCounter_3Frame++;
            if (spriteCounter_3Frame > 13) {
                if (spriteNum_3Frame == 1) {
                    spriteNum_3Frame = 2;
                } else if (spriteNum_3Frame == 2) {
                    spriteNum_3Frame = 3;
                } else if (spriteNum_3Frame == 3) {
                    spriteNum_3Frame = 1;
                }
                spriteCounter_3Frame = 0;
            }
        }

        if (action == "standLeft" || action == "standRight") {
            spriteCounter_2Frame++;
            if (spriteCounter_2Frame > 20) {
                if (spriteNum_2Frame == 1) {
                    spriteNum_2Frame = 2;
                } else if (spriteNum_2Frame == 2) {
                    spriteNum_2Frame = 1;
                }
                spriteCounter_2Frame = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
//        g2.setColor(Color.WHITE);
//        g2.fillRect(x, y, panel.tileSize, panel.tileSize);
        BufferedImage image = null;
        if (action == "moveRight") {
            if (spriteNum_3Frame == 1) {
                image = moveRight1;
            }
            if (spriteNum_3Frame == 2) {
                image = moveRight2;
            }
            if (spriteNum_3Frame == 3) {
                image = moveRight3;
            }
        } else if (action == "moveLeft") {
            if (spriteNum_3Frame == 1) {
                image = moveLeft1;
            }
            if (spriteNum_3Frame == 2) {
                image = moveLeft2;
            }
            if (spriteNum_3Frame == 3) {
                image = moveLeft3;
            }
        } else if (action == "standRight") {
            if (spriteNum_2Frame == 1) {
                image = standRight1;
            }
            if (spriteNum_2Frame == 2) {
                image = standRight2;
            }
        } else if (action == "standLeft") {
            if (spriteNum_2Frame == 1) {
                image = standLeft1;
            }
            if (spriteNum_2Frame == 2) {
                image = standLeft2;
            }

        }

        // when damaged

        if (action == "hurt"){
            if (direction_horizontal == "right"){
                if (spriteNum_14Frame == 1){
                    image = hurtRight1;
                }
                if (spriteNum_14Frame == 2){
                    image = hurtRight2;
                }
                if (spriteNum_14Frame == 3){
                    image = hurtRight3;
                }
                if (spriteNum_14Frame == 4){
                    image = hurtRight4;
                }
                if (spriteNum_14Frame == 5){
                    image = hurtRight5;
                }
                if (spriteNum_14Frame == 6){
                    image = hurtRight6;
                }
                if (spriteNum_14Frame == 7){
                    image = hurtRight7;
                }
                if (spriteNum_14Frame == 8){
                    image = hurtRight8;
                }
                if (spriteNum_14Frame == 9){
                    image = hurtRight9;
                }
                if (spriteNum_14Frame == 10){
                    image = hurtRight10;
                }
                if (spriteNum_14Frame == 11){
                    image = hurtRight11;
                }
                if (spriteNum_14Frame == 12){
                    image = hurtRight12;
                }
                if (spriteNum_14Frame == 13){
                    image = hurtRight13;
                }
                if (spriteNum_14Frame == 14){
                    image = hurtRight14;
                    spriteNum_14Frame = 1;
                    action = "standRight";
                }
            }
            if (direction_horizontal == "left"){
                if (spriteNum_14Frame == 1){
                    image = hurtLeft1;
                }
                if (spriteNum_14Frame == 2){
                    image = hurtLeft2;
                }
                if (spriteNum_14Frame == 3){
                    image = hurtLeft3;
                }
                if (spriteNum_14Frame == 4){
                    image = hurtLeft4;
                }
                if (spriteNum_14Frame == 5){
                    image = hurtLeft5;
                }
                if (spriteNum_14Frame == 6){
                    image = hurtLeft6;
                }
                if (spriteNum_14Frame == 7){
                    image = hurtLeft7;
                }
                if (spriteNum_14Frame == 8){
                    image = hurtLeft8;
                }
                if (spriteNum_14Frame == 9){
                    image = hurtLeft9;
                }
                if (spriteNum_14Frame == 10){
                    image = hurtLeft10;
                }
                if (spriteNum_14Frame == 11){
                    image = hurtLeft11;
                }
                if (spriteNum_14Frame == 12){
                    image = hurtLeft12;
                }
                if (spriteNum_14Frame == 13){
                    image = hurtLeft13;
                }
                if (spriteNum_14Frame == 14){
                    image = hurtLeft14;
                    spriteNum_14Frame = 1;
                    action = "standRight";
                }
            }
        }

        if (action == "death") {
            if (direction_horizontal == "right"){
                if (spriteNum_5Frame == 1){
                    image = deathRight1;
                }
                if (spriteNum_5Frame == 2){
                    image = deathRight2;
                }
                if (spriteNum_5Frame == 3){
                    image = deathRight3;
                }
                if (spriteNum_5Frame == 4){
                    image = deathRight4;
                }
                if (spriteNum_5Frame == 5){
                    image = deathRight5;
                }
            }
            if (direction_horizontal == "left") {
                if (spriteNum_5Frame == 1) {
                    image = deathLeft1;
                }
                if (spriteNum_5Frame == 2) {
                    image = deathLeft2;
                }
                if (spriteNum_5Frame == 3) {
                    image = deathLeft3;
                }
                if (spriteNum_5Frame == 4) {
                    image = deathLeft4;
                }
                if (spriteNum_5Frame == 5) {
                    image = deathLeft5;
                }
            }
        }

        g2.drawImage(image, x, y, width, height, null);

        //draw CollisionArea rectangle
        g2.setColor(Color.RED);
        g2.drawRect(panel.cChecker.getPlayerLeftWorldX(),
                panel.cChecker.getPlayerTopWorldY(),
                panel.cChecker.getPlayerRightWorldX()- panel.cChecker.getPlayerLeftWorldX(),
                panel.cChecker.getPlayerBottomWorldY() - panel.cChecker.getPlayerTopWorldY());

        //draw damageArea rectangle
//        g2.drawRect(damageArea.x, damageArea.y, damageArea.width, damageArea.height);


    }

    public void reset() {
        // Reset player's position, health, and action
        this.x = initialX;
        this.y = initialY;
        this.health = maxHealth;  // Reset health to max
        this.heart = maxHealth;   // Reset heart count if applicable
        this.action = "standRight"; // Reset action to idle/standing

        // Reset other player-specific attributes if needed
        this.direction_horizontal = "right";
        this.direction_vertical = "down";
    }

}
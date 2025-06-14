package enity.Background;

import enity.Enity;
import enity.Player;
import main.KeyHander;
import main.Panel;
import main.Viewpoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Heart extends Enity {
    public boolean started_action = false;
    //boolean action_done = false;

    Panel panel;
    KeyHander keyHander;
    Player player;

    public Heart(Player player) {
        this.panel = player.panel;
        this.keyHander = player.keyHander;
        this.player = player;

        setDefaltValues_Heart();
        getHeartImage();
    }

    public void setDefaltValues_Heart() {
        width = 100;
        height = 20;
        x = 50;
        y = 50;

        action = "4_Hearts";
    }

    public void getHeartImage() {
        try {
            Heart1 = ImageIO.read(getClass().getResourceAsStream("/heart/Hearth_Bar-1.png.png"));
            Heart2 = ImageIO.read(getClass().getResourceAsStream("/heart/Hearth_Bar-2.png.png"));
            Heart3 = ImageIO.read(getClass().getResourceAsStream("/heart/Hearth_Bar-3.png.png"));
            Heart4 = ImageIO.read(getClass().getResourceAsStream("/heart/Hearth_Bar-4.png.png"));
            Heart5 = ImageIO.read(getClass().getResourceAsStream("/heart/Hearth_Bar-5.png.png"));
            Heart6 = ImageIO.read(getClass().getResourceAsStream("/heart/Hearth_Bar-6.png.png"));
            Heart7 = ImageIO.read(getClass().getResourceAsStream("/heart/Hearth_Bar-7.png.png"));
            Heart8 = ImageIO.read(getClass().getResourceAsStream("/heart/Hearth_Bar-8.png.png"));
            Heart9 = ImageIO.read(getClass().getResourceAsStream("/heart/Hearth_Bar-9.png.png"));
            Heart10 = ImageIO.read(getClass().getResourceAsStream("/heart/Hearth_Bar-10.png.png"));
            Heart11 = ImageIO.read(getClass().getResourceAsStream("/heart/Hearth_Bar-11.png.png"));
            Heart12 = ImageIO.read(getClass().getResourceAsStream("/heart/Hearth_Bar-12.png.png"));
            Heart13 = ImageIO.read(getClass().getResourceAsStream("/heart/Hearth_Bar-13.png.png"));
            Heart14 = ImageIO.read(getClass().getResourceAsStream("/heart/Hearth_Bar-14.png.png"));
            Heart15 = ImageIO.read(getClass().getResourceAsStream("/heart/Hearth_Bar-15.png.png"));
            Heart16 = ImageIO.read(getClass().getResourceAsStream("/heart/Hearth_Bar-16.png.png"));
            Heart17 = ImageIO.read(getClass().getResourceAsStream("/heart/Hearth_Bar-17.png.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void reset() {
        // Reset heart action state and the started_action flag
        started_action = false;

        // Optionally reset spriteNum_4Frame and spriteCounter_4Frame if you want to restart the animation
        spriteNum_4Frame = 1;
        spriteCounter_4Frame = 0;

        // Reset the action based on the player's current heart value
        if (player.heart == 4) {
            action = "4_Hearts";
        } else if (player.heart == 3) {
            action = "3_Hearts";
        } else if (player.heart == 2) {
            action = "2_Hearts";
        } else if (player.heart == 1) {
            action = "1_Hearts";
        } else {
            action = "0_Hearts";
        }
    }

    public void update() {
        if (player.heart == 4) action = "4_Hearts";
        else if (player.heart == 3) action = "3_Hearts";
        else if (player.heart == 2) action = "2_Hearts";
        else if (player.heart == 1) action = "1_Hearts";
        else action = "0_Hearts";

        if (action != "4_Hearts" && started_action == true ) {
            spriteCounter_4Frame++;
            if (spriteCounter_4Frame > 30) {
                if (spriteNum_4Frame == 1) {
                    spriteNum_4Frame = 2;
                } else if (spriteNum_4Frame == 2) {
                    spriteNum_4Frame = 3;
                } else if (spriteNum_4Frame == 3) {
                    spriteNum_4Frame = 4;
                }
                spriteCounter_4Frame = 0;
            }
        }
    }

    public void draw (Graphics2D g2, int viewpointX, int viewpointY){
        BufferedImage image = null;
        if (action.equals("4_Hearts")) image = Heart1;
        else if (action.equals("3_Hearts")) image = Heart2;
        else if (action.equals("2_Hearts")) image = Heart6;
        else if (action.equals("1_Hearts")) image = Heart10;
        else if (action.equals("0_Hearts")) image = Heart17;

        g2.drawImage(image, 20, 20, width, height, null);    
    }
}
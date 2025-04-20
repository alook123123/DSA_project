package main;
// 1 mean can move
// 0 mean can not move

import enity.Enity;
import enity.Monsters.Warrior;
import enity.Player;

import java.awt.*;

public class CollisionChecker {

    public  Panel panel;

    private int playerLeftWorldX ;
    private int playerRightWorldX ;
    private int playerTopWorldY;
    private int playerBottomWorldY ;

    private int playerLeftCol ;
    private int playerRightCol ;
    private int playerTopRow ;
    private int playerBottRow ;

    private boolean collisionTop;
    private boolean collisionBottom;
    private boolean collisionRight;
    private boolean collisionLeft;

    public CollisionChecker(Panel panel){
        this.panel = panel;
    }

    public int getPlayerLeftWorldX() {
        return playerLeftWorldX;
    }

    public int getPlayerRightWorldX() {
        return playerRightWorldX;
    }

    public int getPlayerTopWorldY() {
        return playerTopWorldY;
    }

    public int getPlayerBottomWorldY() {
        return playerBottomWorldY;
    }

    public int getPlayerLeftCol() {
        return playerLeftCol;
    }

    public int getPlayerRightCol() {
        return playerRightCol;
    }

    public int getPlayerTopRow() {
        return playerTopRow;
    }

    public int getPlayerBottRow() {
        return playerBottRow;
    }

    public void checkTile(Player player) {
        collisionTop = false;
        collisionBottom = false;
        collisionRight  = false;
        collisionLeft = false;

        playerLeftWorldX = player.worldX + player.collisionArea.x;
        playerRightWorldX = player.worldX + player.collisionArea.x + player.collisionArea.width;
        playerTopWorldY = player.worldY + player.collisionArea.y;
        playerBottomWorldY = player.worldY + player.collisionArea.y + player.collisionArea.height;

        playerLeftCol = playerLeftWorldX / panel.tileSize;
        playerRightCol = playerRightWorldX / panel.tileSize;
        playerTopRow = playerTopWorldY / panel.tileSize;
        playerBottRow = playerBottomWorldY / panel.tileSize;

        int tileNum1, tileNum2;

        if (player.direction_vertical != null) {
            switch (player.direction_vertical) {
                case "up":
                    playerTopRow = (playerTopWorldY - player.speed)/ panel.tileSize;
                    tileNum1 = panel.tileM.mapTileNum[playerLeftCol][playerTopRow];
                    tileNum2 = panel.tileM.mapTileNum[playerRightCol][playerTopRow];
                    //System.out.println(panel.tileM.tile[tileNum1].collision);
                    if (panel.tileM.tile[tileNum1].collision == true || panel.tileM.tile[tileNum2].collision == true)
                    {
                        player.collisionOn = true;
                        collisionTop = true;
                    }
                    break;
                case "down":
                    playerBottRow = (playerBottomWorldY + player.speed)/ panel.tileSize;
                    tileNum1 = panel.tileM.mapTileNum[playerLeftCol][playerBottRow];
                    tileNum2 = panel.tileM.mapTileNum[playerRightCol][playerBottRow];

                    if (panel.tileM.tile[tileNum1].collision == true || panel.tileM.tile[tileNum2].collision == true)
                    {
                        player.collisionOn = true;
                        collisionBottom = true;
                    }
                    break;
            }

        }
        if (player.direction_horizontal != null) {
            switch (player.direction_horizontal)
            {
                case "left":
                    playerLeftCol = (playerLeftWorldX - player.speed)/ panel.tileSize;
                    tileNum1 = panel.tileM.mapTileNum[playerLeftCol][playerTopRow];
                    tileNum2 = panel.tileM.mapTileNum[playerLeftCol][playerBottRow];

                    if (panel.tileM.tile[tileNum1].collision == true || panel.tileM.tile[tileNum2].collision == true)
                    {
                        player.collisionOn = true;
                        collisionLeft = true;
                    }
                    break;
                case "right":
                    playerRightCol = (playerRightWorldX + player.speed)/ panel.tileSize;
                    tileNum1 = panel.tileM.mapTileNum[playerRightCol][playerTopRow];
                    tileNum2 = panel.tileM.mapTileNum[playerRightCol][playerBottRow];

                    if (panel.tileM.tile[tileNum1].collision == true || panel.tileM.tile[tileNum2].collision == true)
                    {
                        player.collisionOn = true;
                        collisionRight = true;
                    }
                    break;

            }
        }

    }

    public boolean isCollisionUp() { return collisionTop; }
    public boolean isCollisionDown() { return collisionBottom; }
    public boolean isCollisionLeft() { return collisionLeft; }
    public boolean isCollisionRight() { return collisionRight; }




    public void checkTileWarrior(Warrior warrior, double speedX, double speedY) {
        warrior.collisionOn = false;

        int futureX = (int)(warrior.worldX + speedX + warrior.collisionArea.x);
        int futureY = (int)(warrior.worldY + speedY + warrior.collisionArea.y);

        int leftCol = futureX / panel.tileSize;
        int rightCol = (futureX + warrior.collisionArea.width) / panel.tileSize;
        int topRow = futureY / panel.tileSize;
        int bottomRow = (futureY + warrior.collisionArea.height) / panel.tileSize;

        try {
            int tile1 = panel.tileM.mapTileNum[leftCol][topRow];
            int tile2 = panel.tileM.mapTileNum[rightCol][topRow];
            int tile3 = panel.tileM.mapTileNum[leftCol][bottomRow];
            int tile4 = panel.tileM.mapTileNum[rightCol][bottomRow];

            if (panel.tileM.tile[tile1].collision || panel.tileM.tile[tile2].collision ||
                    panel.tileM.tile[tile3].collision || panel.tileM.tile[tile4].collision) {
                warrior.collisionOn = true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            warrior.collisionOn = true; // treat out of bounds as solid
        }
    }



}

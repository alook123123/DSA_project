package enity.Monsters;

import Tile.TileManager;
import enity.Enity;
import enity.Player;
import main.KeyHander;
import main.Panel;
import enity.Bullet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;


class PathNode {
    int x, y;
    PathNode parent;



    public PathNode(int x, int y, PathNode parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }
}

public class Warrior extends Enity {
    int heart = 2;
    public int speed = 10;
    int distance_attack = 70;
    private boolean isDead = false;
    private TileManager tileM;
    List<PathNode> path_bsf;

    private long spamMonsterTimer;

    Panel panel;
    KeyHander keyHander;
    Player player;

    public Warrior(Player player) {
        this.panel = player.panel;
        this.keyHander = player.keyHander;
        this.player = player;
        this.tileM = panel.tileM;//     Gán tileM từ panel

        setDefaltValues_Warrior();
        getWarriorImage();

        direction_horizontal = "right";
        spamMonsterTimer = System.nanoTime();
    }

    public void setDefaltValues_Warrior(){
        width = panel.tileSize*2;
        height = panel.tileSize*2;

        //x, y is in pixel
        Random rand = new Random();
        int randomPosition = rand.nextInt(4) + 1;
        if (randomPosition == 1){
            y = 0 - height;
            x = rand.nextInt(panel.boardWidth+1);
        }
        else if (randomPosition == 2){
            y = panel.boardHeight - height - 1; // ensure it's inside the playable field
            x = rand.nextInt(panel.boardWidth - width);
        }
        else if (randomPosition == 3){
            x = panel.boardWidth;
            y = rand.nextInt(panel.boardHeight+1);
        }
        else if (randomPosition == 4){
            x = 0 - width;
            y = rand.nextInt(panel.boardHeight+1);
        }

        attackArea = new Rectangle(x,y,width,height);
        damageArea = new Rectangle(x,y,width,height);


        collisionArea =  new Rectangle();
        collisionArea.x = 30;//30
        collisionArea.y = 50;//50
        collisionArea.width = 32;
        collisionArea.height = 32;
    }

    public void getWarriorImage(){
        try {
            warriorMoveRight1 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/move/Warrior_Move_Right-1.png.png"));
            warriorMoveRight2 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/move/Warrior_Move_Right-2.png.png"));
            warriorMoveRight3 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/move/Warrior_Move_Right-3.png.png"));
            warriorMoveRight4 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/move/Warrior_Move_Right-4.png.png"));
            warriorMoveRight5 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/move/Warrior_Move_Right-5.png.png"));

            warriorMoveLeft1 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/move/Warrior_Move_Left-1.png.png"));
            warriorMoveLeft2 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/move/Warrior_Move_Left-2.png.png"));
            warriorMoveLeft3 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/move/Warrior_Move_Left-3.png.png"));
            warriorMoveLeft4 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/move/Warrior_Move_Left-4.png.png"));
            warriorMoveLeft5 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/move/Warrior_Move_Left-5.png.png"));

            warriorAttack1Right1 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack1/Warrior_Attack1_Right-1.png.png"));
            warriorAttack1Right2 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack1/Warrior_Attack1_Right-2.png.png"));
            warriorAttack1Right3 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack1/Warrior_Attack1_Right-3.png.png"));
            warriorAttack1Right4 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack1/Warrior_Attack1_Right-4.png.png"));
            warriorAttack1Right5 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack1/Warrior_Attack1_Right-5.png.png"));
            warriorAttack1Right6 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack1/Warrior_Attack1_Right-6.png.png"));
            warriorAttack1Right7 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack1/Warrior_Attack1_Right-7.png.png"));
            warriorAttack1Right8 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack1/Warrior_Attack1_Right-8.png.png"));

            warriorAttack1Left1 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack1/Warrior_Attack1_Left-1.png.png"));
            warriorAttack1Left2 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack1/Warrior_Attack1_Left-2.png.png"));
            warriorAttack1Left3 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack1/Warrior_Attack1_Left-3.png.png"));
            warriorAttack1Left4 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack1/Warrior_Attack1_Left-4.png.png"));
            warriorAttack1Left5 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack1/Warrior_Attack1_Left-5.png.png"));
            warriorAttack1Left6 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack1/Warrior_Attack1_Left-6.png.png"));
            warriorAttack1Left7 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack1/Warrior_Attack1_Left-7.png.png"));
            warriorAttack1Left8 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack1/Warrior_Attack1_Left-8.png.png"));

            warriorAttack2Right1 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack2/Warrior_Attack2_Right-1.png.png"));
            warriorAttack2Right2 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack2/Warrior_Attack2_Right-2.png.png"));
            warriorAttack2Right3 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack2/Warrior_Attack2_Right-3.png.png"));
            warriorAttack2Right4 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack2/Warrior_Attack2_Right-4.png.png"));
            warriorAttack2Right5 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack2/Warrior_Attack2_Right-5.png.png"));
            warriorAttack2Right6 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack2/Warrior_Attack2_Right-6.png.png"));

            warriorAttack2Left1 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack2/Warrior_Attack2_Left-1.png.png"));
            warriorAttack2Left2 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack2/Warrior_Attack2_Left-2.png.png"));
            warriorAttack2Left3 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack2/Warrior_Attack2_Left-3.png.png"));
            warriorAttack2Left4 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack2/Warrior_Attack2_Left-4.png.png"));
            warriorAttack2Left5 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack2/Warrior_Attack2_Left-5.png.png"));
            warriorAttack2Left6 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/attack2/Warrior_Attack2_Left-6.png.png"));

            warriorHurtRight = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/hurt/Warrior_Hurt_Right-1.png.png"));
            warriorHurtLeft = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/hurt/Warrior_Hurt_Left-1.png.png"));

            warriorDeathRight1 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/death/Warrior_Death_Right-1.png.png"));
            warriorDeathRight2 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/death/Warrior_Death_Right-2.png.png"));
            warriorDeathRight3 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/death/Warrior_Death_Right-3.png.png"));
            warriorDeathRight4 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/death/Warrior_Death_Right-4.png.png"));
            warriorDeathRight5 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/death/Warrior_Death_Right-5.png.png"));
            warriorDeathRight6 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/death/Warrior_Death_Right-6.png.png"));
            warriorDeathRight7 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/death/Warrior_Death_Right-7.png.png"));
            warriorDeathRight8 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/death/Warrior_Death_Right-8.png.png"));
            warriorDeathRight9 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/death/Warrior_Death_Right-9.png.png"));
            warriorDeathRight10 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/death/Warrior_Death_Right-10.png.png"));
            warriorDeathRight11 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/death/Warrior_Death_Right-11.png.png"));
            warriorDeathRight12 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/death/Warrior_Death_Right-12.png.png"));
            warriorDeathRight13 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/death/Warrior_Death_Right-13.png.png"));

            warriorDeathLeft1 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/death/Warrior_Death_Left-1.png.png"));
            warriorDeathLeft2 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/death/Warrior_Death_Left-2.png.png"));
            warriorDeathLeft3 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/death/Warrior_Death_Left-3.png.png"));
            warriorDeathLeft4 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/death/Warrior_Death_Left-4.png.png"));
            warriorDeathLeft5 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/death/Warrior_Death_Left-5.png.png"));
            warriorDeathLeft6 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/death/Warrior_Death_Left-6.png.png"));
            warriorDeathLeft7 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/death/Warrior_Death_Left-7.png.png"));
            warriorDeathLeft8 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/death/Warrior_Death_Left-8.png.png"));
            warriorDeathLeft9 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/death/Warrior_Death_Left-9.png.png"));
            warriorDeathLeft10 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/death/Warrior_Death_Left-10.png.png"));
            warriorDeathLeft11 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/death/Warrior_Death_Left-11.png.png"));
            warriorDeathLeft12 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/death/Warrior_Death_Left-12.png.png"));
            warriorDeathLeft13 = ImageIO.read(getClass().getResourceAsStream("/monsters/warrior/death/Warrior_Death_Left-13.png.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void checkCollisionWithBullet(Bullet bullet) {
        if (bullet.isProcessed()) return;

        Rectangle attackArea = bullet.getAttackArea();
        if (damageArea.intersects(attackArea)) {
            takeDamage(bullet.getDamage());
            bullet.setProcessed(true);
        }
    }


    public void takeDamage(int damage) {
        if (isDead) return;
        heart -= damage;
        action="hurt";

        if (heart <= 0) {
            isDead = true;
        }
    }


    public void update1(){
        long currentTime = System.nanoTime();
        if (currentTime - spamMonsterTimer > 1500000000L) { //quái tạo ra mỗi 15s
            Panel.warriors.add(new Warrior(this.player));
            spamMonsterTimer = currentTime;
        }
    }

/// //////////////////////////////////////////////////////////
    public List<PathNode> bfs(int startX, int startY, int goalX, int goalY, boolean[][] walkable) {
        int cols = walkable.length;       // Số cột     //walkableMap[col][row] = [x][y]
        int rows = walkable[0].length;    // Số hàng

        if (startX < 0 || startY < 0 || startX >= cols || startY >= rows ||
                goalX < 0 || goalY < 0 || goalX >= cols || goalY >= rows) {
            return null;
        }

        boolean[][] visited = new boolean[cols][rows];
        Queue<PathNode> queue = new LinkedList<>();
        queue.add(new PathNode(startX, startY, null));
        visited[startX][startY] = true;

        int[] dx = {-1, 1, 0, 0}; // trái, phải
        int[] dy = {0, 0, -1, 1}; // lên, xuống

        while (!queue.isEmpty()) {
            PathNode current = queue.poll();

            if (current.x == goalX && current.y == goalY) {
                List<PathNode> path = new ArrayList<>();
                for (PathNode p = current; p != null; p = p.parent) {
                    path.add(p);
                }
                Collections.reverse(path);
                return path;
            }

            for (int i = 0; i < 4; i++) {
                int nx = current.x + dx[i];
                int ny = current.y + dy[i];

                if (nx >= 0 && ny >= 0 && nx < cols && ny < rows && walkable[nx][ny] && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    queue.add(new PathNode(nx, ny, current));
                }
            }
        }

        return null; // Không tìm thấy đường đi
    }

    /// ////////////// debug only
//    public void printWalkableMap() {
//        boolean[][] walkableMap = tileM.getWalkableMap();
//        int cols = walkableMap.length;
//        int rows = walkableMap[0].length;
//
//        for (int row = 0; row < rows; row++) {
//            for (int col = 0; col < cols; col++) {
//                if (walkableMap[col][row]) {
//                    System.out.print("."); // Có thể đi
//                } else {
//                    System.out.print("#"); // Không thể đi
//                }
//            }
//            System.out.println(); // Xuống dòng sau mỗi hàng
//        }
//    }




    public boolean update2(){
        double distance_to_playerX = player.x-x;
        double distance_to_playerY = player.y-y;

        double distance_to_player = Math.sqrt(Math.pow(distance_to_playerX,2) + Math.pow(distance_to_playerY,2));

        if (distance_to_player == 0){
            distance_to_player = 1;
        }
        double speedX = (speed/distance_to_player)*distance_to_playerX;
        double speedY = (speed/distance_to_player)*distance_to_playerY;

        if (isDead) {
            return true;
        }

        collisionOn = false;
        panel.cChecker.checkTileWarrior(this,speedX,speedY);

        if (distance_to_playerX >= 0 && distance_to_player > distance_attack) {
            if (!(action == "hurt" || action == "death")) {
                action = "moveRight";
            }
            direction_horizontal = "right";
        } else if (distance_to_playerX < 0 && distance_to_player > distance_attack) {
            if (!(action == "hurt" || action == "death")) {
                action = "moveLeft";
            }
            direction_horizontal = "left";
        } else if (distance_to_playerX >= 0 && distance_to_player <= distance_attack) {
            if (!(action == "hurt" || action == "death")) {
                action = "attack1Right";
            }
            direction_horizontal = "right";
        } else if (distance_to_playerX < 0 && distance_to_player <= distance_attack) {
            if (!(action == "hurt" || action == "death")) {
                action = "attack1Left";
            }
            direction_horizontal = "left";
        }

        attackArea = new Rectangle(x,y,width,height);
        damageArea = new Rectangle(x,y,width,height);


        if((action == "attack1Right" || action == "attack1Left") && (action != "death") && (player.damageArea.intersects(this.attackArea))){
            if(player.heart <= 0){
                player.action = "death";
            }
            else {
                player.action = "hurt";
            }
        }

        if (!(action == "hurt" || action == "death")) {
            if (action == "moveRight" || action == "moveLeft") {
                spriteCounter_5Frame++;
                if (spriteCounter_5Frame > 12) {
                    if (spriteNum_5Frame == 1) {
                        spriteNum_5Frame = 2;
                    } else if (spriteNum_5Frame == 2) {
                        spriteNum_5Frame = 3;
                    } else if (spriteNum_5Frame == 3) {
                        spriteNum_5Frame = 4;
                    } else if (spriteNum_5Frame == 4) {
                        spriteNum_5Frame = 5;
                    } else if (spriteNum_5Frame == 5) {
                        spriteNum_5Frame = 1;
                    }
                    spriteCounter_5Frame = 0;
                    /// //////////////////////////////////////////////////// update 1
                    if(!collisionOn) {
                        x += speedX;
                        worldX = x;
                        y += speedY;
                        worldY = y;
                    }
                    else {
                        // Get tile coordinates
                        int startTileX = x / panel.tileSize;
                        int startTileY = y / panel.tileSize;
                        int goalTileX = player.x / panel.tileSize;
                        int goalTileY = player.y / panel.tileSize;

                        // Kiểm tra tọa độ tile trước khi tiếp tục BFS
                        if (startTileX < 0 || startTileY < 0 ||
                                startTileX >= panel.maxScreenCol || startTileY >= panel.maxScreenRow ||
                                goalTileX < 0 || goalTileY < 0 ||
                                goalTileX >= panel.maxScreenCol || goalTileY >= panel.maxScreenRow) {
                            return true; // Bỏ qua nếu nằm ngoài bản đồ
                        }

                        // Prepare the walkable map
                        boolean[][] walkable = tileM.getWalkableMap(); // You need to implement this


                        List<PathNode> path = bfs(startTileX, startTileY, goalTileX, goalTileY, walkable);
                        path_bsf = path;
                        if (path != null && path.size() > 1) {
                            PathNode nextStep = path.get(1); // step after current

                            double targetX = nextStep.x * panel.tileSize + panel.tileSize / 4.0;
                            double targetY = nextStep.y * panel.tileSize + panel.tileSize /1.5;

                            double deltaX = targetX - x;
                            double deltaY = targetY - y;
                            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                            if (distance == 0) distance = 1;

                            /// /////////////////////////// update
                            x += (speed / distance) * deltaX;
                            y += (speed / distance) * deltaY;

                            worldX = x;
                            worldY = y;
                            //printWalkableMap();



//                            panel.cChecker.checkTileWarrior(this, speedX, speedY);
//                            if (!collisionOn) {
//                                x += speedX;
//                                worldX = x;
//                                y += speedY;
//                                worldY = y;
//                            }
                        }//////////////////////////////////////////////////// end
                    }

                }

            }

            if (action == "attack1Right" || action == "attack1Left") {
                spriteCounter_8Frame++;
                if (spriteCounter_8Frame > 12) {
                    if (spriteNum_8Frame == 1) {
                        spriteNum_8Frame = 2;
                    } else if (spriteNum_8Frame == 2) {
                        spriteNum_8Frame = 3;
                    } else if (spriteNum_8Frame == 3) {
                        spriteNum_8Frame = 4;
                    } else if (spriteNum_8Frame == 4) {
                        spriteNum_8Frame = 5;
                    } else if (spriteNum_8Frame == 5) {
                        spriteNum_8Frame = 6;
                    } else if (spriteNum_8Frame == 6) {
                        spriteNum_8Frame = 7;
                    } else if (spriteNum_8Frame == 7) {
                        spriteNum_8Frame = 8;
                    } else if (spriteNum_8Frame == 8) {
                        spriteNum_8Frame = 1;
                    }
                    spriteCounter_8Frame = 0;
                    ////////////////////////////////////////// update 2
                    if (!collisionOn) {
                        x += speedX;
                        worldX = x;
                        y += speedY;
                        worldY =y;
                    }
                    /// ///////////////////
                    else  {
                        // Get tile coordinates
                        int startTileX = x / panel.tileSize;
                        int startTileY = y / panel.tileSize;
                        int goalTileX = player.x / panel.tileSize;
                        int goalTileY = player.y / panel.tileSize;

                        // Kiểm tra tọa độ tile trước khi tiếp tục BFS
                        if (startTileX < 0 || startTileY < 0 ||
                                startTileX >= panel.maxScreenCol || startTileY >= panel.maxScreenRow ||
                                goalTileX < 0 || goalTileY < 0 ||
                                goalTileX >= panel.maxScreenCol || goalTileY >= panel.maxScreenRow) {
                            return true; // Bỏ qua nếu nằm ngoài bản đồ
                        }

                        // Prepare the walkable map
                        boolean[][] walkable = tileM.getWalkableMap(); // You need to implement this


                        List<PathNode> path = bfs(startTileX, startTileY, goalTileX, goalTileY, walkable);

                        if (path != null && path.size() > 1) {
                            PathNode nextStep = path.get(1); // step after current

                            double targetX = nextStep.x * panel.tileSize + panel.tileSize / 4.0;
                            double targetY = nextStep.y * panel.tileSize + panel.tileSize / 1.5;

                            double deltaX = targetX - x;
                            double deltaY = targetY - y;
                            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                            if (distance == 0) distance = 1;

                            /// /////////////////////////// update
                            x += (speed / distance) * deltaX;
                            y += (speed / distance) * deltaY;

                            worldX = x;
                            worldY = y;
                        }
                        }
                    /// //////////////////

                }
            }
        }
        if (action == "hurt"){
            spriteCounter_3Frame++;
            if (spriteCounter_3Frame > 8) {
                if (spriteNum_3Frame == 1) {
                    spriteNum_3Frame = 2;
                }
                else if (spriteNum_3Frame == 2) {
                    spriteNum_3Frame = 3;
                }
                else if (spriteNum_3Frame == 3) {
                    spriteNum_3Frame = 1;
                    action = "moveRight";
                }
                spriteCounter_3Frame = 0;
            }
        }

        if( action == "death" ){
            spriteCounter_13Frame++;
            if (spriteCounter_13Frame > 15) {
                if (spriteNum_13Frame == 1) {
                    spriteNum_13Frame = 2;
                } else if (spriteNum_13Frame == 2) {
                    spriteNum_13Frame = 3;
                } else if (spriteNum_13Frame == 3) {
                    spriteNum_13Frame = 4;
                } else if (spriteNum_13Frame == 4) {
                    spriteNum_13Frame = 5;
                } else if (spriteNum_13Frame == 5) {
                    spriteNum_13Frame = 6;
                } else if (spriteNum_13Frame == 6) {
                    spriteNum_13Frame = 7;
                } else if (spriteNum_13Frame == 7) {
                    spriteNum_13Frame = 8;
                } else if (spriteNum_13Frame == 8) {
                    spriteNum_13Frame = 9;
                } else if (spriteNum_13Frame == 9) {
                    spriteNum_13Frame = 10;
                } else if (spriteNum_13Frame == 10) {
                    spriteNum_13Frame = 11;
                } else if (spriteNum_13Frame == 11) {
                    spriteNum_13Frame = 12;
                } else if (spriteNum_13Frame == 12) {
                    spriteNum_14Frame = 13;
                } else if (spriteNum_13Frame == 13) {
                    spriteNum_13Frame = 1;
                }
                spriteCounter_13Frame = 0;
            }
        }

        return false;
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;

        if (action == "moveRight") {
            if (spriteNum_5Frame == 1){
                image = warriorMoveRight1;
            }
            if (spriteNum_5Frame == 2){
                image = warriorMoveRight2;
            }
            if (spriteNum_5Frame == 3){
                image = warriorMoveRight3;
            }
            if (spriteNum_5Frame == 4){
                image = warriorMoveRight4;
            }
            if (spriteNum_5Frame == 5){
                image = warriorMoveRight5;
            }
        }

        if (action == "moveLeft") {
            if (spriteNum_5Frame == 1){
                image = warriorMoveLeft1;
            }
            if (spriteNum_5Frame == 2){
                image = warriorMoveLeft2;
            }
            if (spriteNum_5Frame == 3){
                image = warriorMoveLeft3;
            }
            if (spriteNum_5Frame == 4){
                image = warriorMoveLeft4;
            }
            if (spriteNum_5Frame == 5){
                image = warriorMoveLeft5;
            }
        }

        if (action == "attack1Right") {
            if (spriteNum_8Frame == 1){
                image = warriorAttack1Right1;
            }
            if (spriteNum_8Frame == 2){
                image = warriorAttack1Right2;
            }
            if (spriteNum_8Frame == 3){
                image = warriorAttack1Right3;
            }
            if (spriteNum_8Frame == 4){
                image = warriorAttack1Right4;
            }
            if (spriteNum_8Frame == 5){
                image = warriorAttack1Right5;
            }
            if (spriteNum_8Frame == 6){
                image = warriorAttack1Right6;
            }
            if (spriteNum_8Frame == 7){
                image = warriorAttack1Right7;
            }
            if (spriteNum_8Frame == 8){
                image = warriorAttack1Right8;
            }
        }

        if (action == "attack1Left") {
            if (spriteNum_8Frame == 1){
                image = warriorAttack1Left1;
            }
            if (spriteNum_8Frame == 2){
                image = warriorAttack1Left2;
            }
            if (spriteNum_8Frame == 3){
                image = warriorAttack1Left3;
            }
            if (spriteNum_8Frame == 4){
                image = warriorAttack1Left4;
            }
            if (spriteNum_8Frame == 5){
                image = warriorAttack1Left5;
            }
            if (spriteNum_8Frame == 6){
                image = warriorAttack1Left6;
            }
            if (spriteNum_8Frame == 7){
                image = warriorAttack1Left7;
            }
            if (spriteNum_8Frame == 8){
                image = warriorAttack1Left8;
            }
        }

        if (action == "hurt"){
            if (direction_horizontal == "right") {
                if (spriteNum_3Frame == 1) {
                    image = warriorHurtRight;
                }
                if (spriteNum_3Frame == 2) {
                    image = warriorHurtRight;
                }
                if (spriteNum_3Frame == 3) {
                    image = warriorHurtRight;
                }
            }
            if (direction_horizontal == "left") {
                if (spriteNum_3Frame == 1) {
                    image = warriorHurtLeft;
                }
                if (spriteNum_3Frame == 2) {
                    image = warriorHurtLeft;
                }
                if (spriteNum_3Frame == 3) {
                    image = warriorHurtLeft;
                }
            }
        }

        if (action == "death") {
            if (direction_horizontal == "right") {
                if (spriteNum_13Frame == 1) {
                    image = warriorDeathRight1;
                }
                if (spriteNum_13Frame == 2) {
                    image = warriorDeathRight2;
                }
                if (spriteNum_13Frame == 3) {
                    image = warriorDeathRight3;
                }
                if (spriteNum_13Frame == 4) {
                    image = warriorDeathRight4;
                }
                if (spriteNum_13Frame == 5) {
                    image = warriorDeathRight5;
                }
                if (spriteNum_13Frame == 6) {
                    image = warriorDeathRight6;
                }
                if (spriteNum_13Frame == 7) {
                    image = warriorDeathRight7;
                }
                if (spriteNum_13Frame == 8) {
                    image = warriorDeathRight8;
                }
                if (spriteNum_13Frame == 9) {
                    image = warriorDeathRight9;
                }
                if (spriteNum_13Frame == 10) {
                    image = warriorDeathRight10;
                }
                if (spriteNum_13Frame == 11) {
                    image = warriorDeathRight11;
                }
                if (spriteNum_13Frame == 12) {
                    image = warriorDeathRight12;
                }
                if (spriteNum_13Frame == 13) {
                    image = warriorDeathRight13;
                }
            }
            if (direction_horizontal == "left") {
                if (spriteNum_13Frame == 1) {
                    image = warriorDeathLeft1;
                }
                if (spriteNum_13Frame == 2) {
                    image = warriorDeathLeft2;
                }
                if (spriteNum_13Frame == 3) {
                    image = warriorDeathLeft3;
                }
                if (spriteNum_13Frame == 4) {
                    image = warriorDeathLeft4;
                }
                if (spriteNum_13Frame == 5) {
                    image = warriorDeathLeft5;
                }
                if (spriteNum_13Frame == 6) {
                    image = warriorDeathLeft6;
                }
                if (spriteNum_13Frame == 7) {
                    image = warriorDeathLeft7;
                }
                if (spriteNum_13Frame == 8) {
                    image = warriorDeathLeft8;
                }
                if (spriteNum_13Frame == 9) {
                    image = warriorDeathLeft9;
                }
                if (spriteNum_13Frame == 10) {
                    image = warriorDeathLeft10;
                }
                if (spriteNum_13Frame == 11) {
                    image = warriorDeathLeft11;
                }
                if (spriteNum_13Frame == 12) {
                    image = warriorDeathLeft12;
                }
                if (spriteNum_13Frame == 13) {
                    image = warriorDeathLeft13;
                }
            }
        }



        g2.drawImage(image, x, y, width, height,null);


        //Draw collision area
        g2.setColor(new Color(255, 0, 0, 100));
        g2.fillRect(x + collisionArea.x, y + collisionArea.y,
                collisionArea.width, collisionArea.height);


        ///  ////////////////////////////////////
        if (path_bsf != null) {
            for (PathNode node : path_bsf) {
                int tileX = node.x;
                int tileY = node.y;

                int screenX = tileX * panel.tileSize;
                int screenY = tileY * panel.tileSize;

                g2.setColor(new Color(0, 255, 157, 128)); // Màu đỏ, trong suốt
                g2.fillRect(screenX, screenY, panel.tileSize, panel.tileSize);
            }
        }


    }
    public Rectangle getDamageArea() {
        return damageArea;
    }
}

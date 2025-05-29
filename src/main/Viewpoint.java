package main;

public class Viewpoint {
    public int x, y;
    private int screenWidth, screenHeight;  // Kích thước vùng hiển thị (màn hình)
    private int mapWidth, mapHeight;

    public Viewpoint(int screenWidth, int screenHeight, int mapWidth, int mapHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.x = 0;
        this.y = 0;
    }

    public void follow(int playerX, int playerY) {
        x = playerX - screenWidth / 2;
        y = playerY - screenHeight / 2;
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x > mapWidth - screenWidth) x = mapWidth - screenWidth;
        if (y > mapHeight - screenHeight) y = mapHeight - screenHeight;

        // Nếu map nhỏ hơn màn hình, camera luôn 0
        if (mapWidth < screenWidth) x = 0;
        if (mapHeight < screenHeight) y = 0;
    }
}

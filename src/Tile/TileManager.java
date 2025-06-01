package Tile;

import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    private Panel panel;
    public  Tile[] tile;
    public int mapTileNum[][];
    public int mapCol, mapRow;
    
    private boolean[][] walkableMapCache;
    private boolean walkableMapDirty = true;

    //private final int width, height;

    public TileManager(Panel panel){
        tile = new Tile[10];
        this.panel = panel;

        // width = panel.tileSize * 4 / 3;
        // height = panel.tileSize * 4 / 3;
        getTileImage();
        // loadMap("/Mapdata/Map01.txt"); //Map
    }

    public void setMap(String mapType) {
        String mapPath = "/Mapdata/Map01.txt"; // Mặc định là Tiny (Map01)
        if ("Tiny".equals(mapType)) {
            mapPath = "/Mapdata/Map01.txt";
        } else if ("Medium".equals(mapType)) {
            mapPath = "/Mapdata/Map02.txt";
        } else if ("Big".equals(mapType)) {
            mapPath = "/Mapdata/Map03.txt";
        }
        loadMap(mapPath);
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tile[0].collision = true;

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/transparent.png"));
            tile[1].collision = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try (InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            java.util.List<int[]> rows = new java.util.ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] numbers = line.trim().split(" +");
                int[] row = new int[numbers.length];
                for (int i = 0; i < numbers.length; i++) {
                    try {
                        row[i] = Integer.parseInt(numbers[i]);
                    } catch (NumberFormatException e) {
                        row[i] = 0;
                    }
                }
                rows.add(row);
            }
            mapRow = rows.size();
            mapCol = rows.isEmpty() ? 0 : rows.get(0).length;
            mapTileNum = new int[mapCol][mapRow];
            for (int r = 0; r < mapRow; r++) {
                for (int c = 0; c < mapCol; c++) {
                    mapTileNum[c][r] = rows.get(r)[c];
                }
            }
            markWalkableMapDirty();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2, int viewpointX, int viewpointY, int boardWidth, int boardHeight) {
        int tileSize = panel.tileSize;
        int startCol = Math.max(0, viewpointX / tileSize);
        int endCol = Math.min((viewpointX + boardWidth) / tileSize, mapCol - 1);
        int startRow = Math.max(0, viewpointY / tileSize);
        int endRow = Math.min((viewpointY + boardHeight) / tileSize, mapRow - 1);

        for (int row = startRow; row <= endRow; row++) {
            for (int col = startCol; col <= endCol; col++) {
                int tileNum = mapTileNum[col][row];
                if (tileNum < 0 || tileNum >= tile.length || tile[tileNum] == null) continue;
                int screenX = col * tileSize - viewpointX;
                int screenY = row * tileSize - viewpointY;
                g2.drawImage(tile[tileNum].image, screenX, screenY, tileSize, tileSize, null);
            }
        }
    }

    public boolean[][] getWalkableMap() {
        if (walkableMapCache == null || walkableMapDirty) {
            walkableMapCache = new boolean[mapCol][mapRow];
            for (int row = 0; row < mapRow; row++) {
                for (int col = 0; col < mapCol; col++) {
                    int tileNum = mapTileNum[col][row];
                    walkableMapCache[col][row] = (tileNum >= 0 && tileNum < tile.length && !tile[tileNum].collision);
                }
            }
            walkableMapDirty = false;
        }
        return walkableMapCache;
    }

    public void markWalkableMapDirty() {
        walkableMapDirty = true;
    }

    public void drawCollisionAreas(Graphics2D g2, int viewpointX, int viewpointY) {
        g2.setColor(new Color(255, 0, 0, 100));
        // for (int row = 0; row < panel.maxScreenRow; row++) {
        //     for (int col = 0; col < panel.maxScreenCol; col++) {
        //         int tileNum = mapTileNum[col][row];

        //         if (tileNum < 0 || tileNum >= tile.length || !tile[tileNum].collision) {
        //             continue;
        //         }

        //         int x = col * panel.tileSize - viewpoint.x;
        //         int y = row * panel.tileSize - viewpoint.y;
        //         g2.fillRect(x, y, panel.tileSize, panel.tileSize);
        //     }
        // }
    }
}

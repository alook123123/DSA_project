package utilz;

import java.awt.Rectangle;
import Tile.TileManager;

public class Raycasting {
    /**
     * dùng raycasting để Boss phát hiện Player và đuổi theo
     * Kiểm tra xem từ source có thể nhìn thấy target hay không
     * 
     * @param source     Vị trí của Boss
     * @param target     Vị trí của Player
     * @param mapTileNum Bản đồ va chạm (true nếu là vật cản)
     * @param tileSize   Kích thước mỗi tile
     * @return true nếu có thể nhìn thấy
     */
    public static boolean canSeePlayer(Rectangle source, Rectangle target, int[][] mapTileNum, TileManager tileManager, int tileSize) {
        if (mapTileNum == null) {
            throw new IllegalArgumentException("mapTileNum must not be null. Ensure it is initialized correctly in TileManager.");
        }

        int x0 = source.x + source.width / 2;
        int y0 = source.y + source.height / 2;
        int x1 = target.x + target.width / 2;
        int y1 = target.y + target.height / 2;

        int dx = Math.abs(x1 - x0), dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1, sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            int tileX = x0 / tileSize;
            int tileY = y0 / tileSize;

            // **Bounds Check**: Ensure tileX and tileY are within bounds
            if (tileX < 0 || tileY < 0 || tileX >= mapTileNum.length || tileY >= mapTileNum[0].length) {
                return false; // Out of bounds, cannot see target
            }

            // Kiểm tra nếu tile là vật cản
            int tileNum = mapTileNum[tileX][tileY];
            if (tileManager.tile[tileNum].collision) {
                return false;
            }

            // Nếu tới đích
            if (x0 == x1 && y0 == y1) break;

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
        return true;
    }
}
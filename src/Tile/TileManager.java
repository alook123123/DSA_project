package Tile;

import main.MenuPanel;
import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

   private  MenuPanel gp;

   public  Tile[] tile;
   public int mapTileNum[][];

   private int baseX = 0, baseY = 0, offset = 64;


   Panel panel;
   private int width, height;

   public TileManager(Panel panel)
   {
       tile = new Tile[10];
       this.panel = panel;

       mapTileNum = new int[panel.maxScreenCol][panel.maxScreenRow];

       width = panel.tileSize * 4 / 3;
       height = panel.tileSize * 4 / 3;
       getTileImage();
       loadMap("/Mapdata/Map02.txt");
   }


   public void getTileImage()
   {
       try{
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/black.png"));
            tile[1].collision = false;

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tile[0].collision = true;



       }catch (IOException e)
       {

       }
   }

   public void loadMap(String filePath)
   {
        try {
            InputStream  is = getClass().getResourceAsStream(filePath);
            if (is == null) {
                throw new RuntimeException("Could not load /Maps/Map02.txt");
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;
            while (col<panel.maxScreenCol && row< panel.maxScreenRow)
            {
                String line = br.readLine();

                while(col < panel.maxScreenCol)
                {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == panel.maxScreenCol){
                    col = 0;
                    row++;
                }
            }

            br.close();
        } catch (Exception e) {

        }
   }

   public  void draw(Graphics2D g2)
   {

       int col = 0;
       int row = 0;
       int x = 0, y= 0;
       while (col < panel.maxScreenCol && row < panel.maxScreenRow)
       {
           int tileNum = mapTileNum[col][row];

           g2.drawImage(tile[tileNum].image,col * panel.tileSize,row * panel.tileSize,width,height,null);
           col++;
           x++;
           if (col == panel.maxScreenCol)
           {
               col = 0;
               x = 0;
               row ++;
               y++;
           }
       }

   }


    public void drawCollisionAreas(Graphics2D g2) {
        g2.setColor(new Color(255, 0, 0, 100)); // Semi-transparent red

        //player wall collision
//        for (int row = 0; row < panel.maxScreenRow; row++) {
//            for (int col = 0; col < panel.maxScreenCol; col++) {
//                int tileNum = mapTileNum[col][row];
//                if (tile[tileNum].collision) {
//                    // Draw collision rectangle
//                    int x = col * panel.tileSize;
//                    int y = row * panel.tileSize;
//                    g2.fillRect(x, y, panel.tileSize, panel.tileSize);
//                }
//            }
        }


}

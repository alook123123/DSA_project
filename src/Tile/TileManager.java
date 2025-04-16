package Tile;

import main.MenuPanel;
import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class TileManager {

   private  MenuPanel gp;
   private  Tile[] tile;
   private int baseX = 0, baseY = 0, offset = 64;


   Panel panel;
   private int width, height;

   public TileManager(Panel panel)
   {
       tile = new Tile[10];
       this.panel = panel;
       width = panel.tileSize * 4 / 3;
       height = panel.tileSize * 4 / 3;
       getTileImage();
   }


   public void getTileImage()
   {
       try{
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));

       }catch (IOException e)
       {
           e.printStackTrace();
       }
   }

   public  void draw(Graphics2D g2)
   {
       g2.drawImage(tile[0].image,offset * 4,offset * 3,width,height,null);
       g2.drawImage(tile[0].image,offset * 5,offset * 3,width,height,null);
       g2.drawImage(tile[0].image,offset * 6,offset * 3,width,height,null);
       g2.drawImage(tile[0].image,offset * 7,offset * 3,width,height,null);
       g2.drawImage(tile[0].image,offset * 8,offset * 3,width,height,null);
       g2.drawImage(tile[0].image,offset * 9,offset * 3,width,height,null);

       g2.drawImage(tile[0].image,offset * 4,offset * 4,width,height,null);
       g2.drawImage(tile[0].image,offset * 4,offset * 5,width,height,null);
       g2.drawImage(tile[0].image,offset * 4,offset * 6,width,height,null);
       g2.drawImage(tile[0].image,offset * 4,offset * 7,width,height,null);

       g2.drawImage(tile[0].image,offset * 5,offset * 8,width,height,null);
       g2.drawImage(tile[0].image,offset * 5,offset * 9,width,height,null);

       g2.drawImage(tile[0].image,offset * 6,offset * 9,width,height,null);
       g2.drawImage(tile[0].image,offset * 7,offset * 9,width,height,null);
       g2.drawImage(tile[0].image,offset * 8,offset * 9,width,height,null);
       g2.drawImage(tile[0].image,offset * 9,offset * 9,width,height,null);
       g2.drawImage(tile[0].image,offset * 10,offset * 9,width,height,null);

       g2.drawImage(tile[0].image,offset * 10,offset * 4,width,height,null);
       g2.drawImage(tile[0].image,offset * 10,offset * 5,width,height,null);
       g2.drawImage(tile[0].image,offset * 10,offset * 6,width,height,null);
       g2.drawImage(tile[0].image,offset * 10,offset * 8,width,height,null);
       g2.drawImage(tile[0].image,offset * 10,offset * 9,width,height,null);


   }

}

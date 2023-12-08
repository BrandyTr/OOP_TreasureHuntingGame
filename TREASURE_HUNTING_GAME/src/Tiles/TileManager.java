package Tiles;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int mapTitleNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10]; //tile for water, rock,... => can resize array tile
        mapTitleNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/world01.txt"); //Load map world 1

    }

    //add tiles image
    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/grass.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/rock.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/water.png"));
            
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/earth.png")); // add earth image

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/tree.png")); // tree

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/sand.png")); // sand
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //load map with the world map is the boundary
    public void loadMap(String filePath) { 

        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
            int col = 0, row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = br.readLine();

                while(col < gp.maxWorldCol) {

                    String numbers[] = line.split(" ");
                    
                    int num = Integer.parseInt(numbers[col]);

                    mapTitleNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

       
        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) { // remember to public the maxScreenCol/Row in GamePanel file

                    int tileNum = mapTitleNum[worldCol][worldRow];

                    int worldX = worldCol * gp.tileSize;
                    int worldY = worldRow * gp.tileSize;
                    int screenX = worldX - gp.player.worldX + gp.player.screenX; //subtract to draw tiles base on the first postion of the character(the character in the middle of the map)
                    int screenY = worldY - gp.player.worldY + gp.player.screenY; //add gp.player.screen make the player screen stop expand when character move to the corner of the map

                    //when the area in the screen boundary, the tile is drawn (it only draws tiles in areas appear in the screen)
                    //+- gp.tileSize to avoid delaying and the map look smoother when you move the character
                    if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                        
                        g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null); 
                    }

                    worldCol++; 
                    
                    if (worldCol == gp.maxWorldCol) {
                        worldCol = 0;                       
                        worldRow++;
                    }
                }

    }
}

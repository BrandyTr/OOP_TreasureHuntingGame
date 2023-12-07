package Entity;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Entity {
    public int x, y;
    public int speech;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    //use this to store image files
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

}

package Main;

import Entity.Player;
import Tiles.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable { //make Thread run => implements
    //Screen setting
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // actual tile size on screen

    //size of game screen in hori & verti
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    //ratio 4:3
    public final int screenWidth = tileSize * maxScreenCol; // 16*48 = 768
    public final int screenHeight = tileSize * maxScreenRow; //12*48 = 576

    //World setting
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    //FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread; //Thread: sthing y can start & stop
    //when start => keep running prog until stop
    public Player player = new Player(this, keyH);



    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); //set the size
        this.setBackground(Color.GRAY);
        this.setDoubleBuffered(true); //better rendering performance
        //If set to true, all drawing from this component be done in offscreen painting buffer
        this.addKeyListener(keyH);
        this.setFocusable(true); //with thí, GamePanel can be focused to receive key input
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        // start Thread => auto run this method
        //game loop => CORE OF THIS GAME

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;


        while (gameThread != null) { //gameThread exist => repeat process
            currentTime = System.nanoTime(); //return Java time source in nanose //More specific than Milis

            delta += (currentTime - lastTime)/drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                //1 UPDATE => eg: character position
                update();
                //2 DRAW => eg: screen update inf
                repaint(); //call paintComponent method
                delta--;
            }
        }

    }
    public void update() { //to draw //update position of player
        player.update();
    }
    public void paintComponent(Graphics g) { //Graphics: class has many funct to draw obj on screen
        super.paintComponent(g); //superclass: JPanel; subclass: GamePanel

        Graphics2D g2 = (Graphics2D) g; /*Graphics2D class extends the Graphics class
        to provide more sophisticated control over
        geometry, coordinate transformations,
        color management, and text layout. */

        tileM.draw(g2); //before player => to see player. If inverse => cannot see player
        player.draw(g2);
        g2.dispose(); //Dispose of this graphics context & release any sys resources that it is using
    }
}

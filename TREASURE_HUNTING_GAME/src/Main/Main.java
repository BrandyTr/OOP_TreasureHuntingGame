package Main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false); //cannot resize this window
        window.setTitle("Treasure Hunting Game");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack(); /*cause this window be sized to fit preference size
        & layouts of its subcomponents (GamePanel) */

        window.setLocationRelativeTo(null); //the window displays at the center of screen
        window.setVisible(true);
        gamePanel.startGameThread();
    }
}

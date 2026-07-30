package org.b3llabug.type_game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class TitleScreen extends JFrame {
    //hand-drawn title art
    private static final String TITLE_ART_PATH = "assets/speedTypingTitle.png";

    public TitleScreen() {
        setTitle("//speed typing game//");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //set background color
        getContentPane().setBackground(new Color(18, 38, 10));
        //null so i can do custom positions
        setLayout(null);

        //invisible hotspot right over the "start" text on the monitor screen in the art
        JButton startButton = new JButton();
        startButton.setBounds(985, 278, 232, 98);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setFocusPainted(false);
        startButton.setOpaque(false);
        startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        //enter to start game
        startButton.addActionListener(e -> startGame());
        add(startButton);

        //background art - added LAST. Swing z-orders null-layout siblings with the
        //FIRST-added component on top, so the background has to go on after the
        //overlay controls or it blanks them out - they still receive clicks fine through it.
        JLabel background = new JLabel();
        //getWidth()/getHeight() are still 0 here since pack() hasn't run yet -
        //use the art's fixed native size directly instead
        background.setBounds(0, 0, 1300, 800);
        try {
            BufferedImage titleArt = ImageIO.read(new File(TITLE_ART_PATH));
            background.setIcon(new ImageIcon(titleArt));
        } catch (IOException e) {
            //FIX: missing art shouldn't crash the title screen, just fall back to plain background
            System.err.println("couldn't load title art at " + TITLE_ART_PATH);
        }
        add(background);

        //FIX: size the content pane itself (not the outer window) so the drawable area
        //matches the art's native 1300x800 regardless of title bar height
        getContentPane().setPreferredSize(new Dimension(1300, 800));
        pack();
        setLocationRelativeTo(null);
    }

    private void startGame() {
        // transition to the game screen
        GameScreen gameScreen = new GameScreen();
        gameScreen.setVisible(true);
        this.dispose(); // close the title screen
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TitleScreen titleScreen = new TitleScreen();
            titleScreen.setVisible(true);
        });
    }
}
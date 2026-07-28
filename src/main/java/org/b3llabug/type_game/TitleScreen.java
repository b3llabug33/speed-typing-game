package org.b3llabug.type_game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class TitleScreen extends JFrame {
    private JButton startButton;

    //hand-drawn title art (bellabug at her desk, monitor screen reads "start")
    private static final String TITLE_ART_PATH = "assets/speedTypingTitle.png";

    public TitleScreen() {
        setTitle("//speed typing game//");
        //matches the title art's native size (and GameScreen's size)
        setSize(1300, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //set background color
        getContentPane().setBackground(new Color(18, 38, 10));
        //null so i can do custom positions
        setLayout(null);

        //background art - drawn first so everything else layers on top
        JLabel background = new JLabel();
        background.setBounds(0, 0, getWidth(), getHeight());
        try {
            BufferedImage titleArt = ImageIO.read(new File(TITLE_ART_PATH));
            background.setIcon(new ImageIcon(titleArt));
        } catch (IOException e) {
            //FIX: missing art shouldn't crash the title screen, just fall back to plain background
            System.err.println("couldn't load title art at " + TITLE_ART_PATH);
        }
        add(background);

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
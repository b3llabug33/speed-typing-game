package org.b3llabug.type_game;

import java.awt.*; 
import javax.swing.*;

public class TitleScreen extends JFrame {
    //hand-drawn title art
    private static final String TITLE_ART_PATH = "assets/speedTypingTitle.png";

    public TitleScreen() {
        setTitle("//speed typing game//"); //window titlebar text
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //null so i can do custom positions - it defaults to BorderLayout if not overridden
        setLayout(null);
        //invisible hotspot right over the "start" text on the monitor screen 
        JButton startButton = new JButton();
        startButton.setBounds(985, 278, 232, 98); //you wouldnt use setBounds if you used a layout
        startButton.setContentAreaFilled(false); //these 4 keep button invisible 
        startButton.setBorderPainted(false);
        startButton.setFocusPainted(false);
        startButton.setOpaque(false);/////////////
        startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //turns the default hand into the pointer when hovering over button
        //enter to start game
        startButton.addActionListener(e -> startGame()); 
        add(startButton);

        //background art - added LAST. 
        //first added component on top
        JLabel background = new JLabel(); //use JLabel to store background
        background.setBounds(0, 0, 1300, 800);
        background.setIcon(new ImageIcon(TITLE_ART_PATH));
        add(background);
        getContentPane().setPreferredSize(new Dimension(1300, 800)); //makes sure nothing is cut off
        pack(); //applies size changes
        setLocationRelativeTo(null); //null as the reference component means "center relative to the whole screen" 
            }                          //rather than relative to some parent window
    
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
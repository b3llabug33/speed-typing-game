// filepath: c:\Users\Krist\code\Type_Game\src\main\java\org\b3llabug\type_game\EndScreen.java

package org.b3llabug.type_game;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class EndScreen extends JFrame {

    //hand-drawn end screen art (WPM/ACC readout card)
    private static final String END_ART_PATH = "assets/speedTypeEndScreen.png";

    public EndScreen(int wpm, double accuracy) {
        setTitle("Game Over");
        //FIX: only the title screen should quit the whole app on close
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        //fallback background color if the art fails to load
        getContentPane().setBackground(new Color(23, 14, 31));

        //wpm value - sits in the blank readout space under the "WPM:" label drawn in the art
        JLabel wpmLabel = new JLabel(String.valueOf(wpm), SwingConstants.CENTER);
        wpmLabel.setFont(new Font("Courier New", Font.BOLD, 90));
        wpmLabel.setForeground(new Color(255, 193, 7));
        wpmLabel.setBounds(450, 285, 240, 135);
        add(wpmLabel);

        //acc value - sits in the blank readout space under the "ACC:" label drawn in the art
        JLabel accLabel = new JLabel(String.format("%.0f%%", accuracy), SwingConstants.CENTER);
        accLabel.setFont(new Font("Courier New", Font.BOLD, 70));
        accLabel.setForeground(new Color(255, 193, 7));
        accLabel.setBounds(450, 565, 240, 130);
        add(accLabel);

        //again button - the art has no button drawn for this, so it lives in the blank
        //column to the right of the card, lined up with the bottom bug box
        JButton againButton = new JButton("PLAY AGAIN");
        againButton.setFont(new Font("Courier New", Font.BOLD, 28));
        againButton.setBounds(950, 636, 320, 90);
        againButton.setBackground(new Color(108, 88, 141));
        againButton.setForeground(new Color(230, 230, 235));
        againButton.setBorder(BorderFactory.createLineBorder(new Color(131, 128, 136), 4));
        againButton.setFocusPainted(false);
        //FIX: don't let this grab default focus on open - a space press still held
        //from finishing the round would otherwise immediately "click" it via Swing's
        //default space-activates-focused-button behavior, skipping this screen entirely
        againButton.setFocusable(false);
        againButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(againButton);

        //home button - small, top-right corner, above the card
        JButton homeButton = new JButton("home");
        homeButton.setFont(new Font("Courier New", Font.PLAIN, 18));
        homeButton.setBounds(1150, 20, 120, 45);
        homeButton.setBackground(new Color(108, 88, 141));
        homeButton.setForeground(new Color(230, 230, 235));
        homeButton.setBorder(BorderFactory.createLineBorder(new Color(131, 128, 136), 3));
        homeButton.setFocusPainted(false);
        homeButton.setFocusable(false);
        homeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(homeButton);

        ///////button actions
        againButton.addActionListener(e -> {
            this.dispose();
            new GameScreen().setVisible(true);
        });

        homeButton.addActionListener(e -> {
            this.dispose();
            new TitleScreen().setVisible(true);
        });

        //background art - added LAST. Swing z-orders null-layout siblings with the
        //FIRST-added component on top, so the background has to go on after the
        //overlay controls or it blanks them out - they still receive clicks fine through it.
        JLabel background = new JLabel();
        //getWidth()/getHeight() are still 0 here since pack() hasn't run yet -
        //use the art's fixed native size directly instead
        background.setBounds(0, 0, 1300, 800);
        try {
            BufferedImage endArt = ImageIO.read(new File(END_ART_PATH));
            background.setIcon(new ImageIcon(endArt));
        } catch (IOException e) {
            //FIX: missing art shouldn't crash the end screen, just fall back to plain background
            System.err.println("couldn't load end screen art at " + END_ART_PATH);
        }
        add(background);

        //FIX: size the content pane itself (not the outer window) so the window matches
        //the other screens' drawable area regardless of title bar height
        getContentPane().setPreferredSize(new Dimension(1300, 800));
        pack();
        setLocationRelativeTo(null);
    }
}

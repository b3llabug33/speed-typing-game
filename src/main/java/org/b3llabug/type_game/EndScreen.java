// filepath: c:\Users\Krist\code\Type_Game\src\main\java\org\b3llabug\type_game\EndScreen.java

package org.b3llabug.type_game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class EndScreen extends JFrame {

    public EndScreen(int wpm, double accuracy) {
        setTitle("Game Over");
        //FIX: only the title screen should quit the whole app on close
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        //wpm label
        JLabel wpmLabel = new JLabel("WPM: " + wpm, SwingConstants.CENTER);
        wpmLabel.setFont(new Font("Courier New", Font.PLAIN, 80));
        wpmLabel.setBounds(350, 150, 400, 200);
        wpmLabel.setBorder(BorderFactory.createLineBorder(Color.green, 4));
        add(wpmLabel);

        //acc label
        JLabel accLabel = new JLabel("ACC: " + accuracy, SwingConstants.CENTER);
        accLabel.setFont(new Font("Courier New", Font.PLAIN, 50));
        accLabel.setBounds(750, 200, 400, 200);
        accLabel.setBorder(BorderFactory.createLineBorder(Color.green, 4));
        add(accLabel);

        //again button
        JButton againButton = new JButton("AGAIN");
        againButton.setFont(new Font("Courier New", Font.PLAIN, 60));
        againButton.setBounds(400, 400, 500, 120);
        againButton.setBorder(BorderFactory.createLineBorder(Color.pink, 4));
        againButton.setFocusPainted(false);
        //FIX: don't let this grab default focus on open - a space press still held
        //from finishing the round would otherwise immediately "click" it via Swing's
        //default space-activates-focused-button behavior, skipping this screen entirely
        againButton.setFocusable(false);
        add(againButton);
        
        //home button
        JButton homeButton = new JButton("home");
        homeButton.setFont(new Font("Courier New", Font.PLAIN, 32));
        homeButton.setBounds(1050, 20, 200, 50);
        homeButton.setBorder(BorderFactory.createLineBorder(Color.pink, 3));
        homeButton.setFocusPainted(false);
        homeButton.setFocusable(false);
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
       
        //background color
        getContentPane().setBackground(new Color(200, 255, 200));

        //FIX: size the content pane itself (not the outer window) so the window matches
        //the other screens' drawable area regardless of title bar height
        getContentPane().setPreferredSize(new Dimension(1300, 800));
        pack();
        setLocationRelativeTo(null);
    }
}
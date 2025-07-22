package org.b3llabug.type_game;

import java.awt.*;
import javax.swing.*;

public class TitleScreen extends JFrame {
    private JButton startButton;
    private JButton settingsButton;

    public TitleScreen() {
        setTitle("//speed typing game//");
        setSize(900,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //set background color
        getContentPane().setBackground(new Color(18, 38, 10));
        //null so i can do custom positions
        setLayout(null);

        //center the main panel
        int panelWidth = 600;
        int panelHeight = 300;
        int frameWidth = getWidth();
        int frameHeight = getHeight();
        int panelX = (frameWidth - panelWidth) / 2;
        int panelY = (frameHeight - panelHeight) / 2 - 50; // adjust -50 for vertical offset

    
        //green panel with tital and name 
       JPanel mainPanel = new JPanel();
       mainPanel.setBackground(new Color(110, 153, 56));
        mainPanel.setBounds(panelX, panelY, panelWidth, panelHeight);
       mainPanel.setLayout(null);

             //fake window bar
             JPanel windowBar = new JPanel();
             windowBar.setBackground(Color.lightGray);
             windowBar.setBounds(0,0,600,30);
             windowBar.setLayout(null);

             //colored buttons
             JPanel btn1 = new JPanel(); btn1.setBackground(new Color(204, 132, 0)); btn1.setBounds(500, 5, 20, 20);
             JPanel btn2 = new JPanel(); btn2.setBackground(new Color(255, 0, 204)); btn2.setBounds(530, 5, 20, 20);
             JPanel btn3 = new JPanel(); btn3.setBackground(new Color(0, 204, 204)); btn3.setBounds(560, 5, 20, 20);
             windowBar.add(btn1); windowBar.add(btn2); windowBar.add(btn3);

             mainPanel.add(windowBar);

       //title text
       JLabel titleLabel = new JLabel("<html>speed typing<br>game</html>");
       titleLabel.setFont(new Font("Courier New", Font.PLAIN, 48));
       titleLabel.setForeground(Color.WHITE);
       titleLabel.setBounds(60, 60, 400, 120);
       mainPanel.add(titleLabel);

             //my nameeee
             JLabel subLabel = new JLabel("//b3llabug");
             subLabel.setFont(new Font("Courier New", Font.PLAIN, 24));
             subLabel.setForeground(Color.WHITE);
             subLabel.setBounds(60, 200, 300, 40);
             mainPanel.add(subLabel);

             add(mainPanel);

        JButton startButton = new JButton("START");
        startButton.setFont(new Font("Courier New", Font.PLAIN, 36));
        startButton.setBackground(new Color(102, 68, 120)); // purple
        startButton.setForeground(Color.WHITE);
        startButton.setBounds(250, 420, 400, 70);
        startButton.setBorder(BorderFactory.createLineBorder(Color.lightGray, 4));
        //enter to start game
        startButton.addActionListener(e -> startGame());
        add(startButton);

        JButton settingsButton = new JButton();
        settingsButton.setBackground(new Color(102, 68, 120));
        settingsButton.addActionListener(e -> openSettings());
        settingsButton.setBounds(40, 470, 80, 80);
        //make design for this 
        add(settingsButton);
        
    }

    private void startGame() {
        // Transition to the game screen
        GameScreen gameScreen = new GameScreen();
        gameScreen.setVisible(true);
        this.dispose(); // Close the title screen
    }

    private void openSettings() {
        // Open the settings menu
        SettingsMenu settingsMenu = new SettingsMenu();
        settingsMenu.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TitleScreen titleScreen = new TitleScreen();
            titleScreen.setVisible(true);
        });
    }
}
// filepath: c:\Users\Krist\code\Type_Game\src\main\java\org\b3llabug\type_game\EndScreen.java

package org.b3llabug.type_game;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class EndScreen extends JFrame {
    private JLabel scoreLabel;
    private JButton restartButton;
    private JButton titleScreenButton;

    public EndScreen(int wpm, double accuracy) {
        setTitle("Game Over");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        scoreLabel = new JLabel(String.format("Final Score: WPM: %d | Accuracy: %.1f%%", wpm, accuracy), SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setForeground(new Color(200, 100, 50));
        add(scoreLabel);

        restartButton = new JButton("Restart Game");
        restartButton.setFont(new Font("Arial", Font.BOLD, 16));
        restartButton.addActionListener(e -> restartGame());
        add(restartButton);

        titleScreenButton = new JButton("Return to Title Screen");
        titleScreenButton.setFont(new Font("Arial", Font.BOLD, 16));
        titleScreenButton.addActionListener(e -> returnToTitleScreen());
        add(titleScreenButton);
    }

    private void restartGame() {
        // Logic to restart the game
        SwingUtilities.invokeLater(() -> {
            Type_Game game = new Type_Game();
            game.setVisible(true);
            dispose(); // Close the EndScreen
        });
    }

    private void returnToTitleScreen() {
        // Logic to return to the title screen
        SwingUtilities.invokeLater(() -> {
            TitleScreen titleScreen = new TitleScreen();
            titleScreen.setVisible(true);
            dispose(); // Close the EndScreen
        });
    }
}
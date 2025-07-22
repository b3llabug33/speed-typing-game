// filepath: c:\Users\Krist\code\Type_Game\src\main\java\org\b3llabug\type_game\GameScreen.java

package org.b3llabug.type_game;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class GameScreen extends JFrame {
    private JLabel wordLabel, timerLabel, scoreLabel;
    private JTextField inputField; 
    private JButton startButton; 
    private Timer gameTimer;
    private String[] wordBank = {"apple", "arch", "arrow", "bark", "beach", "bear", "bella", "bird", "blade", "bloom", "bloop", "boink", "book", "breeze", "bridge", "brown", "bug", "bump", "buzz", "cake", "candle", "car", "chair", "chip", "circle", "cloud", "coin", "computer", "corn", "crayon", "dance", "dawn", "desk", "dog", "dork", "dream", "drop", "dust", "eagle", "echo", "fence", "field", "fire", "fizz", "flame", "flop", "flower", "fog", "fox", "frog", "game", "gate", "gloop", "goat", "goof", "grass", "gush", "hammer", "hill", "home", "hope", "house", "iphone", "java", "jet", "jump", "jumps", "knot", "lake", "lamp", "lazy", "leaf", "leo", "mantis", "map", "meadow", "mirror", "moo", "moon", "mouse", "noodle", "ocean", "over", "paper", "path", "pen", "pine", "plop", "pop", "program", "programming", "quick", "rain", "random", "ridge", "ring", "river", "road", "rock", "rose", "sand", "shade", "shadow", "ship", "sky", "splat", "star", "starbucks", "stone", "storm", "stream", "sun", "swing", "table", "the", "tide", "tree", "tristan", "typing", "valley", "vine", "wacky", "wave", "whiz", "wind", "wish", "wood", "yap", "yard", "zap", "zing", "anchor", "badge", "basket", "blaze", "bottle", "branch", "bubble", "canyon", "castle", "cherry", "compass", "cricket", "desert", "diamond", "dragon", "emerald", "forest", "galaxy", "garden", "glacier", "horizon", "island", "journey", "jungle", "lantern", "legend", "lotus", "marble", "market", "melody", "mountain", "oasis", "opal", "orchid", "palace", "pebble", "pillar", "prairie", "puzzle", "raven", "rocket", "saddle", "sapphire", "spark", "sparrow", "temple", "thunder", "tiger", "trail", "treasure", "village", "volcano", "waterfall", "willow", "winter", "options", "stocks"};
    private Random random = new Random();
    private String currentWord;
    private int timeLeft = 60;
    private int correctWords = 0;
    private int totalWordsTyped = 0;
    private boolean isGameRunning = false;

    public GameScreen() {
        setTitle("Typing Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        wordLabel = new JLabel("Press Start to Begin!", SwingConstants.CENTER);
        wordLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(wordLabel);

        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.BOLD, 16)); 
        inputField.setEnabled(false);
        add(inputField);

        timerLabel = new JLabel("Time: 60", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(timerLabel); 

        scoreLabel = new JLabel("WPM: 0 | Accuracy: 100%", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(scoreLabel);

        startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        add(startButton);

        inputField.addActionListener(e -> checkInput());
        
        startButton.addActionListener(e -> {
            if (!isGameRunning) {
                isGameRunning = true;
                inputField.setEnabled(true);
                inputField.requestFocus();
                gameTimer.start();
                nextWord();
            }
        });

        gameTimer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time: " + timeLeft);
            if (timeLeft <= 0) {
                endGame();
            }
        });
    }

    private void endGame() {
        gameTimer.stop();
        inputField.setEnabled(false);
        isGameRunning = false;

        double minutes = (60.0 - timeLeft) / 60.0;
        int wpm = minutes > 0 ? (int) (correctWords / minutes) : 0;
        double accuracy = totalWordsTyped > 0 ? (correctWords * 100.0 / totalWordsTyped) : 100.0;
        JOptionPane.showMessageDialog(this, 
            String.format("Game Over! Final Score: WPM: %d | Accuracy: %.1f%%", wpm, accuracy),
            "Game Over", JOptionPane.INFORMATION_MESSAGE);
        resetGame();
    }

    private void resetGame() {
        timeLeft = 60;
        correctWords = 0;
        totalWordsTyped = 0;
        timerLabel.setText("Time: 60");
        scoreLabel.setText("WPM: 0 | Accuracy: 100%");
        wordLabel.setText("Press Start to Begin!");
    }

    private void checkInput() {
        if (!isGameRunning) return;
        String typedWord = inputField.getText().trim();
        totalWordsTyped++;
        if (typedWord.equals(currentWord)) {
            correctWords++;
        }
        updateScore();
        nextWord();
    }

    private void updateScore() {
        double minutes = (60.0 - timeLeft) / 60.0;
        int wpm = minutes > 0 ? (int) (correctWords / minutes) : 0;
        double accuracy = totalWordsTyped > 0 ? (correctWords * 100.0 / totalWordsTyped) : 100.0;
        scoreLabel.setText(String.format("WPM: %d | Accuracy: %.1f%%", wpm, accuracy));
    }

    private void nextWord() {
        if (!isGameRunning) return;
        int index = random.nextInt(wordBank.length);
        currentWord = wordBank[index];
        wordLabel.setText(currentWord);
        inputField.setText("");
    }
}
// filepath: c:\Users\Krist\code\Type_Game\src\main\java\org\b3llabug\type_game\GameScreen.java

package org.b3llabug.type_game;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class GameScreen extends JFrame {
    //ui components 
    private JLabel wordLabel, timerLabel;
    private JTextField inputField;
    private JButton startButton;
    private JButton[] timeButtons;
    private JPanel timerPanel, fakeWindowBar;
    private Timer gameTimer;
    //////// game logic components
    private String[] wordBank = {"apple", "arch", "arrow", "bark", "beach", "bear", "bella", "bird", "blade", "bloom", "bloop", "boink", "book", "breeze", "bridge", "brown", "bug", "bump", "buzz", "cake", "candle", "car", "chair", "chip", "circle", "cloud", "coin", "computer", "corn", "crayon", "dance", "dawn", "desk", "dog", "dork", "dream", "drop", "dust", "eagle", "echo", "fence", "field", "fire", "fizz", "flame", "flop", "flower", "fog", "fox", "frog", "game", "gate", "gloop", "goat", "goof", "grass", "gush", "hammer", "hill", "home", "hope", "house", "iphone", "java", "jet", "jump", "jumps", "knot", "lake", "lamp", "lazy", "leaf", "leo", "mantis", "map", "meadow", "mirror", "moo", "moon", "mouse", "noodle", "ocean", "over", "paper", "path", "pen", "pine", "plop", "pop", "program", "programming", "quick", "rain", "random", "ridge", "ring", "river", "road", "rock", "rose", "sand", "shade", "shadow", "ship", "sky", "splat", "star", "starbucks", "stone", "storm", "stream", "sun", "swing", "table", "the", "tide", "tree", "tristan", "typing", "valley", "vine", "wacky", "wave", "whiz", "wind", "wish", "wood", "yap", "yard", "zap", "zing", "anchor", "badge", "basket", "blaze", "bottle", "branch", "bubble", "canyon", "castle", "cherry", "compass", "cricket", "desert", "diamond", "dragon", "emerald", "forest", "galaxy", "garden", "glacier", "horizon", "island", "journey", "jungle", "lantern", "legend", "lotus", "marble", "market", "melody", "mountain", "oasis", "opal", "orchid", "palace", "pebble", "pillar", "prairie", "puzzle", "raven", "rocket", "saddle", "sapphire", "spark", "sparrow", "temple", "thunder", "tiger", "trail", "treasure", "village", "volcano", "waterfall", "willow", "winter", "options", "stocks"};
    ////////
    private String[] wordsSequence;
    private Random random = new Random();
    private int currentWordIndex = 0;
    private String currentWord;
    private int timeLeft = 60;
    private int correctWords = 0;
    private int totalWordsTyped = 0;
    private boolean isGameRunning = false;
    //long is for long integers were using it for milliseconds
    private long gameStartTime = 0;
    //set chunk size for the word rotation
    private static final int CHUNK_SIZE = 20;

    public GameScreen() {
        //main frame
        setTitle("speed type");
        setSize(1300, 800);
        //FIX: handle close ourselves below so we can confirm + not just vanish mid-round
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        //FIX: if this window is closed (X button/Alt+F4), confirm if a round is in
        //progress, then go back to the title screen instead of just disappearing
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (isGameRunning) {
                    int choice = JOptionPane.showConfirmDialog(GameScreen.this,
                            "Quit this round? Your progress will be lost.",
                            "Quit round?", JOptionPane.YES_NO_OPTION);
                    if (choice != JOptionPane.YES_OPTION) {
                        return; //cancel - keep playing
                    }
                    gameTimer.stop();
                }
                dispose();
                new TitleScreen().setVisible(true);
            }
        });

        //set background color
        getContentPane().setBackground(new Color(110, 153, 56));

        //timer selection
        timerPanel = new JPanel();
        timerPanel.setBackground(new Color(110, 153, 56));
        timerPanel.setBounds(60, 60, 160, 220);
        timerPanel.setLayout(null);
        //freaky words to make borders
        timerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        timerLabel = new JLabel("Time", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Courier New", Font.PLAIN, 32));
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setBounds(0, 10, 160, 40);
        timerPanel.add(timerLabel); 

        //time options
        JButton time15 = new JButton("15");
            time15.setBounds(40, 60, 80, 40);
            time15.addActionListener(e -> setTime(15));
        JButton time30 = new JButton("30");
            time30.setBounds(40, 110, 80, 40);
            time30.addActionListener(e -> setTime(30));
        JButton time60 = new JButton("60");
            time60.setBounds(40, 160, 80, 40);
            time60.addActionListener(e -> setTime(60));

            //"for-each" loop - creates a temp array of 3 buttons and applies one set of rules
            // to each instead of writting it 3 times
            timeButtons = new JButton[]{time15, time30, time60};
            for(JButton btn : timeButtons){
                btn.setFont(new Font("Courier New", Font.PLAIN, 28));
                btn.setBackground(new Color(153, 204, 153));
                btn.setForeground(Color.WHITE);
                //in swing when you are focused on something it gives it an outline
                //by default but this gets rid of that
                btn.setFocusPainted(false);
                btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                timerPanel.add(btn);
            }

            add(timerPanel);

        //main typing panel
        JPanel typingPanel = new JPanel();
        typingPanel.setBackground(new Color(153, 204, 153)); //light green
        typingPanel.setBounds(320, 60, 900, 400);
        typingPanel.setLayout(null);
        typingPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        
        //fake window bar thing
        fakeWindowBar = new JPanel();
        fakeWindowBar.setBackground(new Color(153, 204, 153));
        //FIX: needs explicit bounds + a null layout since its parent uses null layout too,
        //otherwise it renders at 0x0 and the corner buttons never show up
        fakeWindowBar.setBounds(0, 0, 900, 30);
        fakeWindowBar.setLayout(null);

        //for loop to make the fake buttons in less lines
        Color[] btnColors = {new Color(255, 153, 51), new Color(51, 102, 204), new Color(255, 204, 204)};
        for(int i = 0; i < btnColors.length; i++){
            JPanel btn = new JPanel();
            btn.setBackground(btnColors[i]);
            btn.setBounds(820 + i * 25, 8, 20, 20);
            btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            fakeWindowBar.add(btn);
        }
        typingPanel.add(fakeWindowBar);

        //word label - shows sequence of words
        wordLabel = new JLabel();
        wordLabel.setFont(new Font("Courier New", Font.PLAIN, 40));
        wordLabel.setForeground(Color.BLACK);
        wordLabel.setBounds(40, 60, 820, 300);
        typingPanel.add(wordLabel);

        add(typingPanel);

        //input field 
        inputField = new JTextField();
        inputField.setFont(new Font("Courier New", Font.PLAIN, 36));
        inputField.setBounds(400, 600, 600, 60);
        inputField.setVisible(false); // Hidden until game starts
        add(inputField);

        startButton = new JButton("Start");
        startButton.setFont(new Font("Courier New", Font.BOLD, 32));
        startButton.setBackground(new Color(102, 68, 120));
        startButton.setForeground(Color.WHITE);
        startButton.setBounds(1050, 500, 150, 60);
        startButton.setFocusPainted(false);
        startButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        add(startButton);

        //game timer
        gameTimer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time: " + timeLeft);
            if (timeLeft <= 0) {
                endGame();
            }
        });

        //button listeners
        //space for next word so it flows 
        inputField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e){
                if(e.getKeyCode() == java.awt.event.KeyEvent.VK_SPACE){
                    checkInput();
                    e.consume(); //prevent extra space
                }
            }
        });

        //enter button or click
        startButton.addActionListener(e -> startGame());

        resetGame();

    }

        //sets time and displays it
        private void setTime(int seconds){
            timeLeft = seconds;
            timerLabel.setText("time: " + timeLeft);
        }

        private void startGame(){
        isGameRunning = true;
        //FIX: lock the time presets and start button so they can't disrupt a round in progress
        startButton.setEnabled(false);
        for(JButton btn : timeButtons){
            btn.setEnabled(false);
        }
        inputField.setVisible(true);
        inputField.setEnabled(true);
        inputField.setText("");
        inputField.requestFocus();
        correctWords = 0;
        totalWordsTyped = 0;
        loadNextChunk();
        currentWordIndex = 0;
        //gameStartTime = System.currentTimeMillis();
        //gameTimer.start();
        updateWordsLabel();
        //infoLabel.setText("Type the words above!"); maybe do this idk
        gameStartTime = System.currentTimeMillis(); //milliseconds
        gameTimer.start();
        }

        private void loadNextChunk(){
            wordsSequence = genWords(CHUNK_SIZE); //generate new chunk
            currentWordIndex = 0; //set to 0 again
            updateWordsLabel(); //update display
        }

        //generate random words from the word bank
        private String[] genWords(int count) {
            String[] seq = new String[count];
            for(int i = 0; i < count; i++){
                seq[i] = wordBank[random.nextInt(wordBank.length)];
            }
            return seq;
        }
 
        
        //updates word label and highlights current wordls(wordsSequence[currentWordIndex 
        private void updateWordsLabel(){
            //create a string builder to build the html string for label
            //<html> tag lets you use HTML formatting in swing labels
            StringBuilder sb = new StringBuilder("<html>");
            //loop through all words in the wordSequence array
            for(int i = 0; i < wordsSequence.length; i++){
                //if this is the current word the user should type...
                if(i == currentWordIndex){
                    //...make the font of the word green using html <span> styling 
                    //append just means add this text to the end of what i already have
                    sb.append("<span style='color:green;'>").append(wordsSequence[i]).append("</span>");
                }
                else{
                    //otherwise add the word normally
                    sb.append(wordsSequence[i]);
                }
                //space between words
                sb.append(" ");
            }
            //close html tag
            sb.append("</html>");
            //set the labels text to the string that was built
            wordLabel.setText(sb.toString());
        }

        //checks the users input, updates score, moves to next word
        private void checkInput() {
        if (!isGameRunning) return;
        //remove leading/trailing spaces
        String typed = inputField.getText().trim();
        if(typed.length() == 0) return;
        //split input into words with spaces as seperators
        String[] typedWords = typed.split("\\s+");
        //loop through each word user types
        for(String word : typedWords){
            totalWordsTyped++;
            //if we havent reached the end of the wordSequence 
            //and the typed word matched currentWords count
            if(currentWordIndex < wordsSequence.length && word.equals(wordsSequence[currentWordIndex])) {
                correctWords++;
            }
            //next word now
            currentWordIndex++;
            //if the current chunk is finished load the next one unless times up
            if(currentWordIndex >= wordsSequence.length){
                if(timeLeft > 0){
                    loadNextChunk();
                    inputField.setText("");
                    return;
                } else {
                    endGame();
                    return;
                }
            }
        }
        updateWordsLabel();
        //clear input field for next word
        inputField.setText("");
        }

        private void endGame() {
        gameTimer.stop();
        //no more typing
        inputField.setEnabled(false);
        isGameRunning = false;
        //FIX: unlock controls for the next round
        startButton.setEnabled(true);
        for(JButton btn : timeButtons){
            btn.setEnabled(true);
        }
        // calculate wpm
        long elapsedMillis = System.currentTimeMillis() - gameStartTime;
        double minutes = elapsedMillis / 60000.0; // 60,000 ms in a minute
        //this way of writting it just means if minutes is greater than 0
        //use first value if not use second
        int wpm = minutes > 0 ? (int) (correctWords / minutes) : correctWords;
        double accuracy = totalWordsTyped > 0 ? (correctWords * 100.0 / totalWordsTyped) : 100.0;
        //go to end screen
        EndScreen endScreen = new EndScreen(wpm, accuracy);
        endScreen.setVisible(true);
        //closes game screen
        this.dispose();
        }

        //need main

    private void resetGame() {
        timeLeft = 60;
        correctWords = 0;
        totalWordsTyped = 0;
        currentWordIndex = 0;
        timerLabel.setText("time: " + timeLeft);
        wordLabel.setText("<html></html>");
        inputField.setText("");
        inputField.setVisible(false);
        isGameRunning = false;
    }

    //need main method to run
    public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new GameScreen().setVisible(true));
    }
} 


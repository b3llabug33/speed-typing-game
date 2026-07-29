// filepath: c:\Users\Krist\code\Type_Game\src\main\java\org\b3llabug\type_game\GameScreen.java

package org.b3llabug.type_game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GameScreen extends JFrame {
    //hand-drawn game screen art (timer panel, typing window, input bar, start button)
    private static final String GAME_ART_PATH = "assets/typingGameMainScreen.png";

    //ui components
    private JLabel wordLabel, timerLabel;
    private JTextField inputField;
    private JButton startButton;
    private JButton[] timeButtons;
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
        //FIX: handle close ourselves below so we can confirm + not just vanish mid-round
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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

        //fallback background color if the art fails to load
        getContentPane().setBackground(new Color(110, 153, 56));

        //timer countdown readout - sits right after the "TIME" title drawn in the art,
        //same sage-green tone as the art's own lettering
        timerLabel = new JLabel("60", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Courier New", Font.BOLD, 22));
        timerLabel.setForeground(new Color(158, 186, 148));
        timerLabel.setBounds(190, 40, 65, 34);
        add(timerLabel);

        //invisible hotspots right over the "15"/"30"/"60" boxes drawn in the art
        JButton time15 = new JButton();
        time15.setBounds(41, 88, 88, 78);
        time15.addActionListener(e -> setTime(15));

        JButton time30 = new JButton();
        time30.setBounds(41, 174, 88, 78);
        time30.addActionListener(e -> setTime(30));

        JButton time60 = new JButton();
        time60.setBounds(41, 260, 88, 78);
        time60.addActionListener(e -> setTime(60));

        //"for-each" loop - creates a temp array of 3 buttons and applies one set of rules
        // to each instead of writting it 3 times
        timeButtons = new JButton[]{time15, time30, time60};
        for (JButton btn : timeButtons) {
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setOpaque(false);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            add(btn);
        }

        //word label - shows sequence of words, sits over the typing window's
        //green paper area drawn in the art
        wordLabel = new JLabel();
        wordLabel.setFont(new Font("Courier New", Font.BOLD, 44));
        wordLabel.setForeground(new Color(20, 40, 10));
        wordLabel.setBounds(430, 270, 780, 380);
        wordLabel.setVerticalAlignment(SwingConstants.TOP);
        add(wordLabel);

        //input field - invisible/transparent so the drawn input box shows through
        inputField = new JTextField();
        inputField.setFont(new Font("Courier New", Font.BOLD, 30));
        inputField.setForeground(new Color(20, 40, 10));
        inputField.setCaretColor(new Color(20, 40, 10));
        inputField.setOpaque(false);
        inputField.setBorder(null);
        inputField.setBounds(365, 712, 630, 58);
        inputField.setVisible(false); // Hidden until game starts
        add(inputField);

        //invisible hotspot right over the "START" text drawn in the art
        startButton = new JButton();
        startButton.setBounds(1042, 712, 190, 58);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setFocusPainted(false);
        startButton.setOpaque(false);
        startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(startButton);

        //background art - added LAST. Swing (this environment/JDK build) paints/z-orders
        //null-layout siblings with the FIRST-added component on top, so the background
        //has to be added after all the overlay controls or it blanks out their text -
        //despite that, JLabel/JButton overlays still receive mouse clicks fine through it.
        JLabel background = new JLabel();
        //getWidth()/getHeight() are still 0 here since pack() hasn't run yet -
        //use the art's fixed native size directly instead
        background.setBounds(0, 0, 1300, 800);
        try {
            BufferedImage gameArt = ImageIO.read(new File(GAME_ART_PATH));
            background.setIcon(new ImageIcon(gameArt));
        } catch (IOException e) {
            //FIX: missing art shouldn't crash the game screen, just fall back to plain background
            System.err.println("couldn't load game art at " + GAME_ART_PATH);
        }
        add(background);

        //game timer
        gameTimer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText(String.valueOf(timeLeft));
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

        //FIX: setSize() sizes the whole window including the title bar, which left the
        //content pane a bit shorter than 800px and clipped the input field/start button
        //near the bottom edge. Sizing the content pane itself and packing around it
        //keeps the drawable area exactly matching the art's native 1300x800.
        getContentPane().setPreferredSize(new Dimension(1300, 800));
        pack();
        setLocationRelativeTo(null);
    }

        //sets time and displays it
        private void setTime(int seconds){
            timeLeft = seconds;
            timerLabel.setText(String.valueOf(timeLeft));
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
                    //...make the font of the word gold so it pops against the green paper
                    //append just means add this text to the end of what i already have
                    sb.append("<span style='color:#FFC107;'>").append(wordsSequence[i]).append("</span>");
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
        timerLabel.setText(String.valueOf(timeLeft));
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

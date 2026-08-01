// filepath: c:\Users\Krist\code\Type_Game\src\main\java\org\b3llabug\type_game\GameScreen.java

package org.b3llabug.type_game;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class GameScreen extends JFrame {
    //game screen art
    private static final String GAME_ART_PATH = "assets/typingGameMainScreen.png";
    //ui components
    private JLabel wordLabel, timerLabel;
    private JTextField inputField;
    private JButton startButton;
    private JButton[] timeButtons;
    private Timer gameTimer;
    //////// game logic components
    private String[] wordBank = {"apple", "arch", "arrow", "bark", "beach", "bear", "bella", "bird", "blade", "bloom", "bloop", "boink", "book", "breeze", "bridge", "brown", "bug", "bump", "buzz", "cake", "candle", "car", "chair", "chip", "circle", "cloud", "coin", "computer", "corn", "crayon", "dance", "dawn", "desk", "dog", "dork", "dream", "drop", "dust", "eagle", "echo", "fence", "field", "fire", "fizz", "flame", "flop", "flower", "fog", "fox", "frog", "game", "gate", "gloop", "goat", "goof", "grass", "gush", "hammer", "hill", "home", "hope", "house", "iphone", "java", "jet", "jump", "jumps", "knot", "lake", "lamp", "lazy", "leaf", "leo", "mantis", "map", "meadow", "mirror", "moo", "moon", "mouse", "noodle", "ocean", "over", "paper", "path", "pen", "pine", "plop", "pop", "program", "programming", "quick", "rain", "random", "ridge", "ring", "river", "road", "rock", "rose", "sand", "shade", "shadow", "ship", "sky", "splat", "star", "starbucks", "stone", "storm", "stream", "sun", "swing", "table", "the", "tide", "tree", "tristan", "typing", "valley", "vine", "wacky", "wave", "whiz", "wind", "wish", "wood", "yap", "yard", "zap", "zing", "anchor", "badge", "basket", "blaze", "bottle", "branch", "bubble", "canyon", "castle", "cherry", "compass", "cricket", "desert", "diamond", "dragon", "emerald", "forest", "galaxy", "garden", "glacier", "horizon", "island", "journey", "jungle", "lantern", "legend", "lotus", "marble", "market", "melody", "mountain", "oasis", "opal", "orchid", "palace", "pebble", "pillar", "prairie", "puzzle", "raven", "rocket", "saddle", "sapphire", "spark", "sparrow", "temple", "thunder", "tiger", "trail", "treasure", "village", "volcano", "waterfall", "willow", "winter", "options", "stocks",
    "of", "to", "and", "in", "is", "it", "you", "that", "he", "was", "for", "on", "are", "with", "as", "his", "they", "be", "at", "one", "have", "this", "from", "or", "had", "by", "not", "word", "but", "what", "some", "we", "can", "out", "other", "were", "all", "there", "when", "up", "use", "your", "how", "said", "an", "each", "she", "which", "do", "their", "time", "if", "will", "way", "about", "many", "then", "them", "write", "would", "like", "so", "these", "her", "long", "make", "thing", "see", "him", "two", "has", "look", "more", "day", "could", "go", "come", "did", "number", "sound", "no", "most", "people", "my", "know", "water", "than", "call", "first", "who", "may", "down", "side", "been", "now", "find", "any", "new", "work", "part", "take", "get", "place", "made", "live", "where", "after", "back", "little", "only", "round", "man", "year", "came", "show", "every", "good", "me", "give", "our", "under", "name", "very", "just", "form", "great", "think", "say", "help", "low", "line", "differ", "turn", "cause", "much", "mean", "before", "move", "right", "boy", "old", "too", "same", "tell", "does", "set", "three", "want", "air", "well"};
    ////////monkeytype-style common short words (from monkeytype's english_1k list)
    private String[] wordsSequence;
    private Random random = new Random();
    private int currentWordIndex = 0;
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
        setTitle("//speed typing game//");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(null);
        //if window is closed go back to the title screen instead of just disappearing
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (isGameRunning) {
                    gameTimer.stop();
                }
                dispose();
                new TitleScreen().setVisible(true);
            }
        });

        //fallback background color 
        getContentPane().setBackground(new Color(110, 153, 56));

        //timer countdown readout 
        timerLabel = new JLabel("60", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Courier New", Font.BOLD, 22));
        timerLabel.setForeground(new Color(158, 186, 148));
        timerLabel.setBounds(210, 30, 65, 34);
        add(timerLabel);

        //invisible hotspots right over the "15"/"30"/"60" boxes drawn in the art
        JButton time15 = new JButton();
        time15.setBounds(61, 78, 88, 78);
        time15.addActionListener(e -> setTime(15)); //15 seconds

        JButton time30 = new JButton();
        time30.setBounds(61, 164, 88, 78);
        time30.addActionListener(e -> setTime(30)); //30 seconds

        JButton time60 = new JButton();
        time60.setBounds(61, 250, 88, 78);
        time60.addActionListener(e -> setTime(60)); //60 seconds

        //"for-each" loop - creates a temp array of 3 buttons and applies one set of rules
        // to each instead of writting it 3 times
        timeButtons = new JButton[]{time15, time30, time60};
        for (JButton btn : timeButtons) {
            btn.setContentAreaFilled(false); //make invisible
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setOpaque(false); //////
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            add(btn);
        }

        //word label - shows sequence of words
        wordLabel = new JLabel();
        wordLabel.setFont(new Font("Courier New", Font.BOLD, 44));
        wordLabel.setForeground(new Color(20, 40, 10));
        wordLabel.setBounds(430, 270, 780, 380);
        wordLabel.setVerticalAlignment(SwingConstants.TOP);
        add(wordLabel);

        //input field 
        inputField = new JTextField();
        inputField.setFont(new Font("Courier New", Font.BOLD, 30));
        inputField.setForeground(new Color(20, 40, 10)); //letter color
        inputField.setCaretColor(new Color(20, 40, 10)); //text cursor color 
        inputField.setOpaque(false);
        inputField.setBorder(null);
        inputField.setBounds(365, 712, 630, 58);
        inputField.setVisible(false); // hidden until game starts
        add(inputField);

        //start button
        startButton = new JButton();
        startButton.setBounds(1042, 712, 190, 58);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setFocusPainted(false);
        startButton.setOpaque(false);
        startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(startButton);

        //background art - added LAST
        JLabel background = new JLabel();
        background.setBounds(0, 0, 1300, 800);
        background.setIcon(new ImageIcon(GAME_ART_PATH));
        add(background);

        //game timer
        gameTimer = new Timer(1000, e -> { //every 1 second
            timeLeft--; //time left goes down
            timerLabel.setText(String.valueOf(timeLeft)); //timer label matches
            if (timeLeft <= 0) { //if it runs out the game ends 
                endGame();
            }
        });

        //button listeners
        //space for next word so it flows
        inputField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e){
                if(e.getKeyCode() == java.awt.event.KeyEvent.VK_SPACE){ //if space is pressed
                    checkInput(); //call checkInput
                    e.consume(); //prevent extra space
                }
            }
        });

        //enter button or click
        startButton.addActionListener(e -> startGame());
        resetGame();
        //FIX: setSize() sizes the whole window including the title bar so the art gets clipped 
        getContentPane().setPreferredSize(new Dimension(1300, 800));
        pack(); //load
        setLocationRelativeTo(null);
    }

        //sets time and displays it
        private void setTime(int seconds){
            timeLeft = seconds;
            timerLabel.setText(String.valueOf(timeLeft));
        }

        private void startGame(){
        isGameRunning = true;
        //lock the time presets and start button so they can't disrupt a round in progress
        startButton.setEnabled(false);
        for(JButton btn : timeButtons){
            btn.setEnabled(false);
        }
        inputField.setVisible(true); //show things now
        inputField.setEnabled(true);
        inputField.setText("");
        inputField.requestFocus();
        correctWords = 0;
        totalWordsTyped = 0;
        loadNextChunk();
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


        //updates word label and highlights current words(wordsSequence[currentWordIndex
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

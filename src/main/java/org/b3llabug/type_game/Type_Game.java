// filepath: c:\Users\Krist\code\Type_Game\src\main\java\org\b3llabug\type_game\Type_Game.java
package org.b3llabug.type_game;

import javax.swing.SwingUtilities;

public class Type_Game {
    public static void main(String[] args) {
        // Runs the UI code on the Event Dispatch Thread (EDT), which is required for Swing to handle UI updates
        SwingUtilities.invokeLater(() -> {
            // Create and display the title screen
            TitleScreen titleScreen = new TitleScreen();
            titleScreen.setVisible(true);
        });
    }
}
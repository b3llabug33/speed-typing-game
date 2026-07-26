// filepath: c:\Users\Krist\code\Type_Game\src\main\java\org\b3llabug\type_game\Type_Game.java
package org.b3llabug.type_game;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;
import javax.swing.SwingUtilities;

public class Type_Game {
    public static void main(String[] args) {
        // FIX: any exception that slips through a button/key handler on the UI thread
        // gets written to crash.log instead of silently vanishing
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            try (PrintWriter pw = new PrintWriter(new FileWriter("crash.log", true))) {
                pw.println("=== " + new Date() + " ===");
                throwable.printStackTrace(pw);
            } catch (Exception ignored) {
                // nothing else we can do if we can't even write the log
            }
            throwable.printStackTrace();
        });

        // Runs the UI code on the Event Dispatch Thread (EDT), which is required for Swing to handle UI updates
        SwingUtilities.invokeLater(() -> {
            // Create and display the title screen
            TitleScreen titleScreen = new TitleScreen();
            titleScreen.setVisible(true);
        });
    }
}
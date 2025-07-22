package org.b3llabug.type_game;

import java.util.ArrayList;
import java.util.List;

public class ThemeManager {
    private List<Theme> themes;
    private Theme currentTheme;

    public ThemeManager() {
        themes = new ArrayList<>();
        loadThemes();
        currentTheme = themes.get(0); // Set default theme
    }

    private void loadThemes() {
        // Example themes, you can add more themes with different colors and styles
        themes.add(new Theme("Default", 
            new Color(240, 240, 240), // Background color
            new Color(0, 0, 0), // Text color
            new Font("Arial", Font.PLAIN, 16))); // Font

        themes.add(new Theme("Dark", 
            new Color(30, 30, 30), // Background color
            new Color(255, 255, 255), // Text color
            new Font("Arial", Font.PLAIN, 16))); // Font

        themes.add(new Theme("Retro", 
            new Color(255, 204, 0), // Background color
            new Color(0, 102, 204), // Text color
            new Font("Courier New", Font.BOLD, 16))); // Font
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public void setCurrentTheme(Theme theme) {
        this.currentTheme = theme;
    }

    public Theme getCurrentTheme() {
        return currentTheme;
    }

    public void applyTheme(GameScreen gameScreen) {
        gameScreen.getContentPane().setBackground(currentTheme.getBackgroundColor());
        gameScreen.setFont(currentTheme.getFont());
        // Apply other theme properties as needed
    }
}
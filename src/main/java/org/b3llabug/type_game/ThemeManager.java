package org.b3llabug.type_game;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;

public class ThemeManager {
    //holds current theme
    private static Theme currentTheme;
    //this creates "dictionary" called themes
    //the string is the name of the theme and the Theme is an
    //object that stores all the settings for the theme
    private static final Map<String, Theme> themes = new HashMap<>();
    //code inside static runs once when the class is first loaded (before objects)
    //its used to set up static variables
    static {
        themes.put("original", new Theme (
                new Color(18, 38, 10),     // FIX: matches the title screen's actual background
                Color.WHITE,                // text color
                new Font("Courier New", Font.PLAIN, 16), // font
                null       // background image
        ));
        themes.put("twoo", new Theme(
                new Color(235, 235, 245),
                Color.BLACK,
                new Font("Courier New", Font.PLAIN, 16),
                null
        ));
        //set initial theme
        currentTheme = themes.get("original");

    }

    //returns the current theme
    public static Theme getCurrentTheme(){
        return currentTheme;
    }

    //applies a current theme
    //updates currentTheme
    public static void applyTheme(String themeName){
        Theme theme = themes.get(themeName);
        if(theme != null){
            currentTheme = theme;
        }
    }

    //FIX: restyles an already-open window with the current theme's background color
    public static void applyThemeTo(JFrame frame){
        if(frame != null){
            frame.getContentPane().setBackground(currentTheme.backgroundColor);
            frame.repaint();
        }
    }

    //returns all themes
    public static Map<String, Theme> getThemes(){
        return themes;
    }
   

    //theme class holds all theme properties 
    //add more fields later for extra details and images 
    public static class Theme {
        public Color backgroundColor;
        public Color textColor;
        public Font font;
        public Image backgroundImage;
       
        //constructor 
        public Theme(Color backgroundColor, Color textColor, Font font, Image backgroundImage){
            this.backgroundColor = backgroundColor;
            this.textColor = textColor;
            this.font = font;
            this.backgroundImage = backgroundImage;
        }


    }
}
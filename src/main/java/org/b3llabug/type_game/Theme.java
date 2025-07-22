package org.b3llabug.type_game;

import java.awt.Color;
import java.awt.Font;

public class Theme {
    private String name;
    private Color backgroundColor;
    private Color textColor;
    private Font font;

    public Theme(String name, Color backgroundColor, Color textColor, Font font) {
        this.name = name;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.font = font;
    }

    public String getName() {
        return name;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getTextColor() {
        return textColor;
    }

    public Font getFont() {
        return font;
    }
}
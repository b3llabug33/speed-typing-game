# Speed Typing Game

A Java Swing desktop application for practicing typing speed and accuracy. Features multiple screens, theme customization, and end-of-round scoring.

## Features

- Randomized word/phrase prompts
- Real-time accuracy tracking
- WPM (words per minute) calculation
- Customizable color themes
- Settings menu
- End screen with round summary

## Screenshots

_Title screen → Game screen → End screen_

## How to Run

Requires Java 11+.

```bash
cd src/main/java
javac org/b3llabug/type_game/*.java
java org.b3llabug.type_game.Type_Game
```

## Project Structure

```
src/main/java/org/b3llabug/type_game/
├── Type_Game.java      # Entry point, launches Swing EDT
├── TitleScreen.java    # Start menu
├── GameScreen.java     # Main typing interface
├── SettingsMenu.java   # Theme and settings configuration
├── EndScreen.java      # Score summary after each round
├── Theme.java          # Theme data class
└── ThemeManager.java   # Manages and applies themes
```

## Requirements

- Java 11+
- No external dependencies

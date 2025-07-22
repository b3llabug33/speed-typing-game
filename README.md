# Type Game

## Overview
Type Game is a typing speed game that challenges players to type words as quickly and accurately as possible. The game features a title screen, a game screen, and an ending screen that displays the player's performance metrics, including Words Per Minute (WPM) and accuracy. Players can also customize their experience through a settings menu that allows them to select different themes.

## Features
- **Title Screen**: The initial screen where players can start the game or access the settings menu.
- **Game Screen**: The main gameplay area where players type the displayed words against a timer.
- **End Screen**: Displays the final score, including WPM and accuracy, with options to restart the game or return to the title screen.
- **Settings Menu**: Allows players to choose from various themes that change the design and appearance of the game.
- **Theme Management**: Supports multiple themes with customizable colors, fonts, and styles.

## File Structure
```
Type_Game
├── src
│   └── main
│       └── java
│           └── org
│               └── b3llabug
│                   └── type_game
│                       ├── Type_Game.java
│                       ├── TitleScreen.java
│                       ├── GameScreen.java
│                       ├── EndScreen.java
│                       ├── SettingsMenu.java
│                       ├── Theme.java
│                       └── ThemeManager.java
├── README.md
```

## Getting Started
To run the game, follow these steps:

1. Ensure you have Java Development Kit (JDK) installed on your machine.
2. Clone the repository or download the project files.
3. Navigate to the project directory in your terminal.
4. Compile the Java files using the command:
   ```
   javac src/main/java/org/b3llabug/type_game/*.java
   ```
5. Run the game using the command:
   ```
   java -cp src/main/java org.b3llabug.type_game.Type_Game
   ```

## Themes
The game includes several themes that can be selected from the settings menu. Each theme alters the visual appearance of the game, providing a unique experience for players. Themes can include variations in color schemes, fonts, and button styles.

## Contributing
Contributions to enhance the game are welcome! Feel free to submit pull requests or open issues for any bugs or feature requests.

## License
This project is licensed under the MIT License - see the LICENSE file for details.
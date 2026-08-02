# Speed Typing Game

A little Java Swing game I made to practice typing speed. Type the words as they show up before the timer runs out, then see your WPM and accuracy on the end screen.

## What it does

- Pick a time limit before you start — 15, 30, or 60 seconds
- Type the words shown on screen (word bank has ~250 words, mostly short/common ones with a few longer ones mixed in)
- Words come in chunks and refill automatically as you go, just hit space after each word
- When time's up you land on an end screen with your WPM and accuracy, plus buttons to play again or go back to the title screen
- Everything's drawn over hand-drawn background art instead of default gray Swing buttons

## How to run

Needs Java 11+. Run these from the repo root — the art won't load if you `cd` into `src` first since the `assets/` folder lives at the top level:

```bash
javac -d bin src/main/java/org/b3llabug/type_game/*.java
java -cp bin org.b3llabug.type_game.Type_Game
```

## Files

- `Type_Game.java` — entry point, just launches the title screen
- `TitleScreen.java` — start screen
- `GameScreen.java` — the actual game: word bank, timer, WPM/accuracy tracking
- `EndScreen.java` — shows your score, play again / home buttons

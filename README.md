# Donkey Kong - Java Edition

A Donkey Kong game implemented in Java using object-oriented design patterns.

## Features

- 3 different levels with progressive difficulty
- Multiple enemies: Gorilla, Bats, Beef
- Weapon and power-up system: Banana, Bomb, Sword
- Diverse mechanics: Ladders, Doors, Hidden Traps
- Scoring system with a Top 10 ranking
- Graphical interface using image tiles
- Observer pattern for event synchronization

## Technologies

- **Language**: Java
- **Architecture**: MVC with Observer pattern
- **GUI**: Tile-based system using images
- **Structure**: OOP with interfaces and abstract classes

## How to Play

1. Compile the project
2. Run `Main.java`
3. Move using the arrow keys
4. Collect items, defeat enemies, and reach the Princess
5. Complete all 3 levels to win!

## Project Structure

The project is organized as follows:

```text
src/
├── objects/ # Game entities (Players, Crates, Walls, etc.)
├── pt/iscte/poo/
│ ├── game/ # Core logic and game state management
│ ├── gui/ # Graphical interface (ImageMatrixGUI)
│ ├── observer/ # Observer Design Pattern implementation
│ └── utils/ # Utility classes (Vectors, Directions, etc.)
rooms/ # Text files defining level layouts
images/ # Graphic assets (sprites and icons)

```

## Authors

- José Jarmela (122663)
- Tiago Nogueira (122693)







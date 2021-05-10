# Tigerfish Pac-Man Project


### Introduction

Welcome to the Tigerfish Team Pac-Man game. Here you will find a basic set of instructions to assist you in game play.

This project was coded by Ezri Braid-Grizzel, Asmaa Chaudhry, Emily Nishikimoto, Nicole Roberts, and Liz Sevigny. Starter code was provided by Prof. Melody Su.

### How to Access the Game
1. Click the green 'Code' button and copy the HTTPS link.
2. Open your system terminal and change to the desired directory by typing 'cd' + the directory path into the command line.
3. When you are in the desired directory, type 'git clone' + the URL you copied from GitHub.
4. Type 'cd project-pacman-tigerfish' into the command line.
5. Compile the code by typing 'javac -d bin src/*.java'
6. Run the code by typing 'java -cp bin BlobAnimation' into the command line. A new window should pop up, allowing you to play the game.

### Basic Game Play

When you first run our Pac-Man game, you will see the game interface. There will be a maze comprised of 
green walls, small yellow pellets, two ghosts, a yellow animated Pac-man. Your goal is to maneuver 
Pac-Man around the maze to eat all the pellets. Beware of the ghosts! If Pac-Man and any of the ghosts collide,
Pac-Man will lose a life; lose all three, and it's game over!


Once Pac-Man has eaten all the pellets on-screen, your will move to the next level. Each time you level up, the 
pellets will respawn and an additional ghost will spawn on the maze. You win the game when you make it to
Level 11 without losing all your lives. 


### Game Manager


To **start** the game: press 's'
To **pause** the game: press the space bar
To **resume** the game: press the space bar
To **quit** the game: press 'Esc'
To **exit** the game: close the application window

To move Pac-Man: use the up, down, left, right arrow 
keys on your keyboard by either holding the key down 
or repeatedly pressing the key


### Game Interactions


**Maze** - static boundaries that neither Pac-Man nor ghosts can pass through
**Pellets** - when Pac-man passes over a pellet, the pellet gets eaten and the score increases by 1 point
**Ghosts** - when Pac-Man collides with a ghost, the respawn and Pac-Man loses one life (nothing occurs
when two ghosts collide)
**Lives** - when all three lives are lost, the game ends and the user can restart the game form Level 1


### Files in Project

1. **Basecode Package** - contains hard code for this project detailed below
	* BlobAnimation.java - creates window and runs the game from the main method
	* Board.java - draws game components, stages the game interactions, takes in key events
	* Ghost.java - blueprint for instances of the ghosts
2. **Images Package** - contains all the .gif and .png files used to create Pac-Man, pellet, and ghost icons


#### Credits

> Pac Man was made by Namco and release in 1980.

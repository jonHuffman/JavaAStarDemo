# Java A* Demo

## Description
This is a basic demo that implements the A-Star pathfinding algorithm and represents it visually in a console application. A simple guide to the algorithm can be found here https://www.raywenderlich.com/3016-introduction-to-a-pathfinding.

In the Demo our agent will wander the maze endlessly, choosing a new destination at random whenever he reaches his current one.

My goal with the demo was to write it in a way that someone that has learned the basics of Java and written some console applications of their own might be able to download the project and, with minimal research, come to understand how it is working.

## Running the Demo
You can run the demo without compiling the code by running the jar file found in /JavaProject\out\artifacts\JavaProject_jar
There is a batch file in this folder as well that will launch the demo for you in a console window.

## Modifying the Level
The level is simply a text file that uses three characters to define the layout of the maze.

| Character | Description                              |
| --------- | ---------------------------------------- |
| 0         | A wall tile                              |
| 1         | An open tile                             |
| c         | An open tilw with the agent occupying it |

All rows must be the same length and there must be a tile defining the agent's starting position.

The program opens the LevelFile.txt file that exists in the same folder as the .jar file. Try renaming Big_LevelFile.txt to see this in action.

## Challenges
If you are checking this project out because you are (or wish to) learning Java, here are some challenges that you might try. They are of varying difficulty to accomplish, so don't get discouraged if you can't get one right away.

* Change the agent's color so that they are more visible on screen
* Have the agent wait for 3 seconds each time they reach their destination
* Have the agent blink a different color each time they reach their destination
* At the bottom of the level, print the number of times the agent has reached their destination
* At the bottom of the level, print how long it took the program to calculate its current path
* At the bottom of the level, print how many dead-ends the program found before finding its current path
* Add additional types of Tiles that are more difficult (higher point cost) for the agent to traverse
* Modify the application so that there are two agents

## Tools
This application was written in the free version of the IntelliJ IDEA IDE (found here https://www.jetbrains.com/idea/), you should be able to install the IDE and then open the project within it and have it run.

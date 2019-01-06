package com.jhuffman;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Main
{
    private static Level loadedLevel;
    private static LinkedList<Tile> path;

    public static void main(String[] args)
    {
        ClearScreen();

        loadedLevel = new Level();
        boolean isLevelLoaded = loadedLevel.LoadLevelFromFile("./LevelFile.txt");

        path = new LinkedList<>();

        if (isLevelLoaded)
        {
            RunUpdateLoop();
        }
        else
        {
            System.out.print("Level failed to load.");
        }
    }

    private static void RunUpdateLoop()
    {
        // 4fps, because console windows look bad when trying to render too fast
        long frameLength = 125;

        while (true)
        {
            long updateStart = System.currentTimeMillis();

            Update();

            long updateEnd = System.currentTimeMillis();
            long elapsedTime = updateEnd - updateStart;
            long remainingTime = frameLength - elapsedTime;

            if (remainingTime > 0)
            {
                try
                {
                    TimeUnit.MILLISECONDS.sleep(remainingTime);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void Update()
    {
        ClearScreen();

        if (path.size() > 0)
        {
            // I am treating the LinkedList like a Queue, I get the first Tile in it,
            // and all the rest of the Tiles "move up" in the queue
            Tile nextTileInPath = path.removeFirst();

            loadedLevel.MoveAgentToTile(nextTileInPath);
        }
        else
        {
            Tile tileWithAgent = loadedLevel.GetCurrentOccupiedTile();
            path = Pathfinder.FindPath(tileWithAgent, loadedLevel.GetTileAt(9, 9));
        }

        loadedLevel.DrawLevel();
    }

    private static void ClearScreen()
    {
        try
        {
            // This should clear the console window of any output we had previously printed
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void WaitForEnterToContinue()
    {
        System.out.print("Press enter to continue...");
        try
        {
            System.in.read();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
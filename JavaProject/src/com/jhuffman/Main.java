package com.jhuffman;

import java.util.concurrent.TimeUnit;

public class Main
{
    private static Level loadedLevel;

    private static boolean isNavigatingPath;
    private static int currentPathStep;

    public static void main(String[] args)
    {
        ClearScreen();

        isNavigatingPath = false;

        loadedLevel = new Level();
        boolean isLevelLoaded = loadedLevel.LoadLevelFromFile("./LevelFile.txt");

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
        long frameLength = 250;

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

        if (isNavigatingPath)
        {
            loadedLevel.agentTile.containsAgent = false;

            if(currentPathStep < Pathfinder.path.size())
            {
                loadedLevel.agentTile = Pathfinder.path.get(currentPathStep++);
                loadedLevel.agentTile.containsAgent = true;
            }
            else
            {
                isNavigatingPath = false;
            }
        }
        else
        {
            isNavigatingPath = Pathfinder.FindPath(loadedLevel.agentTile, loadedLevel.GetTileAt(9, 9));
            currentPathStep = 0;
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
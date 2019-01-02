package com.jhuffman;

import java.util.concurrent.TimeUnit;

public class Main
{
    private static Level loadedLevel;

    public static void main(String[] args)
    {
        ClearScreen();

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
        // Length of a frame in milliseconds for the 30fps target
        long frameLength = 1 / 30 * 1000;

        while (true)
        {
            long updateStart = System.currentTimeMillis();

            Update();

            long updateEnd = System.currentTimeMillis();
            long elapsedTime = updateEnd - updateStart;
            long remainingTime = frameLength - elapsedTime;

            if(remainingTime > 0)
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
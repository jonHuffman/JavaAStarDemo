package com.jhuffman;

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
        while (true)
        {
            Update();
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
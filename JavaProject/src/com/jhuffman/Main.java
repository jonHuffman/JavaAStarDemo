package com.jhuffman;

public class Main
{
    private static HelloWorldPrinter helloWorldPrinter;

    public static void main(String[] args)
    {
        Level level = new Level();
        boolean isLevelLoaded = level.LoadLevelFromFile("./LevelFile.txt");

        if(isLevelLoaded == false)
        {
            System.out.print("Level failed to load.");
        }

        WaitForEnterToContinue();
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
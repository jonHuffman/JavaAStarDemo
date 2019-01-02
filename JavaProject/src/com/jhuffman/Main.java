package com.jhuffman;

public class Main
{
    private static HelloWorldPrinter helloWorldPrinter;

    public static void main(String[] args)
    {
        helloWorldPrinter = new HelloWorldPrinter();
        helloWorldPrinter.Print();

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
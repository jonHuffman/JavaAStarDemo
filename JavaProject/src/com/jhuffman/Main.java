package com.jhuffman;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Hello World!");
        System.out.print("Press enter to continue...");
        try
        {
            System.in.read();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}

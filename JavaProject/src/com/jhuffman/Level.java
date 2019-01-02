package com.jhuffman;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Level
{
    List<List<Tile>> tiles;

    public Level()
    {
        tiles = new ArrayList<List<Tile>>();
    }

    public boolean LoadLevelFromFile(String fileName)
    {
        // Reads all lines from a file (loaded from a path) as a Stream
        try (Stream<String> stream = Files.lines(Paths.get(fileName)))
        {
            // For each line in the file, pass the line in to the LoadTileRow function
            stream.forEach(System.out::println);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}

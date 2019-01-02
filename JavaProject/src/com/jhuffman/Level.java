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
            stream.forEach(line -> LoadTileRow(line));
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void DrawLevel()
    {
        for(int i = 0; i < tiles.size(); i++)
        {
            DrawRow(tiles.get(i));
        }
    }

    private void LoadTileRow(String tileRow)
    {
        // Each row gets a new List of Tiles in the tiles array
        tiles.add(new ArrayList<Tile>());

        // Get each individual character in the tileRow and create a new Tile object for it
        // Add the new Tile object as an entry in the new List of Tiles we created
        for(int i = 0; i < tileRow.length(); i++)
        {
            char tileData = tileRow.charAt(i);
            tiles.get(tiles.size() - 1).add(new Tile(tileData));
        }
    }

    private void DrawRow(List<Tile> tileRow)
    {
        for(int i = 0; i < tileRow.size(); i++)
        {
            tileRow.get(i).DrawTile();
        }
        System.out.println();
    }
}

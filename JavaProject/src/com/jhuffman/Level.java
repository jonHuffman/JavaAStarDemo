package com.jhuffman;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Level
{
    public Tile agentTile;

    private List<List<Tile>> tiles;

    // The function that shares the same name as the Class is called the constructor.
    // It is the function that you call when you want to Instantiate an instance of your class.
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
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public Tile GetTileAt(int X, int Y)
    {
        if(Y >= tiles.size() ||X > tiles.get(Y).size())
        {
            return null;
        }

        return tiles.get(Y).get(X);
    }

    public void DrawLevel()
    {
        for (int i = 0; i < tiles.size(); i++)
        {
            DrawRow(tiles.get(i));
        }
    }

    private void LoadTileRow(String tileRow)
    {
        // Each row gets a new List of Tiles in the tiles array
        List<Tile> newRow = new ArrayList<Tile>();

        // Get each individual character in the tileRow and create a new Tile object for it
        for (int i = 0; i < tileRow.length(); i++)
        {
            // Create a new Tile object for the character
            char tileData = tileRow.charAt(i);
            Tile newTile = new Tile(tileData);
            newTile.location.X = i;
            newTile.location.Y = Clamp(tiles.size() - 1, 0, Integer.MAX_VALUE);

            // Get the neighbours that may exist for this tile, and link them to this tile.
            Tile westNeighbour = GetWesternNeighbour(i, newRow);
            Tile northNeighbour = GetNorthernNeighbour(i);
            LinkNeighbourTiles(newTile, westNeighbour, northNeighbour);

            // Add the new Tile object as an entry in the new List of Tiles we created
            newRow.add(newTile);

            if(newTile.containsAgent)
            {
                agentTile = newTile;
            }
        }

        tiles.add(newRow);
    }

    private Tile GetWesternNeighbour(int tileIndex, List<Tile> tileRow)
    {
        // If this is the second character in tileRow or greater, than there is a western neighbour
        if (tileIndex >= 1)
        {
            return tileRow.get(tileIndex - 1);
        }
        else
        {
            return null;
        }
    }

    private Tile GetNorthernNeighbour(int tileIndex)
    {
        if (tiles.size() - 1 > 0)
        {
            List<Tile> previousRow = tiles.get(tiles.size() - 1);
            return previousRow.get(tileIndex);
        }
        else
        {
            return null;
        }
    }

    private void LinkNeighbourTiles(Tile mainTile, Tile westNeighbour, Tile northNeighbour)
    {
        if (westNeighbour != null)
        {
            mainTile.neighbours.west = westNeighbour;
            westNeighbour.neighbours.east = mainTile;
        }

        if (northNeighbour != null)
        {
            mainTile.neighbours.north = northNeighbour;
            northNeighbour.neighbours.south = mainTile;
        }
    }

    private void DrawRow(List<Tile> tileRow)
    {
        for (int i = 0; i < tileRow.size(); i++)
        {
            tileRow.get(i).DrawTile();
        }
        System.out.println();
    }

    private int Clamp(int value, int min, int max)
    {
        if(value < min)
        {
            value = min;
        }
        else if(value > max)
        {
            value = max;
        }

        return value;
    }
}

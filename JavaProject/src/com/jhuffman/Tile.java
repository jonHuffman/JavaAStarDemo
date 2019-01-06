package com.jhuffman;

import java.util.ArrayList;
import java.util.List;

public class Tile
{
    public enum TileType
    {
        Open,
        Blocked
    }

    public TileType tileType;
    public boolean containsAgent;
    public Neighbours neighbours;
    public Point location;

    // Constructors can have parameters just like any function.
    // We can use these parameters to set some of the values in our object.
    public Tile(char tileData)
    {
        containsAgent = false;
        neighbours = new Neighbours();
        location = new Point();

        try
        {
            tileType = ConvertCharToTileType(tileData);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void DrawTile()
    {
        if (containsAgent)
        {
            System.out.print('¥');
            return;
        }

        switch (tileType)
        {
            case Blocked:
                System.out.print('▓');
                break;
            case Open:
                System.out.print(' ');
                break;
            default:
                System.out.print('?');
        }

    }

    public ArrayList<Tile> GetOpenNeighbourTiles()
    {
        ArrayList<Tile> openNeighbours = new ArrayList<Tile>();

        if(neighbours.north != null && neighbours.north.tileType == TileType.Open)
        {
            openNeighbours.add(neighbours.north);
        }

        if(neighbours.east != null && neighbours.east.tileType == TileType.Open)
        {
            openNeighbours.add(neighbours.east);
        }

        if(neighbours.south != null && neighbours.south.tileType == TileType.Open)
        {
            openNeighbours.add(neighbours.south);
        }

        if(neighbours.west != null && neighbours.west.tileType == TileType.Open)
        {
            openNeighbours.add(neighbours.west);
        }

        return openNeighbours;
    }

    private TileType ConvertCharToTileType(char tileData)
    {
        switch (tileData)
        {
            case '0':
                return TileType.Blocked;
            case '1':
                return TileType.Open;
            case 'c':
                containsAgent = true;
                return TileType.Open;
            default:
                throw new IllegalArgumentException(tileData + " is an unrecognized level element.");
        }
    }
}

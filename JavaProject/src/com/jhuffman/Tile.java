package com.jhuffman;

import java.util.ArrayList;

public class Tile
{
    public enum TileType
    {
        Walkable,
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
            case Walkable:
                System.out.print(' ');
                break;
            default:
                System.out.print('?');
        }

    }

    public ArrayList<Tile> GetWalkableNeighbourTiles()
    {
        ArrayList<Tile> walkableNeighbours = new ArrayList<Tile>();

        if(neighbours.north != null && neighbours.north.tileType == TileType.Walkable)
        {
            walkableNeighbours.add(neighbours.north);
        }

        if(neighbours.east != null && neighbours.east.tileType == TileType.Walkable)
        {
            walkableNeighbours.add(neighbours.east);
        }

        if(neighbours.south != null && neighbours.south.tileType == TileType.Walkable)
        {
            walkableNeighbours.add(neighbours.south);
        }

        if(neighbours.west != null && neighbours.west.tileType == TileType.Walkable)
        {
            walkableNeighbours.add(neighbours.west);
        }

        return walkableNeighbours;
    }

    private TileType ConvertCharToTileType(char tileData)
    {
        switch (tileData)
        {
            case '0':
                return TileType.Blocked;
            case '1':
                return TileType.Walkable;
            case 'c':
                containsAgent = true;
                return TileType.Walkable;
            default:
                throw new IllegalArgumentException(tileData + " is an unrecognized level element.");
        }
    }
}

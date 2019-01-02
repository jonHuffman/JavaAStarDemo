package com.jhuffman;

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

    // Constructors can have parameters just like any function.
    // We can use these parameters to set some of the values in our object.
    public Tile(char tileData)
    {
        containsAgent = false;
        neighbours = new Neighbours();

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

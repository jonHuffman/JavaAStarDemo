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

    public Tile(char tileData)
    {
        containsAgent = false;

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
        switch (tileType)
        {
            case Blocked:
                // ▓ character (extended ascii 178)
                System.out.printf("%c", 0x2593);
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

package com.jhuffman;

import java.util.*;

// A* Algorithm explanation - https://www.raywenderlich.com/3016-introduction-to-a-pathfinding
public class Pathfinder
{
    public static LinkedList<Tile> path;

    private static List<Tile> openList;
    private static List<Tile> closedList;

    private static Map<Tile, Integer> tileMovementCost;
    private static Map<Tile, Integer> estimatedDistanceToDestination;
    private static Map<Tile, Integer> tileScore;

    private static Tile destinationTile;

    public static boolean FindPath(Tile start, Tile destination)
    {
        destinationTile = destination;

        ReinitializedCollections();
        SetStartTileAsCurrent(start);

        return CheckNextTile(start);
    }

    private static boolean CheckNextTile(Tile nextTile)
    {
        path.addLast(nextTile);
        closedList.add(nextTile);

        if(openList.contains(nextTile))
        {
            openList.remove(nextTile);
        }

        List<Tile> neighbourTiles = nextTile.GetOpenNeighbourTiles();

        for(int i = 0; i < neighbourTiles.size(); i++)
        {
            Tile neighbour = neighbourTiles.get(i);

            if(closedList.contains(neighbour) == false)
            {
                // !openList.contains(neighbour) is the same as openList.contains(neighbour) == false
                if(!openList.contains(neighbour))
                {
                    openList.add(neighbour);
                    tileMovementCost.put(neighbour, tileMovementCost.get(nextTile) + 1);
                    estimatedDistanceToDestination.put(neighbour, GetEstimatedDistanceToDestination(neighbour));
                    tileScore.put(neighbour, GetTileScore(neighbour));
                }
                else
                {
                    int newMovementCost = tileMovementCost.get(nextTile) + 1;
                    int newTileScore = GetTileScore(neighbour, newMovementCost);

                    if(newTileScore < tileScore.get(neighbour))
                    {
                        tileMovementCost.put(neighbour, newMovementCost);
                        tileScore.put(neighbour, GetTileScore(neighbour));
                    }
                }
            }
        }

        if(openList.size() == 0)
        {
            // No path found
            return false;
        }

        Tile nextTileInPath = null;
        for(int i = 0; i < openList.size(); i++)
        {
            Tile tile = openList.get(i);

            // If the neighbour is hte destination, then we have found our goal
            if(tile == destinationTile)
            {
                path.addLast(tile);
                return true;
            }

            if(nextTileInPath == null)
            {
                nextTileInPath = tile;
            }
            else if(tileScore.get(tile) < tileScore.get(nextTileInPath))
            {
                nextTileInPath = tile;
            }
        }

        // A function that calls itself is known as a Recursive function
        return CheckNextTile(nextTileInPath);
    }

    private static void ReinitializedCollections()
    {
        openList = new ArrayList<Tile>();
        closedList = new ArrayList<Tile>();

        tileMovementCost = new HashMap<Tile, Integer>();
        estimatedDistanceToDestination = new HashMap<Tile, Integer>();
        tileScore = new HashMap<Tile, Integer>();

        path = new LinkedList<Tile>();
    }

    private static void SetStartTileAsCurrent(Tile startTile)
    {
        tileMovementCost.put(startTile, 0);
        estimatedDistanceToDestination.put(startTile, GetEstimatedDistanceToDestination(startTile));
        tileScore.put(startTile, GetTileScore(startTile));
    }

    private static int GetEstimatedDistanceToDestination(Tile currentTile)
    {
        int xDistance = destinationTile.location.X - currentTile.location.X;
        int yDistance = destinationTile.location.Y - currentTile.location.Y;

        // Distance values should only be positive
        if(xDistance < 0)
        {
            xDistance = xDistance * -1;
        }

        if(yDistance < 0)
        {
            yDistance = yDistance * -1;
        }

        return xDistance + yDistance;
    }

    private static int GetTileScore(Tile tile)
    {
        return tileMovementCost.get(tile) + estimatedDistanceToDestination.get(tile);
    }

    // Two functions can have the same name, if they have difference Signatures (parameters in this case)
    private static int GetTileScore(Tile tile, int movementCost)
    {
        return movementCost + estimatedDistanceToDestination.get(tile);
    }
}

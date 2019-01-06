package com.jhuffman;

import java.util.*;

// A* Algorithm explanation - https://www.raywenderlich.com/3016-introduction-to-a-pathfinding
public class Pathfinder
{
    // A LinkedList is a series of nodes that know which others are connected to it
    // In this example B knows that its previous node is A and its next node is C
    // A <-> B <-> C <-> D
    private static LinkedList<Tile> path;

    private static ArrayList<Tile> openList;
    private static ArrayList<Tile> closedList;

    private static Map<Tile, Integer> tileMovementCost;
    private static Map<Tile, Integer> estimatedDistanceToDestination;
    private static Map<Tile, Integer> tileScore;

    private static Tile currentTile;
    private static Tile destinationTile;

    public static LinkedList<Tile> FindPath(Tile start, Tile destination)
    {
        destinationTile = destination;

        ReinitializeCollections();
        SetStartTileAsCurrent(start);

        return FindPath();
    }

    private static void ReinitializeCollections()
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
        // We prime our collections with the startTile's path-scoring values
        // These values allow the other tiles to calculate their scores
        tileMovementCost.put(startTile, 0);
        estimatedDistanceToDestination.put(startTile, GetEstimatedDistanceToDestination(startTile));
        tileScore.put(startTile, GetTileScore(startTile));

        currentTile = startTile;
    }

    private static LinkedList<Tile> FindPath()
    {
        // !pathFound can be read as "Not path found" or "Path not found", this statement could also be written pathFound == false
        do
        {
            path.addLast(currentTile);

            MoveToClosedList(currentTile);

            AddCurrentTileNeighboursToOpenList();

            // If our openList is empty at this point, then no path has been found
            // We'll return an empty LinkedList so the app can continue to run
            if(openList.size() == 0)
            {
                return new LinkedList<>();
            }

            currentTile = GetNextTileInPath();

            // If we were unable to find the next step in the Path then we
            // abort and return an empty LinkedList so the app can continue to run
            if(currentTile == null)
            {
                return new LinkedList<>();
            }
        }
        while(currentTile != destinationTile);

        // Add the final tile we found, as it is our destination
        path.addLast(currentTile);

        return path;
    }

    private static void MoveToClosedList(Tile tile)
    {
        closedList.add(tile);

        if(openList.contains(tile))
        {
            openList.remove(tile);
        }
    }

    private static void AddCurrentTileNeighboursToOpenList()
    {
        ArrayList<Tile> neighbourTiles = currentTile.GetWalkableNeighbourTiles();
        for(int i = 0; i < neighbourTiles.size(); i++)
        {
            Tile neighbour = neighbourTiles.get(i);

            if(closedList.contains(neighbour) == false)
            {
                if(openList.contains(neighbour) == false)
                {
                    openList.add(neighbour);
                    tileMovementCost.put(neighbour, tileMovementCost.get(currentTile) + 1);
                    estimatedDistanceToDestination.put(neighbour, GetEstimatedDistanceToDestination(neighbour));
                    tileScore.put(neighbour, GetTileScore(neighbour));
                }
                else
                {
                    int newMovementCost = tileMovementCost.get(currentTile) + 1;
                    int newTileScore = GetTileScore(neighbour, newMovementCost);

                    if(newTileScore < tileScore.get(neighbour))
                    {
                        tileMovementCost.put(neighbour, newMovementCost);
                        tileScore.put(neighbour, GetTileScore(neighbour));
                    }
                }
            }
        }
    }

    private static Tile GetNextTileInPath()
    {
        Tile bestNextTile = null;
        ArrayList<Tile> neighbourTiles;
        int furthestValidTileIndex = path.indexOf(currentTile);

        do
        {
            // In the furthestValidTileIndex goes below 0 it means that we have run out
            // of possible viable tiles in the path. This means there is no path to find.
            if(furthestValidTileIndex < 0)
            {
                return null;
            }

            neighbourTiles = path.get(furthestValidTileIndex).GetWalkableNeighbourTiles();

            for (int i = 0; i < neighbourTiles.size(); i++)
            {
                Tile neighbour = neighbourTiles.get(i);

                // This is the same as writing openList.contains(neighbour) == true
                // contains returns true if it succeeds, so we don't NEED to do further checking
                if (openList.contains(neighbour))
                {
                    if (neighbour == destinationTile)
                    {
                        return neighbour;
                    }

                    if (bestNextTile == null)
                    {
                        bestNextTile = neighbour;
                    }
                    else if (tileScore.get(neighbour) < tileScore.get(bestNextTile))
                    {
                        // If our tile has a lower score than it is more likely to take us
                        // to our destination as fast as possible
                        bestNextTile = neighbour;
                    }
                }
            }

            // If we have not yet found a tile, then we need to step backwards in our path and try a different fork
            if(bestNextTile == null)
            {
                furthestValidTileIndex--;
            }
        }
        while(bestNextTile == null);

        // If our furthestValidTileIndex is not our currentTile then it means
        // we ran into a dead end of some kind. We need to remove all steps
        // in the path after the valid one that we back-tracked to
        if(furthestValidTileIndex != path.indexOf(currentTile))
        {
            path.subList(furthestValidTileIndex + 1, path.size()).clear();
        }

        return bestNextTile;
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

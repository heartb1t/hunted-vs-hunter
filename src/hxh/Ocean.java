package hxh;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author Max William S. Filgueira, João Pedro de A. Paula
 * @version 2018-10-10
 */
public class Ocean
{
    // Default values for the height and width of the ocean
    private final static int DEFAULT_HEIGHT = 200;
    private final static int DEFAULT_WIDTH = 200;
    private final Seaweed[][] seaweed;
    private final Fish[][] fish;
    private final Integer height;
    private final Integer width;
    private final Random rand = Randomizer.getRandom();

    /**
     * Default constructor for ocean with default values.
     */
    public Ocean()
    {
        this(DEFAULT_HEIGHT, DEFAULT_WIDTH);
    }

    /**
     * Represent an ocean of the given dimensions.
     *
     * @param height The height of the ocean.
     * @param width The width of the ocean.
     */
    public Ocean(int height, int width)
    {
        this.height = height;
        this.width = width;
        seaweed = new Seaweed[height][width];
        fish = new Fish[height][width];
    }

    /**
     * Return the fish at the given location, if any.
     *
     * @param height The desired row.
     * @param width The desired column.
     * @return The fish at the given location, or null if there is none.
     */
    public Fish getFishAt(int height, int width)
    {
        return fish[height][width];
    }

    /**
     * Return the seaweed at the given location, if any.
     *
     * @param location The desired location.
     * @return The seaweed at the given location, or null if there is none.
     */
    public Seaweed getSeaweedAt(Location location)
    {
        return seaweed[location.getRow()][location.getCol()];
    }

    /**
     * Return the fish at the given location, if any.
     *
     * @param location The desired location.
     * @return The fish at the given location, or null if there is none.
     */
    public Fish getFishAt(Location location)
    {
        return fish[location.getRow()][location.getCol()];
    }

    /**
     * @return The height of the ocean.
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * @return The width of the ocean.
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Return a shuffled list of locations adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     *
     * @param location The location from which to generate adjacencies.
     * @return A list of locations adjacent to that given.
     */
    public List<Location> adjacentLocations(Location location)
    {
        assert location != null : "Null location passed to adjacentLocations";
        // The list of locations to be returned.
        List<Location> locations = new LinkedList<Location>();

        if(location != null) {
            int row = location.getRow();
            int col = location.getCol();

            for (int roffset = -1; roffset <= 1; roffset++) {
                int nextRow = row + roffset;

                if (nextRow >= 0 && nextRow < this.height) {
                    for (int coffset = -1; coffset <= 1; coffset++) {
                        int nextCol = col + coffset;
                        // Exclude invalid locations and the original location.
                        if (nextCol >= 0 && nextCol < this.width &&
                            (roffset != 0 || coffset != 0)) {
                            locations.add(new Location(nextRow, nextCol));
                        }
                    }
                }
            }

            // Shuffle the list. Several other methods rely on the list
            // being in a random order.
            Collections.shuffle(locations, rand);
        }

        return locations;
    }

    /**
     * Empty the field.
     */
    public void clear()
    {
        for(int row = 0; row < this.height; row++) {
            for(int col = 0; col < this.width; col++) {
                fish[row][col] = null;
                seaweed[row][col] = null;
            }
        }
    }

    /**
     * Clear the given location.
     *
     * @param location The location to clear.
     */
    public void clear(Location location)
    {
        fish[location.getRow()][location.getCol()] = null;
    }

    /**
     * Clear the seaweed at given location.
     *
     * @param location The location to clear.
     */
    public void clearSeaweed(Location location)
    {
        seaweed[location.getRow()][location.getCol()] = null;
    }

    /**
     * Place an fish at the given location.
     * If there is already an fish at the location it will
     * be lost.
     *
     * @param fish The fish to be placed.
     * @param height Row coordinate of the location.
     * @param width Column coordinate of the location.
     */
    public void place(Fish fish, int height, int width)
    {
        place(fish, new Location(height, width));
    }

    /**
     * Place a fish at the given location.
     * If there is already an fish at the location it will
     * be lost.
     *
     * @param newFish The fish to be placed.
     * @param location Where to place the fish.
     */
    public void place(Fish newFish, Location location)
    {
        fish[location.getRow()][location.getCol()] = newFish;
    }

    /**
     * Place a seaweed at the given location.
     * If there is already a seaweed at the location it will
     * be lost.
     *
     * @param newSeaweed The seaweed to be placed.
     * @param location Where to place the seaweed.
     */
    public void place(Seaweed newSeaweed, Location location)
    {
        seaweed[location.getRow()][location.getCol()] = newSeaweed;
    }

    /**
     * Try to find a free location that is adjacent to the
     * given location. If there is none, return null.
     * The returned location will be within the valid bounds
     * of the ocean.
     *
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area.
     */
    public Location freeAdjacentLocation(Location location)
    {
        // The available free ones.
        List<Location> free = getFreeAdjacentLocations(location);

        if (free.size() > 0)
            return free.get(0);
        else
            return null;
    }

    /**
     * Get a shuffled list of the free adjacent locations.
     *
     * @param location Get locations adjacent to this.
     * @return A list of free adjacent locations.
     */
    public List<Location> getFreeAdjacentLocations(Location location)
    {
        List<Location> free = new LinkedList<Location>();
        List<Location> adjacent = adjacentLocations(location);

        for (Location next : adjacent) {
            if (getFishAt(next) == null)
                free.add(next);
        }

        return free;
    }
}

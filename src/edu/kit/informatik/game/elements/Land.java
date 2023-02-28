package edu.kit.informatik.game.elements;

import edu.kit.informatik.ui.ErrorMessage;
import edu.kit.informatik.ui.GameException;
import edu.kit.informatik.ui.Main;
import edu.kit.informatik.utils.Vector2d;

import java.util.HashMap;
import java.util.Map;

/**
 * This is an essential game element. It saves the players tiles. Each Land can only have one barn at the specified
 * location, every other tile needs to be a cultivatable tile.
 *
 * @author uzovo
 * @version 1.0
 */
public class Land {
    private final Map<Vector2d, Tile> map;
    private Vector2d barnLocation;
    private Vector2d max;
    private Vector2d min;

    /**
     * This instantiates a new Land consisting of the specified tiles as well as the barn. The start configuration
     * should only have one barn.
     * @param startConfiguration The tiles and positions at the beginning of the game.
     */
    public Land(final Map<Vector2d, TileType> startConfiguration) {
        this.barnLocation = null;
        this.map = new HashMap<>();
        this.min = (Vector2d) startConfiguration.keySet().toArray()[0];
        this.max = (Vector2d) startConfiguration.keySet().toArray()[0];
        for (final Map.Entry<Vector2d, TileType> entry : startConfiguration.entrySet()) {
            this.setMinMax(entry.getKey());
            if(entry.getValue() == TileType.BARN) {
                if (this.barnLocation == null) {
                    this.barnLocation = entry.getKey();
                }else throw new IllegalArgumentException(ErrorMessage.TOO_MANY_BARNS.getMessage());
            }
            this.map.put(entry.getKey(),entry.getValue().getTile());
        }
        if(this.barnLocation == null) throw new IllegalArgumentException(ErrorMessage.NO_BARN.getMessage());
        if (this.map.get(this.barnLocation).getTiles() != TileType.BARN) throw new IllegalArgumentException("Specified barn" +
            "location is not a barn");
    }

    /**
     * This copies the given land with separate but equal tiles, note that the tiles on this land do not update
     * unless altered
     * @param land The land to be copied
     */
    public Land(final Land land) {
        this.map = new HashMap<>();
        for (final Map.Entry<Vector2d,Tile> entry: land.map.entrySet()) {
            this.map.put(entry.getKey(), entry.getValue().clone());
        }
        this.barnLocation = land.barnLocation;
        this.max = land.max;
        this.min = land.min;
    }

    /**
     * This places a new non barn tile on the land at the specified location.
     * New tiles can only be added above left or right of existing tiles, not on them.
     * @param tile The tile that needs to be placed
     * @param position The position the tile should be placed at
     * @throws GameException If the tile is not placeable or if the tile is a barn
     */


    public void placeTile(final Tile tile, final Vector2d position) throws GameException {
        if(!this.isPlaceable(position)) throw new GameException(ErrorMessage.TILE_NOT_PLACABLE);
        if(tile.getTiles() == TileType.BARN) throw new GameException(ErrorMessage.TOO_MANY_BARNS);
        this.map.put(position, tile);
        this.setMinMax(position);
    }

    private void setMinMax(final Vector2d vector2d) {
        if (this.max.x() < vector2d.x()) this.max = new Vector2d(vector2d.x(), this.max.y());
        if (this.max.y() < vector2d.y()) this.max = new Vector2d(this.max.x(), vector2d.y());
        if (this.min.x() > vector2d.x()) this.min = new Vector2d(vector2d.x(), this.min.y());
        if (this.min.y() > vector2d.y()) this.min = new Vector2d(this.min.x(), vector2d.y());
    }

    /**
     * This returns the barn of this land
     * @return the barn of this land
     */
    public Barn getBarn() {
        return (Barn) this.map.get(this.barnLocation);
    }

    /**
     * This returns the cultivatable tile that is located at these coordinates
     * @param location The location of the tile
     * @return The cultivatable tile at the location. Not a copy
     * @throws GameException If the tile at this location does not exist or is a barn
     */
    public CultivatableTile getCultivatableTile(final Vector2d location) throws GameException {
        if (!this.map.containsKey(location))
            throw new GameException(ErrorMessage.NO_TILE_WITH_THESE_COORDINATS);
        if (location.equals(this.barnLocation)) throw new GameException(ErrorMessage.TILE_IS_BARN);
        return (CultivatableTile) this.map.get(location);
    }


    private boolean isPlaceable(final Vector2d location) {
        if (this.map.containsKey(location))
            return false;
        if (this.map.get(new Vector2d(location.x() - 1, location.y())) != null) return true;
        if (this.map.get(new Vector2d(location.x() + 1, location.y())) != null) return true;
        return this.map.get(new Vector2d(location.x(), location.y() - 1)) != null;
    }

    /**
     * /**
     * This checks if a tile is placeable at the specified location. Tiles can only be placed left, right or above
     * existing tiles and not on them. If a tile is placeable at the specified location, the manhattan distance between
     * the new tile and the barn is returned
     * @param location The location of the tile that needs to be placed
     * @return The Manhattan distance between the location and the barn
     * @throws GameException If the tile is not placeable at this location
     */
    public int getManhattanDistanceForNewTile(final Vector2d location) throws GameException {
        if (!this.isPlaceable(location)) throw new GameException(ErrorMessage.TILE_NOT_PLACABLE);
        return location.calculateManhattanDistance(this.barnLocation);
    }

    /**
     * This returns the Number of vegetables that have grown on the cultivatable tiles since the last call to this
     * method
     * @return The number of vegetables that have grown since the last call to this method
     */
    public int getNumberOfVegetablesGrown() {
        int numberOfVegetablesGrown = 0;
        for (final Tile tile : this.map.values()) {
            if (tile.getTiles() != TileType.BARN) {
                numberOfVegetablesGrown += ((CultivatableTile) tile).getNumberOfVegetablesGrown();
            }
        }
        return numberOfVegetablesGrown;
    }

    /**
     * This returns if the food in the barn has spoiled since the last call to this method
     * @return True if the food has spoiled, otherwise false
     */
    public boolean hasFoodSpoiled() {
        return this.getBarn().hasFoodSpoiled();
    }

    @Override
    public String toString() {
        final StringBuilder resultStringBuilder = new StringBuilder();
        for (int j = this.max.y(); j >= this.min.y(); j--) {
            final StringBuilder[] stringBuilderForMultiLine = new StringBuilder[Tile.TO_STRING_SIZE.y()];
            for (int i = 0; i < stringBuilderForMultiLine.length; i++) {
                stringBuilderForMultiLine[i] = new StringBuilder();
            }
            if (this.map.containsKey(new Vector2d(this.min.x(), j))) {
                for (final StringBuilder stringBuilder : stringBuilderForMultiLine) {
                    stringBuilder.append(Main.COLUMN_SEPARATOR);
                }
            }
            else {
                for (final StringBuilder stringBuilder : stringBuilderForMultiLine) {
                    stringBuilder.append(Main.SPACE);
                }
            }
            for (int i = this.min.x(); i <= this.max.x(); i++) {
                if (this.map.containsKey(new Vector2d(i, j))) {
                    for (final StringBuilder stringBuilder : stringBuilderForMultiLine) {
                        stringBuilder.replace(
                                stringBuilder.length() - 1, stringBuilder.length(), Main.COLUMN_SEPARATOR);
                    }
                    final String tileString = this.map.get(new Vector2d(i, j)).toString();
                    final String[] multiLineTile = tileString.split(System.lineSeparator());
                    for (int k = 0; k < multiLineTile.length; k++) {
                        stringBuilderForMultiLine[k].append(multiLineTile[k]).append(Main.COLUMN_SEPARATOR);
                    }
                } else {
                    for (final StringBuilder stringBuilder : stringBuilderForMultiLine) {
                        stringBuilder.append(Main.SPACE.repeat
                                (Tile.TO_STRING_SIZE.x() + Main.COLUMN_SEPARATOR.length()));
                    }
                }
            }
            for (int i = 0; i < stringBuilderForMultiLine.length; i++) {
                resultStringBuilder.append(stringBuilderForMultiLine[i]).append(System.lineSeparator());
                stringBuilderForMultiLine[i] = new StringBuilder();
            }
        }
        resultStringBuilder.delete(resultStringBuilder.length() - System.lineSeparator().length(), resultStringBuilder.length());
        return resultStringBuilder.toString();
    }
    // TODO: 28.02.2023 reduce cognitive complexity
}

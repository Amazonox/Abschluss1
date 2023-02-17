package edu.kit.informatik.game.storages;

import edu.kit.informatik.game.elements.Barn;
import edu.kit.informatik.game.elements.CultivatableTile;
import edu.kit.informatik.game.elements.Tile;
import edu.kit.informatik.game.elements.Tiles;
import edu.kit.informatik.ui.ErrorMessage;
import edu.kit.informatik.ui.InvalidArgumentException;
import edu.kit.informatik.ui.Main;
import edu.kit.informatik.utils.Vector2d;

import java.util.HashMap;
import java.util.Map;

public class Land {
    private final Map<Vector2d, Tile> map;
    private final Vector2d barnLocation;
    private Vector2d max;
    private Vector2d min;

    public Land(final Map<Vector2d, Tile> startConfiguration, final Vector2d barnLocation) {
        this.map = startConfiguration;
        min = (Vector2d) startConfiguration.keySet().toArray()[0];
        max = (Vector2d) startConfiguration.keySet().toArray()[0];
        for (final Vector2d vector2d : this.map.keySet()) {
            this.setMinMax(vector2d);
        }
        this.barnLocation = barnLocation;
        if (this.map.get(barnLocation).getTiles() != Tiles.BARN) throw new IllegalArgumentException("Not a barn");
    }

    public Land(final Land land) {
        this.map = new HashMap<>();
        for (final Vector2d vector2d : land.map.keySet()) {
            this.map.put(vector2d, land.map.get(vector2d).clone());
        }
        this.barnLocation = land.barnLocation;
        this.max = land.max;
        this.min = land.min;
    }


    public void setTile(final Tile tile, final Vector2d vector2d) throws InvalidArgumentException {


        // money shenanigans
        this.map.put(vector2d, tile);
        this.setMinMax(vector2d);
    }

    private void setMinMax(final Vector2d vector2d) {
        if (this.max.x() < vector2d.x()) this.max = new Vector2d(vector2d.x(), this.max.y());
        if (this.max.y() < vector2d.y()) this.max = new Vector2d(this.max.x(), vector2d.y());
        if (this.min.x() > vector2d.x()) this.min = new Vector2d(vector2d.x(), this.min.y());
        if (this.min.y() > vector2d.y()) this.min = new Vector2d(this.min.x(), vector2d.y());
    }

    public Barn getBarn() {
        return (Barn) this.map.get(this.barnLocation);
    }

    public CultivatableTile getTile(final Vector2d location) throws InvalidArgumentException {
        if (!this.map.containsKey(location))
            throw new InvalidArgumentException(ErrorMessage.NO_TILE_WITH_THESE_COORDINATS);
        if (location.equals(this.barnLocation)) throw new InvalidArgumentException(ErrorMessage.TILE_IS_BARN);
        return (CultivatableTile) this.map.get(location);
    }

    public boolean isPlacable(final Vector2d location) {
        if (this.map.containsKey(location))
            return false;
        if (this.map.get(new Vector2d(location.x() - 1, location.y())) != null) return true;
        if (this.map.get(new Vector2d(location.x() + 1, location.y())) != null) return true;
        return this.map.get(new Vector2d(location.x(), location.y() - 1)) != null;
    }

    public int getManhattanDistanceForNewTile(final Vector2d location) throws InvalidArgumentException {
        if (!this.isPlacable(location)) throw new InvalidArgumentException(ErrorMessage.TILE_NOT_PLACABLE);
        return location.calculateManhattanDistance(this.barnLocation);
    }

    public int getNumberOfVegetablesGrown() {
        int numberOfVegetablesGrown = 0;
        for (final Tile tile : this.map.values()) {
            if (tile.getTiles() != Tiles.BARN) {
                numberOfVegetablesGrown += ((CultivatableTile) tile).getNumberOfVegetablesGrown();
            }
        }
        return numberOfVegetablesGrown;
    }

    public boolean hasFoodSpoiled() {
        return this.getBarn().hasFoodSpoiled();
    }

    @Override
    public String toString() {
        final StringBuilder resultStringBuilder = new StringBuilder();
        for (int j = this.max.y(); j >= this.min.y(); j--) {
            final StringBuilder[] stringBuilderForMultyLine = new StringBuilder[Tile.TO_STRING_SIZE.y()];
            for (int i = 0; i < stringBuilderForMultyLine.length; i++) {
                stringBuilderForMultyLine[i] = new StringBuilder();
            }
            if (this.map.containsKey(new Vector2d(this.min.x(), j))) {
                for (final StringBuilder stringBuilder : stringBuilderForMultyLine) {
                    stringBuilder.append(Main.COLUMN_SEPARATOR);
                }
            }
            else {
                for (final StringBuilder stringBuilder : stringBuilderForMultyLine) {
                    stringBuilder.append(Main.SPACE);
                }
            }
            for (int i = this.min.x(); i <= this.max.x(); i++) {
                if (this.map.containsKey(new Vector2d(i, j))) {
                    for (final StringBuilder stringBuilder : stringBuilderForMultyLine) {
                        stringBuilder.replace(
                                stringBuilder.length() - 1, stringBuilder.length(), Main.COLUMN_SEPARATOR);
                    }
                    final String tileString = this.map.get(new Vector2d(i, j)).toString();
                    final String[] multyLineTile = tileString.split(System.lineSeparator());
                    for (int k = 0; k < multyLineTile.length; k++) {
                        stringBuilderForMultyLine[k].append(multyLineTile[k]).append(Main.COLUMN_SEPARATOR);
                    }
                } else {
                    for (final StringBuilder stringBuilder : stringBuilderForMultyLine) {
                        stringBuilder.append(Main.SPACE.repeat
                                (Tile.TO_STRING_SIZE.x() + Main.COLUMN_SEPARATOR.length()));
                    }
                }
            }
            for (int i = 0; i < stringBuilderForMultyLine.length; i++) {
                resultStringBuilder.append(stringBuilderForMultyLine[i]).append(System.lineSeparator());
                stringBuilderForMultyLine[i] = new StringBuilder();
            }
        }
        resultStringBuilder.delete(resultStringBuilder.length() - System.lineSeparator().length(), resultStringBuilder.length());
        return resultStringBuilder.toString();
    }
}

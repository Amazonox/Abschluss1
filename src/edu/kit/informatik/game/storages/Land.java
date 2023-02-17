package edu.kit.informatik.game.storages;

import edu.kit.informatik.game.elements.Barn;
import edu.kit.informatik.game.elements.Tile;
import edu.kit.informatik.game.elements.CultivatableTile;
import edu.kit.informatik.game.elements.Tiles;
import edu.kit.informatik.ui.ErrorMessage;
import edu.kit.informatik.ui.InvalidArgumentException;
import edu.kit.informatik.ui.Main;
import edu.kit.informatik.utils.Vector2d;

import java.util.HashMap;
import java.util.Map;
import java.util.spi.LocaleNameProvider;

public class Land {
    private final Map<Vector2d,Tile> map;
    private Vector2d barnLocation;
    private Vector2d max = null;
    private Vector2d min = null;

    public Land(final Map<Vector2d,Tile> startConfiguration, final Vector2d barnLocation){
        this.map = startConfiguration;
        for (final Vector2d vector2d: this.map.keySet()){
            this.setMinMax(vector2d);
        }
        this.barnLocation = barnLocation;
        if(this.map.get(barnLocation).getTiles() != Tiles.BARN) throw new IllegalArgumentException("Not a barn");
    }

    public Land(Land land){
        this.map = new HashMap<>();
        for (Vector2d vector2d : land.map.keySet()){
            this.map.put(vector2d, land.map.get(vector2d).clone());
        }
        this.barnLocation = land.barnLocation;
        this.max = land.max;
        this.min = land.min;
    }


    public void setTile(final Tile tile, final Vector2d vector2d) throws InvalidArgumentException {


        // money shenanigans
        this.map.put(vector2d,tile);
        this.setMinMax(vector2d);
    }

    private void setMinMax(final Vector2d vector2d){
        if(this.max.x() < vector2d.x()) this.max = new Vector2d(vector2d.x(), this.max.y());
        if(this.max.y() < vector2d.y()) this.max = new Vector2d(this.max.x(), vector2d.y());
        if(this.min.x() > vector2d.x()) this.min = new Vector2d(vector2d.x(), this.min.y());
        if(this.min.y() > vector2d.y()) this.min = new Vector2d(this.min.x(), vector2d.y());
    }
    public Barn getBarn(){
        return (Barn)this.map.get(this.barnLocation);
    }

    public CultivatableTile getTile(Vector2d location) throws InvalidArgumentException {
        if(!this.map.containsKey(location)) throw new InvalidArgumentException(ErrorMessage.NO_TILE_WITH_THESE_COORDINATS);
        if(location.equals(this.barnLocation)) throw new InvalidArgumentException(ErrorMessage.TILE_IS_BARN);
        return (CultivatableTile) this.map.get(location);
    }

    public boolean isPlacable(Vector2d location){
        if(this.map.containsKey(location))
            return false;
        if(this.map.get(new Vector2d(location.x() - 1, location.y())) != null) return true;
        if(this.map.get(new Vector2d(location.x() + 1, location.y())) != null) return true;
        return this.map.get(new Vector2d(location.x(), location.y() - 1)) != null;
    }

    public int getManhattanDistanceForNewTile(Vector2d location) throws InvalidArgumentException {
        if(!this.isPlacable(location)) throw new InvalidArgumentException(ErrorMessage.TILE_NOT_PLACABLE);
        return location.calculateManhattanDistance(barnLocation);
    }

    public int getNumberOfVegetablesGrown(){
        int numberOfVegetablesGrown = 0;
        for (Tile tile : map.values()){
            if(tile.getTiles() != Tiles.BARN) {
                numberOfVegetablesGrown += ((CultivatableTile)tile).getNumberOfVegetablesGrown();
            }
        }
        return numberOfVegetablesGrown;
    }

    public boolean hasFoodSpoiled(){
        return this.getBarn().hasFoodSpoiled();
    }

    @Override
    public String toString() {
        StringBuilder resultStringBuilder = new StringBuilder();
        for (int j = max.y(); j >= min.y(); j--) {
            StringBuilder[] stringBuilderForMultyLine = new StringBuilder[Tile.TO_STRING_SIZE.y()];
            for (int i = 0; i < stringBuilderForMultyLine.length; i++) {
                stringBuilderForMultyLine[i] = new StringBuilder();
            }
            for (int i = min.x(); i <= max.x(); i++) {
                if(map.containsKey(new Vector2d(i,j))){
                    for (StringBuilder stringBuilder:stringBuilderForMultyLine) {
                        stringBuilder.append(Main.COLUMN_SEPARATOR);
                    }
                    String tileString = this.map.get(new Vector2d(i,j)).toString();
                    String[] multyLineTile = tileString.split(System.lineSeparator());
                    for (int k = 0; i<multyLineTile.length;i++){
                        stringBuilderForMultyLine[i].append(multyLineTile[i]);
                    }
                }else {
                    for (StringBuilder stringBuilder:stringBuilderForMultyLine){
                        stringBuilder.append(Main.SPACE.repeat
                            (Tile.TO_STRING_SIZE.x() + Main.WORD_NUMBER_SEPERATOR.length()));
                    }
                }
            }
            if(map.containsKey(new Vector2d(max.x(),j))){
                for (StringBuilder stringBuilder:stringBuilderForMultyLine) {
                    stringBuilder.append(Main.COLUMN_SEPARATOR);
                }
            }else{
                for (StringBuilder stringBuilder:stringBuilderForMultyLine){
                    stringBuilder.append(Main.SPACE.repeat
                        (Main.WORD_NUMBER_SEPERATOR.length()));
                }
            }
            for (int i = 0; i < stringBuilderForMultyLine.length; i++) {
                resultStringBuilder.append(stringBuilderForMultyLine[i]).append(System.lineSeparator());
                stringBuilderForMultyLine[i] = new StringBuilder();
            }
        }
        resultStringBuilder.delete(resultStringBuilder.length()-System.lineSeparator().length(),resultStringBuilder.length());
        return resultStringBuilder.toString();
    }
}

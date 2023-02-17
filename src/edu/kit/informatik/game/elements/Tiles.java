package edu.kit.informatik.game.elements;

import java.util.List;

public enum Tiles {
    BARN("B",1,-1,List.of()) {
        @Override
        public Tile getTile() {
            return new Barn(this);
        }
    },
    GARDEN("G",4,2,List.of(Vegetables.values())),
    FIELD("Fi",4,4,List.of(Vegetables.CARROT,Vegetables.SALAD,Vegetables.TOMATO)),
    LARGE_FIELD("LFi",2,8,List.of(Vegetables.CARROT,Vegetables.SALAD,Vegetables.TOMATO)),
    FOREST("Fo",2,4,List.of(Vegetables.CARROT,Vegetables.MUSHROOM)),
    LARGE_FOREST("LFo",1,8,List.of(Vegetables.CARROT,Vegetables.MUSHROOM))
    ;

    private final String abbreviation;
    private final int timesInGamePerPlayer;
    private final int capacity;
    private final List<Vegetables>  cultivatableVegetables;

    Tiles(final String abbreviation, final int timesInGamePerPlayer, final int capacity, final List<Vegetables> cultivatableVegetables) {
        this.abbreviation = abbreviation;
        this.timesInGamePerPlayer = timesInGamePerPlayer;
        this.capacity = capacity;
        this.cultivatableVegetables = cultivatableVegetables;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }

    public int getTimesInGamePerPlayer() {
        return this.timesInGamePerPlayer;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public List<Vegetables> getCultivatableVegetables() {
        return this.cultivatableVegetables;
    }

    public Tile getTile(){
        return new CultivatableTile(this);
    }
}

package edu.kit.informatik.game.elements;

import java.util.ArrayList;
import java.util.List;

public enum Tiles {
    BARN("B", "", 1, -1, List.of()) {
        @Override
        public Tile getTile() {
            return new Barn(this);
        }
    },
    GARDEN("G", "Garden", 4, 2, List.of(Vegetables.values())),
    FIELD("Fi", "Field", 4, 4, List.of(Vegetables.CARROT, Vegetables.SALAD, Vegetables.TOMATO)),
    LARGE_FIELD("LFi", "Large Field", 2, 8, List.of(Vegetables.CARROT, Vegetables.SALAD, Vegetables.TOMATO)),
    FOREST("Fo", "Forest", 2, 4, List.of(Vegetables.CARROT, Vegetables.MUSHROOM)),
    LARGE_FOREST("LFo", "Large Forest", 1, 8, List.of(Vegetables.CARROT, Vegetables.MUSHROOM));

    private final String abbreviation;
    private final String name;
    private final int timesInGamePerPlayer;
    private final int capacity;
    private final List<Vegetables> cultivatableVegetables;

    Tiles(final String abbreviation, final String name, final int timesInGamePerPlayer, final int capacity, final List<Vegetables> cultivatableVegetables) {
        this.abbreviation = abbreviation;
        this.name = name;
        this.timesInGamePerPlayer = timesInGamePerPlayer;
        this.capacity = capacity;
        this.cultivatableVegetables = cultivatableVegetables;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }

    public String getName() {
        return this.name;
    }

    public int getTimesInGamePerPlayer() {
        return this.timesInGamePerPlayer;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public List<Vegetables> getCultivatableVegetables() {
        return new ArrayList<>(this.cultivatableVegetables);
    }

    public Tile getTile() {
        return new CultivatableTile(this);
    }
}

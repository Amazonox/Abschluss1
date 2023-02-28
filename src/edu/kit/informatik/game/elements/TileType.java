package edu.kit.informatik.game.elements;

import java.util.ArrayList;
import java.util.List;

/**
 * These are all the types a tile can belong to, each tile has information about the name, the abbreviation,
 * how many times it is in game per player, the amount of vegetables it can hold and a list of vegetables cultivatable
 * on this tile.
 */
public enum TileType {
    /**
     * This is the barn. It is only once per player in game, has an infinite capacity and no vegetables are cultivatable
     * on it. The barn if the only tile type that is not designated as a cultivatable tile. It holds the players vegetables.
     */
    BARN("B", "Barn", 1, -1, List.of()) {
        @Override
        public Tile getTile() {
            return new Barn();
        }
    },
    /**
     * The garden can grow all vegetables in game
     */
    GARDEN("G", "Garden", 4, 2, List.of(Vegetables.values())),
    /**
     * The field can grow carrots, salads and tomatoes
     */
    FIELD("Fi", "Field", 4, 4, List.of(Vegetables.CARROT, Vegetables.SALAD, Vegetables.TOMATO)),
    /**
     * The large field can grow carrots, salads and tomatoes
     */
    LARGE_FIELD("LFi", "Large Field", 2, 8, List.of(Vegetables.CARROT, Vegetables.SALAD, Vegetables.TOMATO)),
    /**
     * The forest can grow carrots and mushrooms
     */
    FOREST("Fo", "Forest", 2, 4, List.of(Vegetables.CARROT, Vegetables.MUSHROOM)),
    /**
     * The large forest can grow carrots and mushrooms
     */
    LARGE_FOREST("LFo", "Large Forest", 1, 8, List.of(Vegetables.CARROT, Vegetables.MUSHROOM));

    private final String abbreviation;
    private final String name;
    private final int timesInGamePerPlayer;
    private final int capacity;
    private final List<Vegetables> cultivatableVegetables;

    TileType(final String abbreviation, final String name, final int timesInGamePerPlayer, final int capacity, final List<Vegetables> cultivatableVegetables) {
        this.abbreviation = abbreviation;
        this.name = name;
        this.timesInGamePerPlayer = timesInGamePerPlayer;
        this.capacity = capacity;
        this.cultivatableVegetables = cultivatableVegetables;
    }

    /**
     * This returns the abbreviation of a tile. The abbreviation is normally consisting of the first and sometimes second
     * letter of the words making up the name of this tile type
     * @return The abbreviation of this tile
     */
    public String getAbbreviation() {
        return this.abbreviation;
    }

    /**
     * This returns the name of the tile. "A rose by any other name would smell as sweet" William Shakespeare
     * @return The name of this tile
     */
    public String getName() {
        return this.name;
    }

    /**
     * This returns how many times this tile is in game per player
     * @return The number of this tile type in game per player
     */
    public int getTimesInGamePerPlayer() {
        return this.timesInGamePerPlayer;
    }

    /**
     * This returns the amount of vegetables that can be stored on this tile. -1 indicates infinity
     * @return The amounts of vegetables that can be stored on this tile
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * This returns a list of vegetables that can be planted on this tile type
     * @return The vegetables that can be planted on tiles of this type
     */
    public List<Vegetables> getCultivatableVegetables() {
        return new ArrayList<>(this.cultivatableVegetables);
    }

    /**
     * This instantiates a new tile of this type.
     * @return A new tile of this type.
     */
    public Tile getTile() {
        return new CultivatableTile(this);
    }
}

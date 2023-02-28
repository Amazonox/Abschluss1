package edu.kit.informatik.ui;

public enum ErrorMessage {
    INSUFFICIENT_MONEY("Player does not have enough money"),
    TILE_NOT_PLACABLE("Tile not placable"),
    TILE_IS_BARN("Barn is not cultivatable"),
    VEGETABLE_NOT_CULTIVATABLE_ON_THIS_TILE("not a valid vegetable for this tile"),
    TILE_ALREADY_HAS_VEGETABLE("No more than one vegetable per tile"),
    BELOW_ZERO_INTEGER("Amount should not be below 0"),
    NOT_ENOUGH_VEGETABLES("The barn doesn't hold enough vegetables"),
    NO_TILE_WITH_THESE_COORDINATS("there is no tile here"),
    NO_MORE_TILES_IN_GAME("there are no more tiles available"),
    TOO_MANY_ARGUMENTS("This program takes no arguments"),
    NOT_A_POSITIVE_INTEGER("The input should be a positive integer"),
    NOT_A_INTEGER("The input should be a integer"),
    NOT_A_NAME("Names should be of the form [A-Za-z]+"),
    NO_VEGETABLE_ON_TILE("There are no vegetables on this tile"),
    NOT_ENOUGH_VEGETABLES_ON_TILE("There are not enough vegetables on this tile"),
    NOT_A_COMMAND("This string doesn't fulfill all the requirements for an command,"
             + "either the command or the arguments are faulted"),
    TOO_MANY_BARNS("Each land should only have one barn"),
    NO_BARN("Each land should have a barn");


    private final String message;

    ErrorMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}

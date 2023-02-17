package edu.kit.informatik.game.elements;
import edu.kit.informatik.ui.ErrorMessage;
import edu.kit.informatik.ui.InvalidArgumentException;
import edu.kit.informatik.ui.Main;
import edu.kit.informatik.utils.Counter;

public class CultivatableTile implements Tile{
    private Vegetables currentlyPlantedVegetables;
    private Counter counter;
    private Tiles tiles;
    private int numberOfVegetables;
    private int numberOfVegetablesGrown;

    public CultivatableTile(final Tiles tiles) {
        this.counter = null;
        this.numberOfVegetables = 0;
        this.tiles = tiles;
        this.numberOfVegetablesGrown = 0;
    }

    /**
     * not updated clone
     * @param cultivatableTile
     */
    public CultivatableTile(final CultivatableTile cultivatableTile){
        this.counter = new Counter(cultivatableTile.counter);
        this.numberOfVegetables = cultivatableTile.numberOfVegetables;
        this.tiles = cultivatableTile.tiles;
        this.currentlyPlantedVegetables = cultivatableTile.currentlyPlantedVegetables;
        this.numberOfVegetablesGrown = cultivatableTile.numberOfVegetablesGrown;
    }

    public void plantVegetable(final Vegetables vegetable) throws InvalidArgumentException {
        if(this.currentlyPlantedVegetables != null) throw new InvalidArgumentException(
            ErrorMessage.TILE_ALREADY_HAS_VEGETABLE
        );
        if(this.tiles.getCultivatableVegetables().contains(vegetable))
            throw new InvalidArgumentException(ErrorMessage.VEGETABLE_NOT_CULTIVATABLE_ON_THIS_TILE);
        this.currentlyPlantedVegetables = vegetable;
        this.numberOfVegetables++;
        this.counter = new Counter(this.currentlyPlantedVegetables.getDaysToGrow(),
            ()->{
                this.numberOfVegetablesGrown
                    = Math.max(this.tiles.getCapacity(), this.numberOfVegetables *2) - this.numberOfVegetables;
                this.numberOfVegetables = Math.max(this.tiles.getCapacity(), this.numberOfVegetables *2);
                return this.numberOfVegetables != this.tiles.getCapacity();
            });
    }

    public Vegetables removeVegetables(final int amount) throws InvalidArgumentException {
        if(this.numberOfVegetables == 0) throw new InvalidArgumentException(ErrorMessage.NO_VEGETABLE_ON_TILE);
        if(this.numberOfVegetables < amount) throw new InvalidArgumentException(ErrorMessage.NOT_ENOUGH_VEGETABLES);
        this.numberOfVegetables -= amount;
        final Vegetables plantedVegetable = this.currentlyPlantedVegetables;
        if(this.numberOfVegetables == 0){
            this.currentlyPlantedVegetables = null;
            this.counter.removeCounter();
            this.counter = null;
        }else {
            if(this.counter.isFinished()){
                this.counter.restartCounter();
            }
        }
        return plantedVegetable;
    }

    public int getNumberOfVegetablesGrown(){
        final int vegetablesGrown = this.numberOfVegetablesGrown;
        this.numberOfVegetablesGrown = 0;
        return vegetablesGrown;
    }

    @Override
    public Tiles getTiles() {
        return this.tiles;
    }

    @Override
    public Tile clone() {
        return new CultivatableTile(this);
    }

    @Override
    public String toString() {
        String counter = this.counter == null || this.counter.isFinished()
            ?"*":   Integer.toString(this.counter.getRoundsToEnd());
        String format = switch (this.tiles.getAbbreviation().length()){
            case 1 -> " %s %s ";
            case 2 -> " %s %s";
            case 3 -> "%s %s";
            default -> throw new IllegalStateException("Unexpected value: " + this.tiles.getAbbreviation().length());
        };
        StringBuilder result = new StringBuilder();
        result.append(format.formatted(this.tiles.getAbbreviation(),counter)).append(System.lineSeparator());
        String vegetable = currentlyPlantedVegetables == null? Main.SPACE : currentlyPlantedVegetables.getAbbreviation();
        result.append(String.format("  %s  ",vegetable)).append(System.lineSeparator());
        result.append(String.format(" %s/%s",numberOfVegetables,tiles.getCapacity()));
        return result.toString();
    }
}

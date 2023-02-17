import edu.kit.informatik.game.elements.Tiles;
import edu.kit.informatik.game.elements.Vegetables;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final List<Tiles> tiles = new ArrayList<>();
        for (final Tiles tile : Tiles.values()) {
            for (int i = 0; i < tile.getTimesInGamePerPlayer() * 1; i++) {
                tiles.add(tile);
            }
        }
        System.out.println("plant -1 0 carrot".matches("plant (%s) (%s) (%s)".formatted(edu.kit.informatik.ui.Main.INTEGER_REGEX, edu.kit.informatik.ui.Main.INTEGER_REGEX, Vegetables.getSingularRegex())));
        System.out.println("-1".matches(edu.kit.informatik.ui.Main.INTEGER_REGEX));
        System.out.println("0".matches(edu.kit.informatik.ui.Main.INTEGER_REGEX));
        System.out.println("plant %s %s (%s)".formatted(edu.kit.informatik.ui.Main.INTEGER_REGEX, edu.kit.informatik.ui.Main.INTEGER_REGEX, Vegetables.getSingularRegex()));
    }
}

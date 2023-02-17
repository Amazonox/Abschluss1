package edu.kit.informatik.game.actions.results;

import java.util.Objects;

public class PlantResult extends ActionResult {
    @Override
    public String toString() {
        return Objects.equals(super.toString(), "")
                ? null : super.toString().replaceAll(System.lineSeparator(), "");
    }
}

package edu.kit.informatik.game.actions.results;

import java.util.Objects;

/**
 * this is a class for the plant result. As the plant action needs no information to be carried, its purpose is to
 * ensure, that no lines are printed as long as there is no turn information, that needs to be conveyed
 *
 * @author uzovo
 * @version 1.0
 */
public class PlantResult extends ActionResult {
    @Override
    public String toString() {
        return Objects.equals(super.toString(), "")
                ? null : super.toString().replaceAll(System.lineSeparator(), "");
    }
}

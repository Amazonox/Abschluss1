package edu.kit.informatik.game.interfaces;

/**
 * This is an interface for objects only implementing the on countdown method to be used by the counter class.
 */
public interface OnCountdown {
    /**
     * This method gets executed when the counter this countdown is attached to reaches zero. It returns whether
     * th counter should be restarted.
     * @return True if the counter should be restarted, otherwise false
     */
    boolean onCountdown();
}

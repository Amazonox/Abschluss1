package edu.kit.informatik.utils;

import edu.kit.informatik.game.interfaces.OnCountdown;

import java.util.ArrayList;
import java.util.List;

public class Counter {
    private static final List<Counter> counters = new ArrayList<>();
    private final int totalRounds;
    private final OnCountdown onCountdown;
    private boolean finished;
    private int roundsToEnd;

    /**
     * use
     *
     * @param rounds
     * @param onCountdown
     */
    public Counter(final int rounds, final OnCountdown onCountdown) {
        if (rounds <= 0) throw new IllegalArgumentException("Rounds needs to be greater than 0");
        this.onCountdown = onCountdown;
        this.roundsToEnd = rounds;
        this.totalRounds = rounds;
        this.finished = false;
        counters.add(this);
    }

    /**
     * cloned counters do not update
     *
     * @param counter
     */
    public Counter(final Counter counter) {
        this.onCountdown = counter.onCountdown;
        this.roundsToEnd = counter.roundsToEnd;
        this.totalRounds = counter.totalRounds;
        this.finished = counter.finished;
    }

    public static void updateCounters() {
        final List<Counter> toRemove = new ArrayList<>();
        //for because modification of array during foreach is false
        for (final Counter counter : counters) {
            counter.roundsToEnd--;
            if (counter.roundsToEnd == 0) {
                if (counter.onCountdown.onCountdown()) {
                    counter.roundsToEnd = counter.totalRounds;
                } else {
                    toRemove.add(counter);
                    counter.finished = true;
                }
            }
        }
        counters.removeAll(toRemove);
    }

    public boolean isFinished() {
        return this.finished;
    }

    public int getRoundsToEnd() {
        return this.roundsToEnd;
    }

    public void removeCounter() {
        this.finished = true;
        counters.remove(this);
    }

    public void restartCounter() {
        this.roundsToEnd = this.totalRounds;
        this.finished = false;
        counters.add(this);
    }
}

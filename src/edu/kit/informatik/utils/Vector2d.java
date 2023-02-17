package edu.kit.informatik.utils;

public record Vector2d(int x, int y) {
    public int calculateManhattanDistance(final Vector2d vector2d) {
        return Math.abs(this.x - vector2d.x) + Math.abs(this.y - vector2d.y);
    }

    public Vector2d(String x, String y) {
        this(Integer.parseInt(x), Integer.parseInt(y));
    }
}

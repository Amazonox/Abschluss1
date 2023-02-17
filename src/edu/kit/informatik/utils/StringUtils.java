package edu.kit.informatik.utils;

public final class StringUtils {
    private StringUtils() {}

    public static String indentCorrectly(final int totalLength, final String front, final String back, final String space) {
        final StringBuilder lineStringBuilder = new StringBuilder(front);
        while (lineStringBuilder.length() + back.length() < totalLength) {
            lineStringBuilder.append(space);
        }
        lineStringBuilder.append(back);
        return lineStringBuilder.toString();
    }
}

package tui;

import java.util.Objects;

/**
 * Utility class for consistent and formatted terminal output.
 * Provides distinct display styles for titles, sections, body text,
 * feedback messages, menu entries and tabular data.
 */
public final class Displayer {
    private static final int LINE_WIDTH = 60;
    private static final char HEAVY_CHAR = '═';
    private static final char LIGHT_CHAR = '─';
    private static final String ERROR_PREFIX = "  [!] ";
    private static final String SUCCESS_PREFIX = "  [✓] ";
    private static final String INFO_PREFIX = "  [i] ";

    private Displayer() {
    }

    /**
     * Prints a top-level title, centered between two heavy separator lines.
     * Use once per screen, at the top.
     * 
     * @param text non-null title text
     */
    public static void title(String text) {
        Objects.requireNonNull(text);

        String heavy = String.valueOf(HEAVY_CHAR).repeat(LINE_WIDTH);
        IO.println();
        IO.println(heavy);
        IO.println(center(text.toUpperCase(), LINE_WIDTH));
        IO.println(heavy);
        IO.println();
    }

    /**
     * Prints a top-level title, centered between two heavy separator lines, and
     * with a sub-title.
     * Use once per screen, at the top.
     * 
     * @param text non-null title text
     */
    public static void title(String text, String subtitle, String alignment) {
        Objects.requireNonNull(text);
        Objects.requireNonNull(subtitle);
        Objects.requireNonNull(alignment);

        String heavy = String.valueOf(HEAVY_CHAR).repeat(LINE_WIDTH);
        IO.println();
        IO.println(heavy);
        IO.println(center(text.toUpperCase(), LINE_WIDTH));
        String formatted_subtitle;
        formatted_subtitle = switch (alignment) {
            case "center" -> center(subtitle, LINE_WIDTH);
            case "right" -> right(subtitle, LINE_WIDTH);
            case "left" -> subtitle;
            default -> center(text, LINE_WIDTH);
        };
        IO.println(formatted_subtitle);
        IO.println(heavy);
        IO.println();
    }

    /**
     * Prints a section header with a light separator line below.
     * Use to divide logical areas within a screen.
     * 
     * @param text non-null section name
     */
    public static void section(String text) {
        Objects.requireNonNull(text);

        IO.println();
        IO.println("  " + text);
        IO.println("  " + String.valueOf(LIGHT_CHAR).repeat(text.length()));
    }

    /**
     * Prints plain indented body text.
     * 
     * @param text non-null content
     */
    public static void body(String text) {
        Objects.requireNonNull(text);

        IO.println("  " + text);
    }

    /**
     * Prints a blank line for vertical spacing.
     */
    public static void blank() {
        IO.println();
    }

    /**
     * Prints n blank lines for vertical spacing
     * 
     * @param n number of lines to print
     */
    public static void blank(int n) {
        for (int i = 0; i < n; i++) {
            IO.println();
        }
    }

    /**
     * Prints a full-width light separator line.
     */
    public static void separator() {
        IO.println("  " + String.valueOf(LIGHT_CHAR).repeat(LINE_WIDTH - 2));
    }

    /**
     * Prints an error message with a visual prefix.
     * 
     * @param text error description, not null
     */
    public static void error(String text) {
        Objects.requireNonNull(text);

        IO.println(ERROR_PREFIX + text);
    }

    /**
     * Prints a success confirmation with a visual prefix.
     * 
     * @param text confirmation message, not null
     */
    public static void success(String text) {
        Objects.requireNonNull(text);

        IO.println(SUCCESS_PREFIX + text);
    }

    /**
     * Prints an informational notice with a visual prefix.
     * 
     * @param text notice content, not null
     */
    public static void info(String text) {
        Objects.requireNonNull(text);

        IO.println(INFO_PREFIX + text);
    }

    /**
     * Prints a numbered menu entry, retrieving its label from the i18n bundle.
     * Index starts from 1.
     * 
     * @param index    display number, must be positive
     * @param labelKey i18n key for the label, not null
     */
    public static void menuEntry(int index, String labelKey) {
        if (index < 1)
            throw new IllegalArgumentException("index must be positive");
        Objects.requireNonNull(labelKey);

        System.out.printf("  %2d.  %s%n", index, Message.get(labelKey));
    }

    /**
     * Prints a numbered meny entry, without i18n. Index starts from 1.
     * 
     * @param index display number, must be positive
     * @param str   non-null string to display
     */
    public static void rawMenuEntry(int index, String str) {
        if (index < 1)
            throw new IllegalArgumentException("index must be positive");
        Objects.requireNonNull(str);

        System.out.printf("  %2d.  %s%n", index, str);
    }

    /**
     * Prints a two-column key-value row, used for projection and booking details.
     * The key is right-padded to align values consistently.
     * 
     * @param key   field label, not null
     * @param value field value, not null
     */
    public static void field(String key, String value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);

        System.out.printf("  %-20s %s%n", key + ":", value);
    }

    private static String center(String text, int width) {
        if (text.length() >= width)
            return text;

        int padding = (width - text.length()) / 2;

        return " ".repeat(padding) + text;
    }

    private static String right(String text, int width) {
        if (text.length() >= width)
            return text;

        int padding = width - text.length();

        return " ".repeat(padding) + text;
    }
}
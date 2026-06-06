package tui;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * Singleton managing all terminal input for the application.
 * Provides numeric menu selection and free text input.
 */
public enum Input {
    INSTANCE;

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Displays the provided entries and returns the chosen one,
     * repeatedly prompting until a valid number is entered.
     * 
     * @param entries non-null, non-empty list of menu entries
     * @return the selected MenuEntry
     */
    public static MenuEntry choose(List<MenuEntry> entries) {
        Objects.requireNonNull(entries);

        if (entries.isEmpty())
            throw new IllegalArgumentException("entries cannot be empty");

        while (true) {
            IntStream.range(0, entries.size())
                    .forEach(i -> Displayer.menuEntry(i + 1, entries.get(i).labelKey()));

            String raw = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(raw);

                if (choice >= 1 && choice <= entries.size())
                    return entries.get(choice - 1);
                Displayer.error( Message.get("error.input.outOfRange.specific", 1, entries.size()));
            } catch (NumberFormatException e) {
                Displayer.error(Message.get("error.input.NaN"));
            }
        }
    }

    /**
     * Prompts the user and returns a non-blank string.
     * 
     * @param promptKey prompt message
     * @return trimmed non-blank user input
     * @see #readSecureLine(String)
     */
    public static String readLine(String promptKey) {
        Objects.requireNonNull(promptKey);

        while (true) {
            Displayer.body(promptKey);

            String line = scanner.nextLine().trim();

            if (!line.isBlank())
                return line;

            Displayer.error(Message.get("error.input.blank"));
        }
    }

    /**
     * Secure way of reading a string from terminal.
     * 
     * @param promptKey prompt message
     * @return secured string
     * @see #readLine(String)
     */
    public static String readSecureLine(String promptKey) {
        Objects.requireNonNull(promptKey);

        while (true) {
            Displayer.body(promptKey);

            char[] charBuff = System.console().readPassword();
            String str = new String(charBuff);
            if (!str.isEmpty())
                return str;

            Displayer.error(Message.get("error.input.blank"));
        }
    }

    /**
     * Prompts the user and returns an integer within the given range.
     * 
     * @param promptKey i18n key for the prompt
     * @param min       minimum accepted value inclusive
     * @param max       maximum accepted value inclusive
     * @return integer within [min, max]
     */
    public static int readInt(String promptKey, int min, int max) {
        Objects.requireNonNull(promptKey);

        while (true) {
            String raw = readLine(promptKey);

            try {
                int value = Integer.parseInt(raw);
                if (value >= min && value <= max)
                    return value;
                Displayer.error(Message.get("error.input.out.of.range", min, max));
            } catch (NumberFormatException e) {
                Displayer.error(Message.get("error.input.not.a.number"));
            }
        }
    }

    /**
     * Expects any input to continue.
     */
    public static void awaitInput() {
        scanner.nextLine();
    }

    public static void close() {
        scanner.close();
    }
}
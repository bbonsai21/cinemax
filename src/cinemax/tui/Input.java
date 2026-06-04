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

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Displays the provided entries and returns the chosen one,
     * repeatedly prompting until a valid number is entered.
     * 
     * @param entries non-null, non-empty list of menu entries
     * @return the selected MenuEntry
     */
    public MenuEntry choose(List<MenuEntry> entries) {
        Objects.requireNonNull(entries);
        if (entries.isEmpty())
            throw new IllegalArgumentException("entries cannot be empty");

        while (true) {
            IntStream.range(0, entries.size())
                    .forEach(i -> Displayer.rawMenuEntry(i + 1, entries.get(i).labelKey()));

            String raw = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(raw);
                if (choice >= 1 && choice <= entries.size())
                    return entries.get(choice - 1);
                Displayer.error("Input is out of range! Must be between 1 and " + (entries.size()) + ".");
            } catch (NumberFormatException e) {
                Displayer.error("Input must be a number!");
            }
        }
    }

    /**
     * Prompts the user and returns a non-blank string.
     * 
     * @param promptKey i18n key for the prompt message
     * @return trimmed non-blank user input
     */
    public String readLine(String promptKey) {
        Objects.requireNonNull(promptKey);
        while (true) {
            Displayer.body(Message.get(promptKey));
            String line = scanner.nextLine().trim();
            if (!line.isBlank())
                return line;
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
    public int readInt(String promptKey, int min, int max) {
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

    public void close() {
        scanner.close();
    }
}
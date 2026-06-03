package tui;

/**
 * Records a menu entry for a given user.
 * @param key string denoting the action key for Message to retrieve
 * @param action the action itself that will be executed inside of the menu class
 */
public record MenuEntry( String key, Runnable action ) {}

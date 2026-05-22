package model.user;

/**
 * User is the superclass of classes meant to represent users in memory, accessing the platform.
 * @see AuthenticatedUser
 */
public sealed abstract class User permits Guest, AuthenticatedUser {
}
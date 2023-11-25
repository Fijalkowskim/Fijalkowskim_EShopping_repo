package fijalkowskim_eshopping.model;

/**
 * Enum representing all possible exceptions thrown while unit testing model classes.
 */
public enum ExpectedException {
    NONE,
    ITEM_NOT_IN_STOCK,
    NOT_ENOUGH_MONEY,
    ILLEGAL_ARGUMENT,
    ITEM_ALREADY_IN_DATABASE,
    ITEM_NOT_IN_DATABASE,
}

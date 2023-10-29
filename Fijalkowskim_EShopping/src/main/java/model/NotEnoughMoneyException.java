package model;
/**
 * Exception thrown while trying to buy an item and not having enough money.
 * @author Fijalkowskim
 * @version 1.0
 */
public class NotEnoughMoneyException extends Exception{
    /**
     * Sets error message.
     * @param errorMessage Error message.
     */
    public NotEnoughMoneyException(String errorMessage) {
        super(errorMessage);
    }
}

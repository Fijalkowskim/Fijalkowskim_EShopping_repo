package model;
/**
 * Exception thrown while trying to buy an item and not having enough money.
 * @author Fijalkowskim
 * @version %I%, %G%
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

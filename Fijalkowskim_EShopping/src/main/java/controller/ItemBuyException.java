package controller;
/**
 * Custom exception that can be thrown while trying to buy an item.
 * @author Fijalkowskim
 * @version %I%, %G%
 */
public class ItemBuyException extends Exception{
    /**
     * Sets error message.
     * @param errorMessage Error message.
     */
    public ItemBuyException(String errorMessage) {
        super(errorMessage);
    }
}

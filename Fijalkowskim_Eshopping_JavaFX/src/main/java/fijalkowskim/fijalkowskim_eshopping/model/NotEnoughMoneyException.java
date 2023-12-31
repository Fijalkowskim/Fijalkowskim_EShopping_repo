package fijalkowskim.fijalkowskim_eshopping.model;
/**
 * Exception thrown while trying to buy an item and not having enough money.
 * @author Fijalkowskim
 * @version 2.0
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

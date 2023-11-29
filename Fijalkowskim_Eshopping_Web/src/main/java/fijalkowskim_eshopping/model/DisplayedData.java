package fijalkowskim_eshopping.model;

/**
 * Represents data to be displayed, including cash amount, shop item container, and exception type.
 *
 * @author Fijalkowskim
 * @version 2.0
 */
public class DisplayedData {
    private float cash;
    private ShopItemContainer shopItemContainer;
    private ExceptionType exceptionType;

    /**
     * Initializes displayed data with the provided values.
     *
     * @param cash           The amount of cash to be displayed.
     * @param firstShopItem  The initial shop item container to be displayed.
     * @param exceptionType  The type of exception to be displayed.
     */
    public DisplayedData(float cash, ShopItemContainer firstShopItem, ExceptionType exceptionType) {
        this.cash = cash;
        this.shopItemContainer = firstShopItem;
        this.exceptionType = exceptionType;
    }

    /**
     * Gets the cash amount.
     *
     * @return The amount of cash.
     */
    public float getCash() {
        return cash;
    }

    /**
     * Gets the shop item container.
     *
     * @return The shop item container.
     */
    public ShopItemContainer getShopItemContainer() {
        return shopItemContainer;
    }

    /**
     * Sets the cash amount.
     *
     * @param cash The amount of cash.
     */
    public void setCash(float cash) {
        this.cash = cash;
    }

    /**
     * Sets the shop item container.
     *
     * @param shopItemContainer The shop item container.
     */
    public void setShopItemContainer(ShopItemContainer shopItemContainer) {
        this.shopItemContainer = shopItemContainer;
    }

    /**
     * Gets the type of exception.
     *
     * @return The type of exception.
     */
    public ExceptionType getExceptionType() {
        return exceptionType;
    }

    /**
     * Sets the type of exception.
     *
     * @param exceptionType The type of exception.
     */
    public void setExceptionType(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }
}


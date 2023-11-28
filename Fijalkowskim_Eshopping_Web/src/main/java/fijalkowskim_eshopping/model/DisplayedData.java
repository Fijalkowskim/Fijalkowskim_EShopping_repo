package fijalkowskim_eshopping.model;

public class DisplayedData {
    private float cash;
    private ShopItemContainer shopItemContainer;
    private ExceptionType exceptionType;

    public DisplayedData(float cash, ShopItemContainer firstShopItem, ExceptionType exceptionType) {
        this.cash = cash;
        this.shopItemContainer = firstShopItem;
        this.exceptionType = exceptionType;
    }

    public float getCash() {
        return cash;
    }

    public ShopItemContainer getShopItemContainer() {
        return shopItemContainer;
    }

    public void setCash(float cash) {
        this.cash = cash;
    }

    public void setShopItemContainer(ShopItemContainer shopItemContainer) {
        this.shopItemContainer = shopItemContainer;
    }
    public ExceptionType getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

}

package fijalkowskim_eshopping.model;

public class DisplayedData {
    private float cash;
    private ShopItemContainer shopItemContainer;

    public DisplayedData(float cash, ShopItemContainer firstShopItem) {
        this.cash = cash;
        this.shopItemContainer = firstShopItem;
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
}

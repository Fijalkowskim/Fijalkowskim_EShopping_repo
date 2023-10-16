package Model;

public class ShopItemContainer {
    public ShopItem getShopItem() {
        return shopItem;
    }

    public int getCount() {
        return count;
    }
    public void setCount(int count){
        this.count = count;
    }

    ShopItem shopItem;
    int count;

    public ShopItemContainer(ShopItem shopItem, int count) {
        this.shopItem = shopItem;
        this.count = count;
    }
}

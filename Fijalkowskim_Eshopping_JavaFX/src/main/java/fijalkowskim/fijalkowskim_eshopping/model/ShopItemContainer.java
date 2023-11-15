package fijalkowskim.fijalkowskim_eshopping.model;
/**
 * Container for items that can also hold their amount available in shop.
 * Usage of this class allows items to be a separate entity (item can exist without their connection to the shop and its available amount).
 * @author Fijalkowskim
 * @version 2.0
 */
public class ShopItemContainer {
    ShopItem shopItem;
    int count;
    /**
     * @return Item held in this container.
     */
    public ShopItem getShopItem() {
        return shopItem;
    }
    /**
     * @return Amount of held item available in the shop.
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets amount of item in store.
     * @param count Amount of available items.
     */
    public void setCount(int count){
        this.count = Math.max(count, 0);
    }

    /**
     * Sets shop item
     * @param item The item
     */
    public void setShopItem(ShopItem item){
        this.shopItem = item;
    }
    /**
     * Sets container's item and it's amount.
     * @param shopItem Item held.
     * @param count Amount of the item.
     */
    public ShopItemContainer(ShopItem shopItem, int count) {
        this.shopItem = shopItem;
        setCount(count);
    }
}

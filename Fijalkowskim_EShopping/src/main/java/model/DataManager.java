package model;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages all the data such as shop items or user data (cash).
 * @author Fijalkowskim
 * @version %I%, %G%
 */
public class DataManager {
    /**
     * Represents all possible results of trying to buy an item from the shop.
     */
    public enum ItemBuyResult {BOUGHT, NOT_ENOUGH_MONEY, ITEM_NOT_IN_STOCK}
    ShopStock shopStock;
    UserData userData;
    /**
     * @return Stock of the shop
     */
    public ShopStock getShopStock(){return shopStock;}
    /**
     * @return User data
     */
    public UserData getUserData() {return userData;}

    /**
     * @param itemIndex Index of wanted item
     * @return Item with given index or null if index is incorrect
     */
    public ShopItemContainer getCurrentItem(int itemIndex)
    {
        if(itemIndex >= shopStock.getItemsInStock().size() || itemIndex < 0) return null;
        return shopStock.getItemsInStock().get(itemIndex);
    }

    /**
     * Checks if buying an item is possible. If it is
     * @param itemIndex Index of an item
     * @return Appropriate ItemBuyResult
     */
    public ItemBuyResult TryToBuyItem(int itemIndex)
    {
        if(!shopStock.IsItemAvailable(itemIndex)) return ItemBuyResult.ITEM_NOT_IN_STOCK;
        if(userData.getCash() < shopStock.getItemsInStock().get(itemIndex).getShopItem().getPrice()) return ItemBuyResult.NOT_ENOUGH_MONEY;

        shopStock.getItemsInStock().get(itemIndex).setCount(shopStock.getItemsInStock().get(itemIndex).getCount()-1);
        userData.setCash(userData.getCash() - shopStock.getItemsInStock().get(itemIndex).getShopItem().getPrice());
        return ItemBuyResult.BOUGHT;
    }

    /**
     * Initialises shop stock and user data (cash).
     */
    public DataManager() {

        InitShopStock();
        userData = new UserData(567.45f);
    }

    /**
     * Initialises shop stock by adding items and their amount
     */
    void InitShopStock()
    {
        List<ShopItem> availableItems = new ArrayList<>();
        availableItems.add(new ShopItem("Bike", 300f));
        availableItems.add(new ShopItem("SamsungTV", 2050.99f));
        availableItems.add(new ShopItem("Apple", 0.89f));

        List<ShopItemContainer> shopItemContainers = new ArrayList<>();
        shopItemContainers.add(new ShopItemContainer(availableItems.get(0), 6));
        shopItemContainers.add(new ShopItemContainer(availableItems.get(1), 12));
        shopItemContainers.add(new ShopItemContainer(availableItems.get(2), 2));

        shopStock = new ShopStock(shopItemContainers);
    }

    /**
     * Tries to add new item to stock only if it is not already there.
     * @param item Item
     * @param itemInStock Amount of this item
     * @return True if item was successfully added, false if it is already in stock.
     */
    public boolean TryAddingItemToStock(ShopItem item, int itemInStock)
    {
        if (shopStock.IsItemAddedToStock(item))
            return false;
        shopStock.AddItemToStock(item, itemInStock);
        return true;
    }

}

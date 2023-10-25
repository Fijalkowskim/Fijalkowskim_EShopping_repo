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
     * Buys item with given index. Throws an exception if it is not possible.
     * @param itemIndex Index of an item
     */
    public void BuyAnItem (int itemIndex)throws NotEnoughMoneyException, ItemNotInStockException {
        if (!shopStock.IsItemAvailable(itemIndex))
            throw new ItemNotInStockException("Item is not in stock");
        if (userData.getCash() < shopStock.getItemsInStock().get(itemIndex).getShopItem().getPrice())
            throw new NotEnoughMoneyException("Not enough money");
        shopStock.getItemsInStock().get(itemIndex).setCount(shopStock.getItemsInStock().get(itemIndex).getCount() - 1);
        userData.setCash(userData.getCash() - shopStock.getItemsInStock().get(itemIndex).getShopItem().getPrice());
    }
    /*public void BuyAnItem (ShopItem item)throws NotEnoughMoneyException, ItemNotInStockException {
        if (!shopStock.IsItemAvailable(item))
            throw new ItemNotInStockException("Item is not in stock");
        if (userData.getCash() < item.getPrice())
            throw new NotEnoughMoneyException("Not enough money");
        shopStock.getItemsInStock().get(itemIndex).setCount(shopStock.getItemsInStock().get(itemIndex).getCount() - 1);
        userData.setCash(userData.getCash() - shopStock.getItemsInStock().get(itemIndex).getShopItem().getPrice());
    }*/

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
        availableItems.add(new ShopItem(0,"Bike", 300f));
        availableItems.add(new ShopItem(1,"SamsungTV", 2050.99f));
        availableItems.add(new ShopItem(2,"Apple", 0.89f));

        List<ShopItemContainer> shopItemContainers = new ArrayList<>();
        shopStock = new ShopStock(shopItemContainers);

        TryAddingItemToStock(availableItems.get(0), 6);
        TryAddingItemToStock(availableItems.get(1), 12);
        TryAddingItemToStock(availableItems.get(2), 2);

    }

    /**
     * Tries to add new item to stock only if it is not already there.
     * @param item Item
     * @param itemInStock Amount of this item
     * @return True if item was successfully added, false if it is already in stock.
     */
    public boolean TryAddingItemToStock(ShopItem item, int itemInStock)
    {
        if(item == null || shopStock == null)
            return false;
        try {
            shopStock.AddItemToStock(item, itemInStock);
            return true;
        }
        catch(ItemAlreadyInStock ex)
        {
            return false;
        }
    }

}

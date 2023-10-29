package model;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages all the data such as shop items or user data (cash).
 * @author Fijalkowskim
 * @version 1.0
 */
public class DataManager {
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
     * Initialises shop stock and user data (cash to 0).
     */
    public DataManager() {
        InitShopStock();
        userData = new UserData(0);
    }
    /**
     * Initialises shop stock and user cash.
     * @param cash Cash of the user
     */
    public DataManager(float cash){
        InitShopStock();
        userData = new UserData(cash);
    }
    /**
     * @param itemIndex Index of wanted item
     * @return Item container with given index or null if index is incorrect
     */
    public ShopItemContainer GetItemContainerByIndex(int itemIndex) {
        if(itemIndex >= shopStock.getItemDatabase().size() || itemIndex < 0) return null;
        return shopStock.getItemDatabase().get(itemIndex);
    }
    /**
     * Buys item with given index. Throws an exception if it is not possible.
     * @param itemIndex Index of an item
     */
    public void BuyAnItem(int itemIndex) throws NotEnoughMoneyException, ItemNotInStockException {
        if (!shopStock.IsItemInStock(itemIndex))
            throw new ItemNotInStockException("Item is not in stock");
        if (userData.getCash() < shopStock.getItemDatabase().get(itemIndex).getShopItem().getPrice())
            throw new NotEnoughMoneyException("Not enough money");
        shopStock.getItemDatabase().get(itemIndex).setCount(shopStock.getItemDatabase().get(itemIndex).getCount() - 1);
        userData.setCash(userData.getCash() - shopStock.getItemDatabase().get(itemIndex).getShopItem().getPrice());
    }
    /**
     * Buys given item. Throws an exception if it is not possible.
     * @param item Given item.
     */
    public void BuyAnItem(ShopItem item) throws NotEnoughMoneyException, ItemNotInStockException, ItemNotInDatabaseException {
        if (!shopStock.IsItemInStock(item))
            throw new ItemNotInStockException("Item is not in stock");
        if (userData.getCash() < item.getPrice())
            throw new NotEnoughMoneyException("Not enough money");
        shopStock.GetItemContainerInDatabase(item).setCount(shopStock.GetItemContainerInDatabase(item).getCount() - 1);
        userData.setCash(userData.getCash() - item.getPrice());
    }

    /**
     * Creates new shop item that is not in database and automatically gives it unique ID.
     * @param name Name of the item.
     * @param price Price of the item.
     * @param description Description of the item.
     * @return Created shop item.
     */
    public ShopItem CreateNewShopItem(String name, float price, String description){
        int newID = 0;
        while(shopStock.IsItemInDatabase(newID))
            newID++;
        return new ShopItem(newID, name, price, description);
    }
    /**
     * Initialises shop stock by adding items and their amount
     */
    void InitShopStock() {
        shopStock = new ShopStock();

        try {
            AddItemToDatabase(CreateNewShopItem("Bike", 300f, "Small BMX for children."), 6);
            AddItemToDatabase(CreateNewShopItem("SamsungTV", 2050.99f, "75 inch Samsung TV."), 3);
            AddItemToDatabase(CreateNewShopItem("Apple", 0.89f, "Just an apple."), 21);
        }
        catch (ItemAlreadyInDatabaseException ex) {}
        catch (IllegalArgumentException ex) {}
    }

    /**
     * Tries to add new item to database only if it is not already there.
     * @param item Item
     * @param count Amount of this item
     */
    public void AddItemToDatabase(ShopItem item, int count) throws ItemAlreadyInDatabaseException, IllegalArgumentException {
        if(item == null || shopStock == null)
            throw new IllegalArgumentException("Parameter is null");
        shopStock.AddNewItemToDatabase(item, count);
    }

}

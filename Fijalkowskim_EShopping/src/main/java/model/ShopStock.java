package model;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents stock of the shop with items and their available amount.
 * @author Fijalkowskim
 * @version 1.0
 */
public class ShopStock {
    List<ShopItemContainer> itemDatabase;
    /**
     * @return List of all items and their amount in stock.
     */
    public List<ShopItemContainer> getItemDatabase() {
        return itemDatabase;
    }
    /**
     * @param itemIndex Index of wanted item
     * @return Item container with given index or null if index is incorrect
     */
    public ShopItemContainer GetItemContainerByIndex(int itemIndex) {
        if(itemIndex >= getItemDatabase().size() || itemIndex < 0) return null;
        return getItemDatabase().get(itemIndex);
    }
    /**
     * Adds given number of items to stock
     * @param item Item
     * @param count Number of items in stock
     */

    public void AddNewItemToDatabase(ShopItem item, int count) throws ItemAlreadyInDatabaseException, IllegalArgumentException
    {
        if(itemDatabase == null || item == null || count < 0)
            throw new IllegalArgumentException("Null argument");
        if(IsItemInDatabase(item))
            throw new ItemAlreadyInDatabaseException("Item is already in stock");
        itemDatabase.add(new ShopItemContainer(item, count));
    }
    /**
     * If the item is in the database, adds its amount.
     * @param item Item
     * @param count Number of items in stock to add
     */
    public void AddItemAmountToDatabase(ShopItem item, int count) throws ItemNotInDatabaseException, IllegalArgumentException
    {
        if(itemDatabase == null || item == null || count < 0)
            throw new IllegalArgumentException("Null argument");
        if(!IsItemInDatabase(item))
            throw new ItemNotInDatabaseException("Item is already in stock");
        GetItemContainerInDatabase(item).setCount(GetItemContainerInDatabase(item).getCount() + count);
    }

    /**
     * Initialises empty item database
     */
    public ShopStock() {this.itemDatabase = new ArrayList<ShopItemContainer>();}
    /**
     * Initialises items in database.
     * @param itemsInDatabase Items in database.
     */
    public ShopStock(List<ShopItemContainer> itemsInDatabase) {
        this.itemDatabase = itemsInDatabase;
    }
    /**
     * Checks if item with given list index is available.
     * @param itemIndex Index in the list of an item.
     * @return True if item is available or false when it is not.
     */
    public boolean IsItemInStock(int itemIndex)
    {
        return itemIndex < itemDatabase.size() && itemIndex >= 0 && itemDatabase.get(itemIndex).count > 0;
    }
    /**
     * Checks if given item is available.
     * @param item Searched item.
     * @return True if item is available or false when it is not.
     */
    public boolean IsItemInStock(ShopItem item)
    {
        if(item == null) return false;
        for(ShopItemContainer container : itemDatabase)
        {
            if(container.shopItem == item && container.count > 0)
                return true;
        }
        return false;
    }
    /**
     * Checks if item with given id is available.
     * @param itemID ID of the item.
     * @return True if item is available or false when it is not.
     */
    public boolean IsItemInStockByID(int itemID)
    {
        for(ShopItemContainer container : itemDatabase)
        {
            if(container.shopItem.getID() == itemID && container.count > 0)
                return true;
        }
        return false;
    }

    /**
     * If given item exists in database return its container.
     * @param item Given item.
     * @return Item container in database.
     */
    public ShopItemContainer GetItemContainerInDatabase(ShopItem item) throws ItemNotInDatabaseException{
        if(!IsItemInDatabase(item))
            throw new ItemNotInDatabaseException("Item is not in database");
        for (ShopItemContainer container : itemDatabase){
            if(container.shopItem == item)
                return container;
        }
        return null;
    }

    /**
     * Tries to get an index of given item in shopStock list.
     * @param item Searched item
     * @return Index of given item or -1 if item is not in stock.
     */
    public int TryToGetItemIndex(ShopItem item) throws ItemNotInDatabaseException{
        if(!IsItemInStock(item))
            throw new ItemNotInDatabaseException("Item is not in database");
        for (int i = 0; i < itemDatabase.size(); i++) {
            if(itemDatabase.get(i).shopItem == item)
                return i;
        }
        throw new ItemNotInDatabaseException("Item is not in database");
    }
    /**
     * Checks if item is in database.
     * @param item Searched item
     * @return True if item is in database, otherwise false.
     */
    public boolean IsItemInDatabase(ShopItem item){
        if(item == null)
            return false;
        for (ShopItemContainer shopItemContainer : itemDatabase) {
            if (shopItemContainer.shopItem == item)
                return true;
        }
        return false;
    }
    /**
     * Checks if item with given index is in database.
     * @param itemID Searched item ID
     * @return True if item is in database, otherwise false.
     */
    public boolean IsItemInDatabase(int itemID){
        for (ShopItemContainer shopItemContainer : itemDatabase) {
            if (shopItemContainer.shopItem.getID() == itemID)
                return true;
        }
        return false;
    }

    /**
     * Clears item database
     */
    public void ClearDatabase(){
        itemDatabase.clear();
    }

}

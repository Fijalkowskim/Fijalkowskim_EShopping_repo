package fijalkowskim_eshopping.model;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents stock of the shop with items and their available amount.
 * @author Fijalkowskim
 * @version 2.0
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
    public void AddItemToDatabase(ShopItem item, int count) throws ItemAlreadyInDatabaseException, IllegalArgumentException
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
     * Retrieves the index of the given item in the database.
     *
     * @param item The item to find the index of.
     * @return The index of the item in the database.
     * @throws ItemNotInDatabaseException If the item is not in the database.
     */
    public int GetItemIndex(ShopItem item) throws ItemNotInDatabaseException{
        for (int i = 0; i < itemDatabase.size(); i++) {
            if(itemDatabase.get(i).shopItem == item)
                return i;
        }
        throw new ItemNotInDatabaseException("Item is not in database");
    }
    /**
     * Sets the count of items at the specified index in the database.
     *
     * @param itemIndex Index of the item.
     * @param newCount  New count to set.
     */
    public void SetItemCount(int itemIndex, int newCount) {
        if(itemIndex >= getItemDatabase().size() || itemIndex < 0) return;
        getItemDatabase().get(itemIndex).setCount(newCount);
    }
    /**
     * Clears item database
     */
    public void ClearDatabase(){
        itemDatabase.clear();
    }
}

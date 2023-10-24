package model;

import java.util.List;
/**
 * Represents stock of the shop with items and their available amount.
 * @author Fijalkowskim
 * @version %I%, %G%
 */
public class ShopStock {
    List<ShopItemContainer> itemsInStock;

    /**
     * @return List of all items and their amount in stock.
     */
    public List<ShopItemContainer> getItemsInStock() {
        return itemsInStock;
    }

    /**
     * Adds given number of items to stock
     * @param item Item
     * @param count Number of items in stock
     */
    public void AddItemToStock(ShopItem item, int count)
    {
        itemsInStock.add(new ShopItemContainer(item, count));
    }

    /**
     * Initialises items in stock.
     * @param itemsInStock Items in stock.
     */
    public ShopStock(List<ShopItemContainer> itemsInStock) {
        this.itemsInStock = itemsInStock;
    }

    /**
     * Checks if item with given index is available.
     * @param itemIndex Index of an item.
     * @return True if item is available or false when it is not.
     */
    public boolean IsItemAvailable(int itemIndex)
    {
        return itemIndex >= itemsInStock.size() || itemIndex < 0 ? false : itemsInStock.get(itemIndex).count > 0;
    }

    /**
     * Checks if item is added to stock
     * @param item Searched item
     * @return True if item is added to stock (even if count is 0), otherwise false
     */
    public boolean IsItemAddedToStock(ShopItem item)
    {
        for (ShopItemContainer container : itemsInStock){
            if(container.shopItem == item)
                return true;
        }
        return false;
    }

    /**
     * Tries to get an index of given item in shopStock list.
     * @param item Searched item
     * @return Index of given item or -1 if item is not in stock.
     */
    public int TryToGetItemIndex(ShopItem item){
        if(!IsItemAddedToStock(item))
            return -1;
        for (int i = 0; i < itemsInStock.size(); i++) {
            if(itemsInStock.get(i).shopItem == item)
                return i;
        }
        return -1;
    }


}

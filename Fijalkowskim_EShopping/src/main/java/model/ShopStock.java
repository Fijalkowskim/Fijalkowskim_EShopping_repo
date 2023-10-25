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
    public void AddItemToStock(ShopItem item, int count) throws ItemAlreadyInStock
    {
        if(itemsInStock == null || item == null) return;
        if(!CanAddItemToStock(item)) throw new ItemAlreadyInStock("Item is already in stock");
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
    /*public boolean IsItemAvailable(ShopItem item)
    {
        if(item == null || !CanAddItemToStock(item)) return false;
        for(ShopItemContainer containter : itemsInStock)
        {
            if(containter.shopItem == item && containter.count > 0)
                return true;
        }
        return false;
    }*/
    /**
     * Checks if item is added to stock
     * @param item Searched item
     * @return True if item is added to stock (even if count is 0), otherwise false
     */
    public boolean CanAddItemToStock(ShopItem item)
    {
        if (itemsInStock == null || item == null) return false;
        for (ShopItemContainer container : itemsInStock){
            if(container.shopItem == item)
                return false;
        }
        return true;
    }

    /**
     * Tries to get an index of given item in shopStock list.
     * @param item Searched item
     * @return Index of given item or -1 if item is not in stock.
     */
    public int TryToGetItemIndex(ShopItem item){
        if(!CanAddItemToStock(item))
            return -1;
        for (int i = 0; i < itemsInStock.size(); i++) {
            if(itemsInStock.get(i).shopItem == item)
                return i;
        }
        return -1;
    }


}

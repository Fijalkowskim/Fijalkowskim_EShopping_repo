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

}

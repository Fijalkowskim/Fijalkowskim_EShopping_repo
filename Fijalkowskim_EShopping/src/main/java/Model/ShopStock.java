package Model;

import java.util.List;

public class ShopStock {
    public List<ShopItemContainer> getItemsInStock() {
        return itemsInStock;
    }

    List<ShopItemContainer> itemsInStock;

    public ShopStock(List<ShopItemContainer> itemsInStock) {
        this.itemsInStock = itemsInStock;
    }
    public boolean IsItemAvailable(int itemIndex)
    {

        boolean itemAvailable = itemIndex >= itemsInStock.size() || itemIndex < 0 ? false : itemsInStock.get(itemIndex).count > 0;

        return itemAvailable;
    }

}

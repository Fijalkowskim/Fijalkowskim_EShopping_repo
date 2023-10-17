package Model;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    public enum ItemBuyResult {BOUGHT, NOT_ENOUGH_MONEY, ITEM_NOT_IN_STOCK}
    ShopStock shopStock;
    UserData userData;
    public ShopStock getShopStock(){return shopStock;}
    public UserData getUserData() {
        return userData;
    }
    public ShopItemContainer getCurrentItem(int itemIndex)
    {
        if(itemIndex >= shopStock.getItemsInStock().size() || itemIndex < 0) return null;
        return shopStock.getItemsInStock().get(itemIndex);
    }
    public ItemBuyResult TryToBuyItem(int itemIndex)
    {
        if(!shopStock.IsItemAvailable(itemIndex)) return ItemBuyResult.ITEM_NOT_IN_STOCK;
        if(userData.getCash() < shopStock.getItemsInStock().get(itemIndex).getShopItem().getPrice()) return ItemBuyResult.NOT_ENOUGH_MONEY;

        shopStock.getItemsInStock().get(itemIndex).setCount(shopStock.getItemsInStock().get(itemIndex).getCount()-1);
        userData.setCash(userData.getCash() - shopStock.getItemsInStock().get(itemIndex).getShopItem().getPrice());
        return ItemBuyResult.BOUGHT;
    }

    public DataManager() {

        InitShopStock();
        userData = new UserData(567.45f);
    }
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

}

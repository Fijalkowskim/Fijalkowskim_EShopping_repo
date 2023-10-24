package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class DataManagerTest {
    @Test
    public void itemBuyNotEnoughMoney(){
        DataManager dataManager = new DataManager();
        dataManager.userData.cash = 0;
        assertEquals(DataManager.ItemBuyResult.NOT_ENOUGH_MONEY, dataManager.TryToBuyItem(0));
    }
    @ParameterizedTest
    @CsvSource({
            "0,NOT_ENOUGH_MONEY",
            "10.5,NOT_ENOUGH_MONEY",
            "23.1,BOUGHT"
    })
    public void parametrizedItemBuyNotEnoughMoney(float cash, DataManager.ItemBuyResult expected){
        DataManager dataManager = new DataManager();
        dataManager.userData.cash = cash;
        ShopItem item = new ShopItem("TestItem", 20.5f);
        dataManager.TryAddingItemToStock(item, 1);
        assertEquals(expected, dataManager.TryToBuyItem(dataManager.getShopStock().TryToGetItemIndex(item)));
    }

}
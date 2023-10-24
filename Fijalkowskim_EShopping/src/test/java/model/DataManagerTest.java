package model;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DataManagerTest {
    @Test
    public void itemBuyNotEnoughMoney(){
        DataManager dataManager = new DataManager();
        dataManager.userData.cash = 0;
        assertEquals(DataManager.ItemBuyResult.NOT_ENOUGH_MONEY, dataManager.TryToBuyItem(0));
    }

}
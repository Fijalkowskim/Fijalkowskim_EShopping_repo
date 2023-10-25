package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import static org.junit.jupiter.api.Assertions.*;

public class DataManagerTest {
    DataManager dataManager;
    @BeforeEach
    void setUp()
    {
        dataManager = new DataManager();
    }
    @Test
    public void testGetShopStock()
    {
        assertNotEquals(null, dataManager.getShopStock());
    }
    @Test
    public void testGetUserData()
    {
        assertNotEquals(null, dataManager.getUserData());
    }

    @Test
    public void testItemBuyNotEnoughMoney() throws NotEnoughMoneyException{
        dataManager.userData.cash = 0;
        try{
            ShopItem item = new ShopItem(999,"TestItem", 1f);
            dataManager.TryAddingItemToStock(item, 0);
            dataManager.BuyAnItem(dataManager.getShopStock().TryToGetItemIndex(item));
        }
        catch(ItemNotInStockException ex)
        {

        }
    }

    @Test
    public void testItemBuyNotInStock() throws ItemNotInStockException{
        dataManager.userData.cash = 10;

        try {
            ShopItem item = new ShopItem(999,"TestItem", 0.1f);
            dataManager.TryAddingItemToStock(item, 0);
            dataManager.BuyAnItem(dataManager.getShopStock().TryToGetItemIndex(item));
        }
        catch(NotEnoughMoneyException ex)
        {

        }
    }
    @Test
    public void tryAddingNewItemToStock(){
        ShopItem item = new ShopItem(999,"TestItem", 1f);
        assertTrue(dataManager.TryAddingItemToStock(item,0));
    }
    @Test
    public void tryAddingExistingItemToStock(){
        ShopItem item = new ShopItem(999,"TestItem", 1f);
        dataManager.TryAddingItemToStock(item, 0);
        assertFalse(dataManager.TryAddingItemToStock(item,0));
    }
    @Test
    public void testAddingNullItemToStock(){
        ShopItem item = null;
        assertFalse(dataManager.TryAddingItemToStock(item,0));
    }
    @ParameterizedTest
    @CsvSource({
            "5,6,true",
            "7,7,false"
    })
    public void tryAddingExistingItemToStockParametrized(int firstID, int secondID, boolean expected){
        ShopItem item1 = new ShopItem(firstID, "a",0f);
        ShopItem item2 = new ShopItem(secondID, "a",0f);
        dataManager.TryAddingItemToStock(item1, 1);
        assertEquals(expected, dataManager.TryAddingItemToStock(item2, 1));
    }

}
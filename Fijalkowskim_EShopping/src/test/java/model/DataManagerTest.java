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
        ShopItem item = dataManager.CreateNewShopItem("Test", 999f, "");
        try {
            dataManager.AddItemToDatabase(item, 1);
        }
        catch(ItemAlreadyInDatabaseException ex){}
        catch(IllegalArgumentException ex){}

        assertThrows(NotEnoughMoneyException.class, ()-> {
            try {
                dataManager.BuyAnItem(item.getID());
            } catch (ItemNotInStockException ex) {
            }
        });

    }
    @Test
    public void testItemBuyNotInStock() throws ItemNotInStockException{
        dataManager.userData.cash = 10;
        ShopItem item = dataManager.CreateNewShopItem("Test", 1, "");
        try {
            dataManager.AddItemToDatabase(item, 0);
        }
        catch(ItemAlreadyInDatabaseException ex){}
        catch(IllegalArgumentException ex){}


        assertThrows(ItemNotInStockException.class, ()-> {
            try{
                dataManager.BuyAnItem(item.getID());
            }
            catch(NotEnoughMoneyException ex){}
        });
    }
    @Test
    public void testAddingNewItemToDatabase(){
        ShopItem item = dataManager.CreateNewShopItem("Test", 0f,"");
        try {
            dataManager.AddItemToDatabase(item,1);
        } catch (ItemAlreadyInDatabaseException e) {}
        assertTrue(dataManager.getShopStock().IsItemInDatabase(item));
    }
    @Test
    public void testAddingExistingItemToDatabase() throws ItemAlreadyInDatabaseException{
        ShopItem item = dataManager.CreateNewShopItem("Test", 0f,"");
        dataManager.AddItemToDatabase(item,1);
        assertThrows(ItemAlreadyInDatabaseException.class, ()-> {
            dataManager.AddItemToDatabase(item, 1);
        });
    }
    @Test
    public void testAddingNullItemToDatabase() throws IllegalArgumentException{
        ShopItem item = null;
        assertThrows(IllegalArgumentException.class, ()-> {
            try {
                dataManager.AddItemToDatabase(item, 1);
            } catch (ItemAlreadyInDatabaseException e) {
            }
        });
    }
    @ParameterizedTest
    @CsvSource({
            "5,6,true",
            "7,7,false"
    })
    public void testAddingItemsToDatabaseParametrized(int firstID, int secondID, boolean expected){
        ShopItem item1 = new ShopItem(firstID, "a",0f,"");
        ShopItem item2 = new ShopItem(secondID, "b",0f,"");
        try {
            dataManager.AddItemToDatabase(item1, 1);
        } catch (ItemAlreadyInDatabaseException e) {}
        assertEquals(expected, !dataManager.getShopStock().IsItemInDatabase(item2.getID()));
    }

}
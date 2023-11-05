package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataManagerTest {
    DataManager dataManager;
    @BeforeEach
    void setUp()
    {
        dataManager = new DataManager();
        dataManager.getShopStock().ClearDatabase();
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

    enum ExpectedException {
        NONE,
        NOT_IN_STOCK,
        NOT_ENOUGH_MONEY,
        ILLEGAL_ARGUMENT,
        ITEM_ALREADY_IN_DATABASE

    }
    @ParameterizedTest
    @CsvSource({
            "999,0,1,NOT_ENOUGH_MONEY",
            "1,10,0,NOT_IN_STOCK",
            "1,10,1,NONE"
    })
    public void testBuyItem(float price, float cash,int count, ExpectedException expectedException){
        dataManager.userData.cash = cash;
        ShopItem item = dataManager.CreateNewShopItem("Test", price, "");
        try {
            dataManager.AddItemToDatabase(item, count);
        }
        catch(IllegalArgumentException | ItemAlreadyInDatabaseException ex){}

            try {
                dataManager.BuyAnItem(item.getID());
                assertEquals(expectedException,ExpectedException.NONE);
            } catch (ItemNotInStockException ex) {
                assertEquals(expectedException, ExpectedException.NOT_IN_STOCK);
            }
            catch (NotEnoughMoneyException e) {
                assertEquals(expectedException, ExpectedException.NOT_ENOUGH_MONEY);
            }
    }
    @ParameterizedTest
    @CsvSource({
            "1,NONE",
            "0,ITEM_ALREADY_IN_DATABASE",
            "-1,ILLEGAL_ARGUMENT"
    })
    public void testAddItemToDatabase(int id, ExpectedException expectedException){
        ShopItem baseItem = new ShopItem(0, "BaseItem", 10, "");
        ShopItem newItem = new ShopItem(id, "NewItem", 1, "");
        try {
            dataManager.AddItemToDatabase(baseItem,1);
        } catch (ItemAlreadyInDatabaseException e) {}
        try {
            dataManager.AddItemToDatabase(newItem,1);
            assertEquals(expectedException, ExpectedException.NONE);
        } catch (ItemAlreadyInDatabaseException e) {
            assertEquals(expectedException, ExpectedException.ITEM_ALREADY_IN_DATABASE);
        }catch (IllegalArgumentException e){
            assertEquals(expectedException, ExpectedException.ILLEGAL_ARGUMENT);

        }
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
    @ParameterizedTest
    @CsvSource({
            "true,20f,50f,30f,20f,30f,50f",
            "false,20f,50f,30f,50f,30f,20f"
    })
    public void testSortingShopStock(boolean ascendingSort,float firstPrice, float secondPrice, float thirdPrice, float expectedFirstPrice, float expectedSecondPrice, float expectedThirdPrice){
        ShopItem item1 = new ShopItem(0, "a",firstPrice,"");
        ShopItem item2 = new ShopItem(1, "b",secondPrice,"");
        ShopItem item3 = new ShopItem(2, "c",thirdPrice,"");
        dataManager.getShopStock().ClearDatabase();
        try {
            dataManager.AddItemToDatabase(item1, 1);
            dataManager.AddItemToDatabase(item2, 1);
            dataManager.AddItemToDatabase(item3, 1);
        } catch (ItemAlreadyInDatabaseException e) {}
        ShopStock sortedShopStock = dataManager.GetSortedShopStock(ascendingSort);

        assertEquals(sortedShopStock.GetItemContainerByIndex(0).getShopItem().getPrice(), expectedFirstPrice);
        assertEquals(sortedShopStock.GetItemContainerByIndex(1).getShopItem().getPrice(), expectedSecondPrice);
        assertEquals(sortedShopStock.GetItemContainerByIndex(2).getShopItem().getPrice(), expectedThirdPrice);

    }

}
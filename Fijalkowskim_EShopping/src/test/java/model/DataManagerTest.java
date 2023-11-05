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
    void setUp() {
        dataManager = new DataManager();
    }
    @ParameterizedTest
    @CsvSource({
            "true",
            "false"
    })
    public void testLoadShopStock(boolean loadShopStock) {
        if(loadShopStock)
            dataManager.LoadShopStock();
        assertEquals(loadShopStock, !dataManager.getShopStock().getItemDatabase().isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "999,0,1,NOT_ENOUGH_MONEY",
            "1,10,0,ITEM_NOT_IN_STOCK",
            "1,10,1,NONE"
    })
    public void testBuyItem(float price, float cash,int count, ExpectedException expectedException){
        dataManager.userData.cash = cash;
        ShopItem item = new ShopItem("Test", price, "");
        try {
            dataManager.AddItemToDatabase(item, count);
        }
        catch(IllegalArgumentException | ItemAlreadyInDatabaseException ex){}

            try {
                dataManager.BuyAnItem(item);
                assertEquals(expectedException,ExpectedException.NONE);
            } catch (ItemNotInStockException ex) {
                assertEquals(expectedException, ExpectedException.ITEM_NOT_IN_STOCK);
            }
            catch (NotEnoughMoneyException e) {
                assertEquals(expectedException, ExpectedException.NOT_ENOUGH_MONEY);
            } catch (ItemNotInDatabaseException e) {

            }
    }
   @ParameterizedTest
    @CsvSource({
            "1,NONE",
            "2,ITEM_ALREADY_IN_DATABASE",
            "0,ILLEGAL_ARGUMENT"
    })
    public void testAddItemsToDatabase(int numberOfRepetitions, ExpectedException expectedException){
       ShopItem item = new ShopItem("a",0f,"");

        if(numberOfRepetitions <= 0)
        {
            numberOfRepetitions = 1;
            item = null;
        }

       for (int i = 0; i < numberOfRepetitions; i++) {
           try {
               dataManager.AddItemToDatabase(item, 1);
           } catch (ItemAlreadyInDatabaseException e) {
               assertEquals(expectedException, ExpectedException.ITEM_ALREADY_IN_DATABASE);
               return;
           }catch(IllegalArgumentException ex){
               assertEquals(expectedException, ExpectedException.ILLEGAL_ARGUMENT);
               return;
           }
       }
       assertEquals(expectedException, ExpectedException.NONE);

    }
    @ParameterizedTest
    @CsvSource({
            "true,20f,50f,30f,20f,30f,50f",
            "false,20f,50f,30f,50f,30f,20f"
    })
    public void testSortingShopStock(boolean ascendingSort,float firstPrice, float secondPrice, float thirdPrice, float expectedFirstPrice, float expectedSecondPrice, float expectedThirdPrice){
        ShopItem item1 = new ShopItem( "a",firstPrice,"");
        ShopItem item2 = new ShopItem( "b",secondPrice,"");
        ShopItem item3 = new ShopItem( "c",thirdPrice,"");
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
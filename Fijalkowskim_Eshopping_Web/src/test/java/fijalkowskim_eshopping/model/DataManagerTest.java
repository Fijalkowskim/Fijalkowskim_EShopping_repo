package fijalkowskim_eshopping.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the DataManager class.
 * @author Fijalkowskim
 * @version 2.0
 */
public class DataManagerTest {
    DataManager dataManager;
    @BeforeEach
    void setUp() {
        dataManager = new DataManager();
    }
    /**
     * Parameterized test for loading shop stock.
     * @param loadShopStock Boolean value indicating whether to load shop stock.
     */
    @ParameterizedTest
    @CsvSource({
            "true",
            "false"
    })
    public void testLoadShopStock(boolean loadShopStock) {
        if(loadShopStock)
            dataManager.LoadDatabase();
        assertEquals(loadShopStock, !dataManager.getShopStock().getItemDatabase().isEmpty());
    }
    /**
     * Parameterized test for buying an item.
     * @param nullItem Should item be null.
     * @param price Item price.
     * @param cash User's available cash.
     * @param count Item count in stock.
     * @param expectedException Expected exception type.
     */
    @ParameterizedTest
    @CsvSource({
            "true,999,0,1,ITEM_NOT_IN_DATABASE",
            "false,999,0,1,NOT_ENOUGH_MONEY",
            "false,1,10,0,ITEM_NOT_IN_STOCK",
            "false,1,10,1,NONE",
            "false,1,2,-1,ITEM_NOT_IN_DATABASE"
    })
    public void testBuyItem(boolean nullItem, float price, float cash,int count, ExceptionType expectedException){
        dataManager.userData.cash = cash;
        ShopItem item = nullItem ? null : new ShopItem("Test", price, "");
        try {
            dataManager.AddItemToDatabase(item, count);
        }
        catch(IllegalArgumentException | ItemAlreadyInDatabaseException ex){}

            try {
                dataManager.BuyAnItem(item);
                assertEquals(expectedException, ExceptionType.NONE);
            } catch (ItemNotInStockException ex) {
                assertEquals(expectedException, ExceptionType.ITEM_NOT_IN_STOCK);
            } catch (NotEnoughMoneyException e) {
                assertEquals(expectedException, ExceptionType.NOT_ENOUGH_MONEY);
            } catch (ItemNotInDatabaseException e) {
                assertEquals(expectedException, ExceptionType.ITEM_NOT_IN_DATABASE);
            }
    }
    /**
     * Parameterized test for adding items to the database.
     * @param nullItem Should item be null.
     * @param numberOfRepetitions Number of repetitions for adding an item.
     * @param expectedException Expected exception type.
     */
   @ParameterizedTest
    @CsvSource({
            "false,0,NONE",
            "false,1,NONE",
            "false,2,ITEM_ALREADY_IN_DATABASE",
            "true,1,ILLEGAL_ARGUMENT",
    })
    public void testAddItemsToDatabase(boolean nullItem, int numberOfRepetitions, ExceptionType expectedException){
       ShopItem item = nullItem ? null : new ShopItem();

       for (int i = 0; i < numberOfRepetitions; i++) {
           try {
               dataManager.AddItemToDatabase(item, 1);
           } catch (ItemAlreadyInDatabaseException e) {
               assertEquals(expectedException, ExceptionType.ITEM_ALREADY_IN_DATABASE);
               return;
           }catch(IllegalArgumentException ex){
               assertEquals(expectedException, ExceptionType.ILLEGAL_ARGUMENT);
               return;
           }
       }
       assertEquals(expectedException, ExceptionType.NONE);

    }
    /**
     * Parameterized test for sorting the shop stock.
     * @param ascendingSort Whether to sort in ascending order.
     * @param firstPrice Price of the first item.
     * @param secondPrice Price of the second item.
     * @param thirdPrice Price of the third item.
     * @param expectedFirstPrice Expected price of the first item after sorting.
     * @param expectedSecondPrice Expected price of the second item after sorting.
     * @param expectedThirdPrice Expected price of the third item after sorting.
     */
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
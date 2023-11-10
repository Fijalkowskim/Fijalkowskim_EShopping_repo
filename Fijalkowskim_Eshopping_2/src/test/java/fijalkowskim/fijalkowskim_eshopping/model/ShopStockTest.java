package fijalkowskim.fijalkowskim_eshopping.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for the ShopStock class.
 * @author Fijalkowskim
 * @version 1.1
 */
public class ShopStockTest {
    ShopStock stock;

    @BeforeEach
    void setUp() {
        stock = new ShopStock();
    }
    /**
     * Parameterized test for adding items to the database.
     * @param numberOfRepetitions Number of repetitions for adding an item.
     * @param expectedException Expected exception type.
     */
    @ParameterizedTest
    @CsvSource({
            "1,NONE",
            "2,ITEM_ALREADY_IN_DATABASE",
            "0,ILLEGAL_ARGUMENT"
    })
    public void testAddItemToDatabase(int numberOfRepetitions, ExpectedException expectedException) {
        ShopItem item = new ShopItem("a", 0f, "");

        if (numberOfRepetitions <= 0) {
            numberOfRepetitions = 1;
            item = null;
        }

        for (int i = 0; i < numberOfRepetitions; i++) {
            try {
                stock.AddItemToDatabase(item, 1);
            } catch (ItemAlreadyInDatabaseException e) {
                assertEquals(expectedException, ExpectedException.ITEM_ALREADY_IN_DATABASE);
                return;
            } catch (IllegalArgumentException ex) {
                assertEquals(expectedException, ExpectedException.ILLEGAL_ARGUMENT);
                return;
            }
        }
        assertEquals(expectedException, ExpectedException.NONE);
    }
    /**
     * Parameterized test for adding item amount to the database.
     * @param addItemToDatabase Whether to add an item to the database before testing.
     * @param nullItem Indicates whether the item is null.
     * @param addedCount The count to add to the item in the database.
     * @param expectedException Expected exception type.
     */
    @ParameterizedTest
    @CsvSource({
            "false,false,10,ITEM_NOT_IN_DATABASE",
            "true,true,10,ILLEGAL_ARGUMENT",
            "true,false,10,NONE",
            "true,false,-1,ILLEGAL_ARGUMENT",
    })
    public void testAddItemAmountToDatabase(boolean addItemToDatabase, boolean nullItem, int addedCount, ExpectedException expectedException) {
        ShopItem item = nullItem ? null : new ShopItem("", 10, "");
        if (addItemToDatabase) {
            try {
                stock.AddItemToDatabase(item, 0);
            } catch (ItemAlreadyInDatabaseException e) {
            } catch (IllegalArgumentException e) {
                if (nullItem)
                    assertEquals(expectedException, ExpectedException.ILLEGAL_ARGUMENT);
            }
        }
        try {
            stock.AddItemAmountToDatabase(item, addedCount);
            assertEquals(stock.GetItemContainerInDatabase(item).count, addedCount);
            assertEquals(expectedException, ExpectedException.NONE);
        } catch (ItemNotInDatabaseException e) {
            assertEquals(expectedException, ExpectedException.ITEM_NOT_IN_DATABASE);
        } catch (IllegalArgumentException e) {
            assertEquals(expectedException, ExpectedException.ILLEGAL_ARGUMENT);
        }
    }
    /**
     * Parameterized test for checking if an item is in stock.
     * @param searchNullItem Indicates whether to search for a null item.
     * @param itemCount The count of items in stock.
     * @param expected The expected result (true if the item is in stock, false otherwise).
     */
    @ParameterizedTest
    @CsvSource({
            "true,10,false",
            "true,-10,false",
            "false,10,true",
            "false,0,false",
            "false,-1,false"
    })
    public void testIsItemInStock(boolean searchNullItem, int itemCount, boolean expected) {
        ShopItem item = new ShopItem();
        try {
            stock.AddItemToDatabase(item, itemCount);
        } catch (ItemAlreadyInDatabaseException | IllegalArgumentException ex) {
        }
        assertEquals(expected, searchNullItem ? stock.IsItemInStock(null) : stock.IsItemInStock(item));
    }
    /**
     * Parameterized test for getting an item container in the database.
     * @param nullItem Indicates whether the item is null.
     * @param addItemToDatabase Whether to add an item to the database before testing.
     * @param itemCount The count of items in the database.
     * @param expectedException Expected exception type.
     */
    @ParameterizedTest
    @CsvSource({
            "true,false,10,ITEM_NOT_IN_DATABASE",
            "true,true,10,ILLEGAL_ARGUMENT",
            "false,false,10,ITEM_NOT_IN_DATABASE",
            "false,true,10,NONE",
            "false,true,-1,ILLEGAL_ARGUMENT",
    })
    public void testGetItemContainerInDatabase(boolean nullItem, boolean addItemToDatabase,int itemCount,ExpectedException expectedException) {
        ShopItem item = nullItem ? null : new ShopItem();
        if(addItemToDatabase){
            try {
                stock.AddItemToDatabase(item, itemCount);
            } catch (ItemAlreadyInDatabaseException | IllegalArgumentException e) {
                assertEquals(expectedException, ExpectedException.ILLEGAL_ARGUMENT);
                return;
            }
        }
        try {
            ShopItemContainer container = stock.GetItemContainerInDatabase(item);
            assertEquals(container.shopItem, item);
            assertEquals(container.count, itemCount);
            assertEquals(expectedException, ExpectedException.NONE);

        } catch (ItemNotInDatabaseException e) {
            assertEquals(expectedException, ExpectedException.ITEM_NOT_IN_DATABASE);
        }
    }
    /**
     * Parameterized test for checking if an item is in the database.
     * @param nullItem Indicates whether the item is null.
     * @param addItemToDatabase Whether to add an item to the database before testing.
     * @param count The count of items in the database.
     * @param expectedResult The expected result (true if the item is in the database, false otherwise).
     */
    @ParameterizedTest
    @CsvSource({
            "true, true, 10, false",
            "true, false, 10, false",
            "false, false, 10, false",
            "false, true, 10, true",
            "false, true, 0, true",
            "false, true, -1, false",
    })
    public void testIsItemInDatabase(boolean nullItem, boolean addItemToDatabase, int count,boolean expectedResult){
        ShopItem item = nullItem ? null : new ShopItem();
        if(addItemToDatabase){
            try {
                stock.AddItemToDatabase(item,count);
            } catch (ItemAlreadyInDatabaseException | IllegalArgumentException e) {
            }
        }
        assertEquals(stock.IsItemInDatabase(item), expectedResult);
    }
    /**
     * Parameterized test for clearing the database.
     * @param itemsAddedToDatabase The number of items added to the database.
     * @param expectedCount The expected count of items in the database after clearing.
     */
    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "10, 0",
    })
    public void testClearDatabase(int itemsAddedToDatabase,int expectedCount){
        if(itemsAddedToDatabase > 0){
            for (int i = 0; i < itemsAddedToDatabase; i++) {
                try {
                    stock.AddItemToDatabase(new ShopItem(), 1);
                } catch (ItemAlreadyInDatabaseException e) {
                }
            }
        }
        stock.ClearDatabase();
        assertEquals(stock.itemDatabase.size(),expectedCount);
    }
}


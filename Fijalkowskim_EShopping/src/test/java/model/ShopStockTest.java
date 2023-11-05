package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShopStockTest {
    ShopStock stock;
    @BeforeEach
    void setUp() {
        List<ShopItemContainer> containerList = new ArrayList<ShopItemContainer>();
        stock = new ShopStock(containerList);
    }

    @Test
    public void testAddItemDuplicateToStock() throws ItemAlreadyInDatabaseException {
        ShopItem item = new ShopItem("test",0f,"");
        stock.AddNewItemToDatabase(item, 1);

        assertThrows(ItemAlreadyInDatabaseException.class,()->{
            stock.AddNewItemToDatabase(item, 1);
        });
    }
    @Test
    public void testItemAvailable() {
        ShopItem item = new ShopItem("test", 0f,"");
        try {
            stock.AddNewItemToDatabase(item, 1);
        }catch(ItemAlreadyInDatabaseException ex)
        {

        }
        assertTrue(stock.IsItemInStock(0));
    }
    @Test
    public void testItemUnavailable()
    {
        assertFalse(stock.IsItemInStock(0));
    }

    @Test
    public void testAddNullItem() throws ItemAlreadyInDatabaseException {

        assertThrows(IllegalArgumentException.class,()->{
            stock.AddNewItemToDatabase(null, 1);
        });
    }
    @Test
    public void testAddNegativeCount() throws ItemAlreadyInDatabaseException {
        ShopItem item = new ShopItem("test", 1f, "");
        assertThrows(IllegalArgumentException.class,()->{
            stock.AddNewItemToDatabase(item, -1);
        });
    }
    @Test
    public void testIsItemInDatabase(){
        ShopItem item = new ShopItem("test", 1f, "");
        ShopItem item2 = new ShopItem("test", 1f, "");
        try {
            stock.AddNewItemToDatabase(item,1);
        } catch (ItemAlreadyInDatabaseException e) {
            throw new RuntimeException(e);
        }
        assertTrue(stock.IsItemInDatabase(item2));
    }
    @Test
    public void testIsNullItemInDatabase(){
        ShopItem item = new ShopItem("test", 1f, "");
        try {
            stock.AddNewItemToDatabase(item,1);
        } catch (ItemAlreadyInDatabaseException e) {
            throw new RuntimeException(e);
        }
        assertFalse(stock.IsItemInDatabase(null));
    }
    @Test
    public void testIsItemInStock() {
        ShopItem item = new ShopItem("test", 1f, "");
        try {
            stock.AddNewItemToDatabase(item,1);
        } catch (ItemAlreadyInDatabaseException e) {
            throw new RuntimeException(e);
        }
        assertTrue(stock.IsItemInStock(item));
    }
}
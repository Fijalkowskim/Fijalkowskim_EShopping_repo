package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShopStockTest {
    ShopStock stock;
    @BeforeEach
    void setUp()
    {
        List<ShopItemContainer> containerList = new ArrayList<ShopItemContainer>();
        stock = new ShopStock(containerList);
    }

    @Test
    public void testAddItemDuplicateToStock() throws ItemAlreadyInDatabaseException
    {
        ShopItem item = new ShopItem(0, "test",0f,"");
        stock.AddNewItemToDatabase(item, 1);

        assertThrows(ItemAlreadyInDatabaseException.class,()->{
            stock.AddNewItemToDatabase(item, 1);
        });
    }
    @Test
    public void testItemAvailable()
    {
        ShopItem item = new ShopItem(0,"test", 0f,"");
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



}
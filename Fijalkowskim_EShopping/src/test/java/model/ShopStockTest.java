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
    public void testAddItemDuplicateToStock() throws ItemAlreadyInStock
    {
        ShopItem item = new ShopItem(0, "test",0f);
        stock.AddItemToStock(item, 1);
        stock.AddItemToStock(item, 1);
    }
    @Test
    public void testItemAvailable()
    {
        ShopItem item = new ShopItem(0,"test", 0f);
        try {
            stock.AddItemToStock(item, 1);
        }catch(ItemAlreadyInStock ex)
        {

        }
        assertTrue(stock.IsItemAvailable(0));
    }
    @Test
    public void testItemUnavailable()
    {
        assertFalse(stock.IsItemAvailable(0));
    }



}
package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShopItemTest {
    @Test
    public void testGetId()
    {
        ShopItem item = new ShopItem(0,"a",0f,"");
        assertEquals(0,item.getID());
    }
    @Test
    public void testGetName()
    {
        ShopItem item = new ShopItem(0,"a",0f,"");
        assertEquals("a",item.getName());
    }
    @Test
    public void testGetPrice()
    {
        ShopItem item = new ShopItem(0,"a",0f,"");
        assertEquals(0f,item.getPrice());
    }


}
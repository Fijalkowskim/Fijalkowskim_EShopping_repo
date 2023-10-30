package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShopItemContainerTest {
    ShopItemContainer container;
    @Test
    public void testNegativeCountInConstructor(){
        ShopItem item = new ShopItem(0,"a",0f,"");
        container = new ShopItemContainer(item, -1);
        assertEquals(0,container.count);
    }
    @Test
    public void testSetNegativeCount(){
        ShopItem item = new ShopItem(0,"a",0f,"");
        container = new ShopItemContainer(item, 10);
        container.setCount(-10);
        assertNotEquals(-10,container.count);
    }
    @Test
    public void testSetShopItem(){
        ShopItem item = new ShopItem(0,"a",0f,"");
        container = new ShopItemContainer(null, 10);
        container.setShopItem(item);
        assertEquals(item, container.shopItem);
    }
    @Test
    public void testGetShopItem(){
        ShopItem item = new ShopItem(0,"a",0f,"");
        container = new ShopItemContainer(item, 10);
        assertEquals(item, container.getShopItem());
    }
    @Test
    public void testGetCount(){
        ShopItem item = new ShopItem(0,"a",0f,"");
        container = new ShopItemContainer(item, 10);
        assertEquals(10, container.getCount());
    }


}
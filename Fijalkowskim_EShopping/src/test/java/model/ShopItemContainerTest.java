package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShopItemContainerTest {
    ShopItemContainer container;
    @Test
    public void testSetNegativeCount(){
        ShopItem item = new ShopItem(0,"a",0f);
        container = new ShopItemContainer(item, -1);
        assertEquals(0,container.count);
    }
    @Test
    public void testChangeCountToNegative(){
        ShopItem item = new ShopItem(0,"a",0f);
        container = new ShopItemContainer(item, 10);
        container.setCount(-10);
        assertNotEquals(10,container.count);
    }

}
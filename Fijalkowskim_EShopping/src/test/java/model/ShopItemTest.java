package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShopItemTest {
    ShopItem item;
    @BeforeEach
    void setUp(){
        item = new ShopItem("a",0f,"");
    }
    @Test
    public void testGetName() {
        assertEquals("a",item.getName());
    }
    @Test
    public void testGetPrice() {
        assertEquals(0f,item.getPrice());
    }
    @Test
    public void testGetDescription() {
        assertEquals("",item.getDescription());
    }
    @Test
    public void testSetPrice(){
        item.setPrice(1.0f);
        assertEquals(item.price, 1.0f);
    }
    @Test
    public void testSetDescription(){
        item.setDescription("test");
        assertEquals(item.description, "test");
    }
    @Test
    public void testSetName(){
        item.setName("b");
        assertEquals(item.name, "b");
    }
    @Test
    public void testSetEmptyName(){
        item.setName("");
        assertNotEquals("", item.name);
    }
    @Test
    public void testSetNegativePrice(){
        item.setPrice(-1);
        assertNotEquals(-1, item.price);
    }



}
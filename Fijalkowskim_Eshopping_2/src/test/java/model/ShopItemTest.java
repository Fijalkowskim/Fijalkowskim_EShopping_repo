package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for the ShopItem class.
 * @author Fijalkowskim
 * @version 1.1
 */
public class ShopItemTest {
    /**
     * Parameterized test for the ShopItem constructor.
     * @param name Item name to set.
     * @param price Item price to set.
     * @param description Item description to set.
     * @param expectedName Expected name after construction.
     * @param expectedPrice Expected price after construction.
     * @param expectedDescription Expected description after construction.
     */
    @ParameterizedTest
    @CsvSource({
            "Item,10,Desc,Item,10,Desc",
            ",5,a,Unnamed item,5,a",
            "a,3,,a,3,No description",
            "a,-1,b,a,0,b",
    })
    public void testShopItemConstructorData(String name, float price, String description,
                                            String expectedName, float expectedPrice, String expectedDescription ) {
        ShopItem item = new ShopItem(name, price, description);
        assertEquals(item.name, expectedName);
        assertEquals(item.price, expectedPrice);
        assertEquals(item.description, expectedDescription);
    }
    /**
     * Parameterized test for getting ShopItem data.
     * @param name Item name to set.
     * @param price Item price to set.
     * @param description Item description to set.
     * @param expectedName Expected name to retrieve.
     * @param expectedPrice Expected price to retrieve.
     * @param expectedDescription Expected description to retrieve.
     */
    @ParameterizedTest
    @CsvSource({
            "Test,2.5,d,Test,2.5,d",
            ",1,x,Unnamed item,1,x",
            "y,8,,y,8,No description",
            "z,-1,zz,z,0,zz",
    })
    public void testGetShopItemData(String name, float price, String description,
                                            String expectedName, float expectedPrice, String expectedDescription ) {
        ShopItem item = new ShopItem(name, price, description);
        assertEquals(item.getName(), expectedName);
        assertEquals(item.getPrice(), expectedPrice);
        assertEquals(item.getDescription(), expectedDescription);
    }
}
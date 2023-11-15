package fijalkowskim.fijalkowskim_eshopping.model;

import fijalkowskim.fijalkowskim_eshopping.model.ShopItem;
import fijalkowskim.fijalkowskim_eshopping.model.ShopItemContainer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for the ShopItemContainer class.
 * @author Fijalkowskim
 * @version 2.0
 */
public class ShopItemContainerTest {
    ShopItemContainer container;
    /**
     * Parameterized test for setting count in the constructor.
     * @param setCount The count to set in the constructor.
     * @param expectedCount The expected count after construction.
     */
    @ParameterizedTest
    @CsvSource({
            "-1,0",
            "5,5"
    })
    public void testContainerSetCountInConstructor(int setCount, int expectedCount){
        ShopItem item = new ShopItem("a",0f,"");
        container = new ShopItemContainer(item, setCount);
        assertEquals(expectedCount,container.count);
    }
    /**
     * Parameterized test for setting count.
     * @param setCount The count to set.
     * @param expectedCount The expected count after setting.
     */
    @ParameterizedTest
    @CsvSource({
            "-8,0",
            "7,7"
    })
    public void testContainerSetCount(int setCount, int expectedCount){
        ShopItem item = new ShopItem("a",0f,"");
        container = new ShopItemContainer(item, 0);
        container.setCount(setCount);
        assertEquals(expectedCount,container.count);
    }
    /**
     * Parameterized test for getting item container data.
     * @param nullItem Indicates whether the item is null.
     * @param itemPrice The price of the item.
     * @param count The count of items.
     */
    @ParameterizedTest
    @CsvSource({
            "true,0,10",
            "false,7,5",
    })
    public void testGettingItemContainerData(boolean nullItem, float itemPrice, int count){
        ShopItem item = nullItem ? null : new ShopItem("a",itemPrice,"");
        container = new ShopItemContainer(item, count);
        assertEquals(item, container.shopItem);
        assertEquals(count, container.count);
    }


}
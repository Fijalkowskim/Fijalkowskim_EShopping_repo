package fijalkowskim_eshopping.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages all the data such as shop items or user data (cash).
 * @author Fijalkowskim
 * @version 2.0
 */
public class DataManager {
    ShopStock shopStock;
    UserData userData;
    /**
     * @return Stock of the shop
     */
    public ShopStock getShopStock(){return shopStock;}
    /**
     * @return User data
     */
    public UserData getUserData() {return userData;}

    /**
     * Initialises shop stock and user cash.
     * @param cash Cash of the user
     */
    public DataManager(float cash){
        shopStock = new ShopStock();
        userData = new UserData(cash);
    }
    /**
     * Initialises shop stock and user data (cash to 0).
     */
    public DataManager() {
        this(1000);
    }

    /**
     * Buys given item. Throws an exception if it is not possible.
     * @param item Given item.
     */
    public void BuyAnItem(ShopItem item) throws NotEnoughMoneyException, ItemNotInStockException, ItemNotInDatabaseException {
        if(!shopStock.IsItemInDatabase(item))
            throw new ItemNotInDatabaseException(("Item is not in database"));
        if (!shopStock.IsItemInStock(item))
            throw new ItemNotInStockException("Item is not in stock");
        if (userData.getCash() < item.getPrice())
            throw new NotEnoughMoneyException("Not enough money");
        shopStock.GetItemContainerInDatabase(item).setCount(shopStock.GetItemContainerInDatabase(item).getCount() - 1);
        userData.setCash(userData.getCash() - item.getPrice());
    }
    /**
     * Loads items from json file and adds them to database.
     */
    public void LoadDatabase() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode rootNode = objectMapper.readTree(DataManager.class.getResource("itemDatabase.json"));

            for (JsonNode jsonNode : rootNode) {
                AddItemToDatabase(new ShopItem(jsonNode.get("name").asText(),
                        (float) jsonNode.get("price").asDouble(),
                        jsonNode.get("description").asText(),
                        jsonNode.get("imageURL").asText()),
                        jsonNode.get("count").asInt());
            }
        } catch (ItemAlreadyInDatabaseException | IllegalArgumentException | IOException e) {}
    }

        /**
     * Tries to add new item to database only if it is not already there.
     * @param item Item
     * @param count Amount of this item
     */
    public void AddItemToDatabase(ShopItem item, int count) throws ItemAlreadyInDatabaseException, IllegalArgumentException {
        if(item == null || shopStock == null || count < 0)
            throw new IllegalArgumentException("Illegal argument");
        shopStock.AddItemToDatabase(item, count);
    }

    /**
     * Sorts shop stock ascending or descending and returns sorted shop stock.
     * @param ascending True for ascending sort, false for descending.
     * @return Sorted shop stock
     */
    public ShopStock GetSortedShopStock(boolean ascending){
        List<ShopItemContainer> sortedItemDatabase = shopStock.getItemDatabase().stream()
                .sorted((container1, container2) -> Float.compare(container1.getShopItem().getPrice(), container2.getShopItem().getPrice()))
                .collect(Collectors.toList());
        if(!ascending) Collections.reverse(sortedItemDatabase);
        return new ShopStock(sortedItemDatabase);
    }
    public String displayedDataToJson(int itemIndex, ExceptionType exceptionType) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ShopItemContainer shopItemContainer = shopStock.getItemDatabase().isEmpty() ? null : shopStock.GetItemContainerByIndex(itemIndex);
        DisplayedData jsonDataStructure = new DisplayedData(userData.getCash(), shopItemContainer, exceptionType);
        return objectMapper.writeValueAsString(jsonDataStructure);
    }

}

package fijalkowskim.fijalkowskim_eshopping.model;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Manages all the data such as shop items or user data (cash).
 * @author Fijalkowskim
 * @version 1.1
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
        this(0);
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
     * Loads items froms database to shop stock
     */
    public void LoadShopStock() {

        try {
            /*AddItemToDatabase(new ShopItem("Bike", 300f, "Small BMX for children.","bike.png"), 6);
            AddItemToDatabase(new ShopItem("SamsungTV", 2050.99f, "75 inch Samsung TV.","tv.png"), 3);
            AddItemToDatabase(new ShopItem("Apple", 0.89f, "Just an apple.","apple.png"), 21);
            AddItemToDatabase(new ShopItem("Creatine 400g", 39.49f, "Creatine monohydrat.","creatine.png"), 117);*/

            ObjectMapper objectMapper = new ObjectMapper();

            // Wczytaj plik JSON
            File jsonFile = new File("ścieżka/do/twojego/pliku.json");
            JsonNode rootNode = objectMapper.readTree(jsonFile);

            // Przetwórz każdy element w tablicy
            List<ShopItemContainer> itemContainers = new ArrayList<>();
            for (JsonNode jsonNode : rootNode) {
                ShopItemContainer itemContainer = new ShopItemContainer();

                // Przypisz wartości do itemContainer na podstawie JSON
                itemContainer.setShopItem(new ShopItem(
                        jsonNode.get("name").asText(),
                        (float) jsonNode.get("price").asDouble(),
                        jsonNode.get("description").asText(),
                        jsonNode.get("imageURL").asText()
                ));
                itemContainer.setCount(jsonNode.get("count").asInt());

                // Dodaj do listy
                itemContainers.add(itemContainer);
            }

            // Teraz masz listę ShopItemContainer na podstawie pliku JSON
            for (ShopItemContainer itemContainer : itemContainers) {
                System.out.println(itemContainer);
            }

        }
        catch (ItemAlreadyInDatabaseException | IllegalArgumentException ex) {}
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

}

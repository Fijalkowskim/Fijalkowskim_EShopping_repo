package fijalkowskim_eshopping.controller;

import fijalkowskim_eshopping.model.*;

import java.sql.*;
import java.util.List;

/**
 * Main controller of the program (Singleton).
 * Manages the overall flow of the application.
 *
 * @author Fijalkowskim
 * @version 2.0
 */
public class Controller {
    private static final Controller INSTANCE = new Controller();
    private DataManager dataManager;
    private int currentPage;
    private ShopStock targetedShopStock;
    private ShopItemContainer currentShopItemContainer;
    private SortingOrder currentSortingOrder;

    private Connection dbConnection;



    private Controller() {
        this.dataManager = new DataManager();
        dataManager.LoadDatabase();
        currentPage = 0;
        currentSortingOrder = SortingOrder.NO_SORTING;
        targetedShopStock = dataManager.getShopStock();
        currentShopItemContainer = targetedShopStock.GetItemContainerByIndex(currentPage);
        dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eshopping?useSSL=false", "root", "root");
        } catch (SQLException e) {

        }
    }

    /**
     * Gets the instance of the Controller (Singleton pattern).
     *
     * @return The instance of the Controller.
     */
    public static Controller getInstance() {
        return INSTANCE;
    }

    /**
     * Gets the data manager.
     *
     * @return The data manager.
     */
    public DataManager getDataManager() {
        return dataManager;
    }

    /**
     * Gets the index of the currently selected item.
     *
     * @return The index of the currently selected item.
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * Gets the targeted shop stock.
     *
     * @return The targeted shop stock.
     */
    public ShopStock getTargetedShopStock() {
        return targetedShopStock;
    }

    /**
     * Gets the current shop item container.
     *
     * @return The current shop item container.
     */
    public ShopItemContainer getCurrentShopItemContainer() {
        return currentShopItemContainer;
    }

    /**
     * Gets the database connection.
     *
     * @return The database connection.
     */
    public Connection getDbConnection() {
        return dbConnection;
    }

    /**
     * Tries to buy the currently selected item.
     *
     * @throws NotEnoughMoneyException   If there is not enough money to buy the item.
     * @throws ItemNotInStockException   If the item is not in stock.
     * @throws ItemNotInDatabaseException If the item is not in the database.
     */
    public void TryToBuyItem() throws NotEnoughMoneyException, ItemNotInStockException, ItemNotInDatabaseException {
        try {
            ShopItemContainer itemContainer = targetedShopStock.getItemDatabase().get(currentPage);
            dataManager.BuyAnItem(itemContainer.getShopItem());
        } catch (NotEnoughMoneyException | ItemNotInStockException | ItemNotInDatabaseException ex) {
            throw ex;
        }
    }

    /**
     * Changes the currently selected item.
     *
     * @param nextItem True if the next item should be selected, false for the previous item.
     * @return The new current shop item container.
     */
    public ShopItemContainer ChangeItem(boolean nextItem) {
        currentPage = nextItem ? (currentPage == targetedShopStock.getItemDatabase().size() - 1 ? 0 : currentPage + 1)
                : (currentPage == 0 ? targetedShopStock.getItemDatabase().size() - 1 : currentPage - 1);
        currentShopItemContainer = targetedShopStock.GetItemContainerByIndex(currentPage);
        return targetedShopStock.GetItemContainerByIndex(currentPage);
    }

    /**
     * Shows information about a specific item.
     *
     * @param item The item to display information about.
     */
    public void ShowSpecificItem(ShopItem item) {
        if (item == null || targetedShopStock == null)
            return;
        try {
            currentShopItemContainer = targetedShopStock.GetItemContainerInDatabase(item);
            currentPage = targetedShopStock.GetItemIndex(item);
        } catch (ItemNotInDatabaseException e) {
            // Handle the exception or log it
        }
    }

    /**
     * Changes the sorting order of the shop stock.
     *
     * @param newSortingOrder The new sorting order to apply.
     */
    public void ChangeSorting(SortingOrder newSortingOrder) {
        if (currentSortingOrder == newSortingOrder)
            return;
        currentSortingOrder = newSortingOrder;
        targetedShopStock = newSortingOrder == SortingOrder.ASCENDING ?
                dataManager.GetSortedShopStock(true) :
                newSortingOrder == SortingOrder.DESCENDING ?
                        dataManager.GetSortedShopStock(false) :
                        dataManager.getShopStock();
    }

    /**
     * Loads saved data into the controller.
     *
     * @param savedCash         The saved cash amount.
     * @param savedItemContainers  The saved items and their amounts.
     * @param savedPageIndex    The saved index of the currently selected item.
     */
    public void LoadSavedData(float savedCash, List<ShopItemContainer> savedItemContainers, int savedPageIndex) {
        if (savedCash >= 0)
            dataManager.getUserData().setCash(savedCash);

        if (savedItemContainers != null) {
            targetedShopStock.ClearDatabase();
            for(ShopItemContainer itemContainer : savedItemContainers){
                try {
                    targetedShopStock.AddItemToDatabase(itemContainer.getShopItem(), itemContainer.getCount());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        if (savedPageIndex >= 0 && savedPageIndex < targetedShopStock.getItemDatabase().size()) {
            currentPage = savedPageIndex;
            currentShopItemContainer = targetedShopStock.GetItemContainerByIndex(currentPage);
        }
    }
}
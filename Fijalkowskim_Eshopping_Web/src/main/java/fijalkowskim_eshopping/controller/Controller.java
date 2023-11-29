package fijalkowskim_eshopping.controller;

import fijalkowskim_eshopping.model.*;

import java.util.Dictionary;
import java.util.Map;

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
    private int currentItemIndex;
    private ShopStock targetedShopStock;
    private ShopItemContainer currentShopItemContainer;
    private SortingOrder currentSortingOrder;

    private Controller() {
        this.dataManager = new DataManager();
        dataManager.LoadDatabase();
        currentItemIndex = 0;
        currentSortingOrder = SortingOrder.NO_SORTING;
        targetedShopStock = dataManager.getShopStock();
        currentShopItemContainer = targetedShopStock.GetItemContainerByIndex(currentItemIndex);
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
    public int getCurrentItemIndex() {
        return currentItemIndex;
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
     * Tries to buy the currently selected item.
     *
     * @throws NotEnoughMoneyException   If there is not enough money to buy the item.
     * @throws ItemNotInStockException   If the item is not in stock.
     * @throws ItemNotInDatabaseException If the item is not in the database.
     */
    public void TryToBuyItem() throws NotEnoughMoneyException, ItemNotInStockException, ItemNotInDatabaseException {
        try {
            ShopItemContainer itemContainer = targetedShopStock.getItemDatabase().get(currentItemIndex);
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
        currentItemIndex = nextItem ? (currentItemIndex == targetedShopStock.getItemDatabase().size() - 1 ? 0 : currentItemIndex + 1)
                : (currentItemIndex == 0 ? targetedShopStock.getItemDatabase().size() - 1 : currentItemIndex - 1);
        currentShopItemContainer = targetedShopStock.GetItemContainerByIndex(currentItemIndex);
        return targetedShopStock.GetItemContainerByIndex(currentItemIndex);
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
            currentItemIndex = targetedShopStock.GetItemIndex(item);
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
     * @param savedItemsAmount  The saved items and their amounts.
     * @param savedPageIndex    The saved index of the currently selected item.
     */
    public void LoadSavedData(float savedCash, Map<Integer, Integer> savedItemsAmount, int savedPageIndex) {
        if (savedCash >= 0)
            dataManager.getUserData().setCash(savedCash);
        if (savedPageIndex >= 0 && savedPageIndex < targetedShopStock.getItemDatabase().size()) {
            currentItemIndex = savedPageIndex;
            currentShopItemContainer = targetedShopStock.GetItemContainerByIndex(currentItemIndex);
        }

        if (savedItemsAmount != null) {
            for (Map.Entry<Integer, Integer> entry : savedItemsAmount.entrySet()) {
                targetedShopStock.SetItemCount(entry.getKey(), entry.getValue());
            }
        }
    }
}
package fijalkowskim.fijalkowskim_eshopping.controller;

import fijalkowskim.fijalkowskim_eshopping.model.*;
import fijalkowskim.fijalkowskim_eshopping.view.GuiManager;

/**
 * Main controller of the program. It is the only class used in main method.
 * @author Fijalkowskim
 * @version 2.0
 */
public class Controller {
    DataManager dataManager;
    /**
     * Gets the data manager associated with this controller.
     * @return The data manager.
     */
    public DataManager getDataManager() {
        return dataManager;
    }

    GuiManager guiManager;
    /**
     * Gets the GUI manager associated with this controller.
     * @return The GUI manager.
     */

    public GuiManager getGuiManager() {
        return guiManager;
    }
    int currentItemIndex;
    /**
     * Gets the index of the currently selected shop item.
     * @return The current item index.
     */
    public int getCurrentItemIndex(){
        return currentItemIndex;
    }
    ShopStock targetedShopStock;
    /**
     * Gets the targeted shop stock.
     * @return The targeted shop stock.
     */
    public ShopStock getTargetedShopStock(){
        return targetedShopStock;
    }
    ShopItemContainer currentShopItemContainer;
    /**
     * Gets the current shop item container.
     * @return The current shop item container.
     */
    public ShopItemContainer getCurrentShopItemContainer() {
        return currentShopItemContainer;
    }
    SortingOrder currentSortingOrder;

    /**
     * Initializes controller with existing GuiManager
     * @param guiManager GuiManager
     */
    public Controller(GuiManager guiManager) {
        this.dataManager = new DataManager();
        this.guiManager = guiManager;
        dataManager.LoadDatabase();
        currentItemIndex = 0;
        currentSortingOrder = SortingOrder.NO_SORTING;
        targetedShopStock = dataManager.getShopStock();
        currentShopItemContainer = targetedShopStock.GetItemContainerByIndex(currentItemIndex);
    }

    /**
     * Itnitializes controller with no guiManager
     */
    public Controller(){
        this(null);
    }

    /**
     * Tries to buy an item with current index using DataManager method TryToBuyItem. According to the result, the appropriate message is displayed by GuiManager.
     * If item cannot be bought, appropriate ItemBuyException will be thrown.
     */
    public void TryToBuyItem() {
        try {
            ShopItemContainer itemContainer = targetedShopStock.getItemDatabase().get(currentItemIndex);
            dataManager.BuyAnItem(itemContainer.getShopItem());
            guiManager.SetCashLabel(dataManager.getUserData().getCash());
            guiManager.SetDisplayedItem(itemContainer);
            guiManager.DisplayInfoText(itemContainer.getShopItem().getName() + " bought", 3);
        } catch (NotEnoughMoneyException | ItemNotInStockException | ItemNotInDatabaseException ex) {
            guiManager.DisplayInfoText("Error: " + ex.getMessage(), 3);
        }
    }

    /**
     * Changes selected item to next/previous one.
     * @param nextItem True for next item, false for previous one
     * @return Newly selected shop item
     */
    public ShopItemContainer ChangeItem(boolean nextItem){
        currentItemIndex = nextItem ?
                currentItemIndex == targetedShopStock.getItemDatabase().size() - 1 ? 0 :
                currentItemIndex + 1 : currentItemIndex == 0 ? targetedShopStock.getItemDatabase().size() - 1 : currentItemIndex - 1;
        guiManager.SetPageNumber(currentItemIndex + 1, targetedShopStock.getItemDatabase().size());
        return targetedShopStock.GetItemContainerByIndex(currentItemIndex);
    }

    /**
     * Selects given shop item
     * @param item Item to select
     */
    public void ShowSpecificItem(ShopItem item){
        if (item == null || targetedShopStock == null)
            return;
        try {
            currentShopItemContainer = targetedShopStock.GetItemContainerInDatabase(item);
            currentItemIndex = targetedShopStock.GetItemIndex(item);
            guiManager.SetPageNumber(currentItemIndex + 1, targetedShopStock.getItemDatabase().size());
            guiManager.SetDisplayedItem(currentShopItemContainer);
        } catch (ItemNotInDatabaseException e) {
            return;
        }
    }

    /**
     * Changes sorting of items in database
     * @param newSortingOrder New order of sorting
     */
    public void ChangeSorting(SortingOrder newSortingOrder){
        if(currentSortingOrder == newSortingOrder)
            return;
        currentSortingOrder = newSortingOrder;
        targetedShopStock =
                newSortingOrder == SortingOrder.ASCENDING ?
                dataManager.GetSortedShopStock(true) :
                newSortingOrder == SortingOrder.DESCENDING ?
                dataManager.GetSortedShopStock(false) :
                dataManager.getShopStock();
        guiManager.SetPageNumber(currentItemIndex + 1, targetedShopStock.getItemDatabase().size());
        guiManager.SetDisplayedItem(targetedShopStock.getItemDatabase().get(currentItemIndex));
    }
}

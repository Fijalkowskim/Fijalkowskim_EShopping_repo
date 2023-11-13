package fijalkowskim.fijalkowskim_eshopping.controller;

import fijalkowskim.fijalkowskim_eshopping.model.*;
import fijalkowskim.fijalkowskim_eshopping.view.GuiManager;

/**
 * Main controller of the program. It is the only class used in main method.
 * @author Fijalkowskim
 * @version %I%, %G%
 */
public class Controller {
    /**
     * Manager handling all application data.
     */
    DataManager dataManager;

    public DataManager getDataManager() {
        return dataManager;
    }

    /**
     * Manager handling displayed content of application and user input.
     */
    GuiManager guiManager;

    public GuiManager getGuiManager() {
        return guiManager;
    }
    /**
     * Index of currently selected shop item.
     */
    int currentItemIndex;
    public int getCurrentItemIndex(){
        return currentItemIndex;
    }
    /**
     * Shop stock that is already spectated (can be changed when sorting is used).
     */
    ShopStock targetedShopStock;
    public ShopStock getTargetedShopStock(){
        return targetedShopStock;
    }
    ShopItemContainer currentShopItemContainer;
    public ShopItemContainer getCurrentShopItemContainer() {
        return currentShopItemContainer;
    }
    SortingOrder currentSortingOrder;
    /**
     * Construction initialises managers and currentItemIndex
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
     * Tries to buy an item with current index using DataManager method TryToBuyItem. According to the result, the appropriate message is displayed by GuiManager.
     * If item cannot be bought, appropriate ItemBuyException will be thrown.
     */
    public void TryToBuyItem() {
        try {
            dataManager.BuyAnItem(targetedShopStock.getItemDatabase().get(currentItemIndex).getShopItem());
            guiManager.SetCashLabel(dataManager.getUserData().getCash());
            guiManager.SetDisplayedItem(targetedShopStock.getItemDatabase().get(currentItemIndex));
            //guiManager.DisplayMessage("Item bought successfully");
        } catch (NotEnoughMoneyException | ItemNotInStockException | ItemNotInDatabaseException ex) {
            //guiManager.DisplayMessage("Error: " + ex.getMessage());
        }
    }
    public ShopItemContainer ChangeItem(boolean nextItem){
        currentItemIndex = nextItem ?
                currentItemIndex == targetedShopStock.getItemDatabase().size() - 1 ? 0 :
                currentItemIndex + 1 : currentItemIndex == 0 ? targetedShopStock.getItemDatabase().size() - 1 : currentItemIndex - 1;
        guiManager.SetPageNumber(currentItemIndex + 1, targetedShopStock.getItemDatabase().size());
        return targetedShopStock.GetItemContainerByIndex(currentItemIndex);
    }
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

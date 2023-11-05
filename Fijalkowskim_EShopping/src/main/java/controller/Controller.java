package controller;

import model.*;
import view.GuiManager;

import java.util.List;

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
    /**
     * Manager handling displayed content of application and user input.
     */
    GuiManager guiManager;
    /**
     * Index of currently selected shop item.
     */
    int currentItemIndex;
    /**
     * Shop stock that is already spectated (can be changed when sorting is used).
     */
    ShopStock targetedShopStock;

    /**
     * Construction initialises managers and currentItemIndex
     */
    public Controller() {
        this.dataManager = new DataManager();
        this.guiManager = new GuiManager();
        dataManager.LoadShopStock();
        currentItemIndex = 0;
        targetedShopStock = dataManager.getShopStock();

    }

    /**
     * Method responsible for handling application logic such as using guiManager for displaying content and handling user input.
     * This method should be used every time the displayed content is changed.
     */
    public void MainLoop() {
        ShopItemContainer currentItem = targetedShopStock.GetItemContainerByIndex(currentItemIndex);
        guiManager.DisplayMainMenu(dataManager.getUserData(), currentItem, currentItemIndex + 1, targetedShopStock.getItemDatabase().size());
        HandleUserInput(guiManager.GetUserInput());

    }

    /**
     * Tries to buy an item with current index using DataManager method TryToBuyItem. According to the result, the appropriate message is displayed by GuiManager.
     * If item cannot be bought, appropriate ItemBuyException will be thrown.
     */
    void TryToBuyItem() {
        try {
            dataManager.BuyAnItem(dataManager.getShopStock().getItemDatabase().get(currentItemIndex).getShopItem());
            guiManager.DisplayMessage("Item bought successfully");
        } catch (NotEnoughMoneyException | ItemNotInStockException | ItemNotInDatabaseException ex) {
            guiManager.DisplayMessage("Error: " + ex.getMessage());
        }
    }


    /**
     * Handles logic of user's input
     *
     * @param inputAction Type of action user is calling
     */
    void HandleUserInput(GuiManager.InputAction inputAction) {
        switch (inputAction) {
            case QUIT:
                return;
            case NEXT_ITEM:
                currentItemIndex = currentItemIndex == targetedShopStock.getItemDatabase().size() - 1 ? 0 : currentItemIndex + 1;
                break;
            case PREVIOUS_ITEM:
                currentItemIndex = currentItemIndex == 0 ? targetedShopStock.getItemDatabase().size() - 1 : currentItemIndex - 1;
                break;
            case BUY_ITEM:
                TryToBuyItem();
                break;
            case NONE:
                break;
            case SORT_ASCENDING:
                targetedShopStock = dataManager.GetSortedShopStock(true);
                break;
            case SORT_DESCENDING:
                targetedShopStock = dataManager.GetSortedShopStock(false);
                break;
            case RESET_SORTING:
                targetedShopStock = dataManager.getShopStock();
                break;
        }
        MainLoop();
    }
}

package controller;

import model.DataManager;
import model.ItemNotInStockException;
import model.NotEnoughMoneyException;
import view.GuiManager;

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
     * Construction initialises managers and currentItemIndex
     */
    public Controller() {
        this.dataManager = new DataManager();
        this.guiManager = new GuiManager();
        currentItemIndex = 0;
    }

    /**
     * Method responsible for handling application logic such as using guiManager for displaying content and handling user input.
     * This method should be used every time the displayed content is changed.
     */
    public void MainLoop() {
        guiManager.DisplayMainMenu(dataManager.getUserData(), dataManager.GetItemContainerByIndex(currentItemIndex));
        HandleUserInput(guiManager.GetUserInput());

    }

    /**
     * Tries to buy an item with current index using DataManager method TryToBuyItem. According to the result, the appropriate message is displayed by GuiManager.
     * If item cannot be bought, appropriate ItemBuyException will be thrown.
     */
    void TryToBuyItem() {
        try {
            dataManager.BuyAnItem(currentItemIndex);
            guiManager.DisplayMessage("Item bought successfully");
        } catch (NotEnoughMoneyException | ItemNotInStockException ex) {
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
                break;
            case NEXT_ITEM:
                currentItemIndex = currentItemIndex == dataManager.getShopStock().getItemDatabase().size() - 1 ? 0 : currentItemIndex + 1;
                MainLoop();
                break;
            case PREVIOUS_ITEM:
                currentItemIndex = currentItemIndex == 0 ? dataManager.getShopStock().getItemDatabase().size() - 1 : currentItemIndex - 1;
                MainLoop();
                break;
            case BUY_ITEM:
                TryToBuyItem();
                MainLoop();
                break;
            case NONE:
                MainLoop();
                break;

        }
    }
}

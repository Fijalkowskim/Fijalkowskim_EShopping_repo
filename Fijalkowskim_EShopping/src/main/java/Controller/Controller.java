package Controller;

import Model.DataManager;
import View.GuiManager;

import java.io.IOException;
import java.util.Scanner;

public class Controller {
    DataManager dataManager;
    GuiManager guiManager;

    Scanner scanner;
    int currentItemIndex;
    public Controller()
    {
        this.dataManager = new DataManager();
        this.guiManager = new GuiManager();

        scanner = new Scanner(System.in);
        currentItemIndex = 0;
    }
    public void MainLoop()
    {
        guiManager.DisplayMainMenu(dataManager.getUserData(), dataManager.getCurrentItem(currentItemIndex));
        HandleUserInput(guiManager.GetUserInput());

    }

    void TryToBuyItem()
    {
        try {
            DataManager.ItemBuyResult result = dataManager.TryToBuyItem(currentItemIndex);
            if(result == DataManager.ItemBuyResult.BOUGHT)
            {
                guiManager.DisplayMessage("Item bought successfully");
            }
            else
            {
                switch (result)
                {
                    case NOT_ENOUGH_MONEY:
                        throw new ItemBuyException("Not enough money");
                    case ITEM_NOT_IN_STOCK:
                        throw new ItemBuyException("Item is not in stock");
                }

            }
        }catch(ItemBuyException err)
        {
            guiManager.DisplayMessage("Error: " + err.getMessage());
        }
    }

    void HandleUserInput(GuiManager.InputAction inputAction)
    {
        switch (inputAction)
        {
            case QUIT:
                break;
            case NEXT_ITEM:
                currentItemIndex = currentItemIndex == dataManager.getShopStock().getItemsInStock().size() - 1 ? 0 : currentItemIndex + 1;
                MainLoop();
                break;
            case PREVIOUS_ITEM:
                currentItemIndex = currentItemIndex == 0 ? dataManager.getShopStock().getItemsInStock().size() - 1 : currentItemIndex - 1;
                MainLoop();
                break;
            case BUY:
                TryToBuyItem();
                MainLoop();
                break;
            case NONE:
                MainLoop();
                break;

        }
    }




}

package Controller;

import Model.Model;
import View.View;

import java.io.IOException;
import java.util.Scanner;

public class Controller {
    Model model;
    View view;
    public enum InputAction {QUIT, NEXT_ITEM, PREVIOUS_ITEM, BUY, NONE};
    Scanner scanner;
    int currentItemIndex;
    public Controller()
    {
        this.model = new Model();
        this.view = new View();

        scanner = new Scanner(System.in);
        currentItemIndex = 0;
    }
    public void MainLoop()
    {
        view.DisplayMainMenu(model.getUserData(),model.getCurrentItem(currentItemIndex));
        HandleUserInput(GetUserInput());

    }
    InputAction GetUserInput()
    {
        InputAction returnAction = InputAction.NONE;
        try {
            int userInput = System.in.read();
            switch(userInput)
            {
                case 'a':
                    returnAction = InputAction.PREVIOUS_ITEM;
                    break;
                case 'd':
                    returnAction = InputAction.NEXT_ITEM;
                    break;
                case 'b':
                    returnAction = InputAction.BUY;
                    break;
                case 'q':
                    returnAction = InputAction.QUIT;
                    break;
                default:

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return returnAction;
    }
    void HandleUserInput(InputAction inputAction)
    {
        switch (inputAction)
        {
            case QUIT:
                break;
            case NEXT_ITEM:
                currentItemIndex = currentItemIndex == model.getShopStock().getItemsInStock().size() - 1 ? 0 : currentItemIndex + 1;
                MainLoop();
                break;
            case PREVIOUS_ITEM:
                currentItemIndex = currentItemIndex == 0 ? model.getShopStock().getItemsInStock().size() - 1 : currentItemIndex - 1;
                MainLoop();
                break;
            case BUY:
                if(model.TryToBuyItem(currentItemIndex))
                    view.DisplayItemInteraction(View.ItemInteraction.BOUGHT);
                else
                    view.DisplayItemInteraction(View.ItemInteraction.NOT_ENOUGH_MONEY);

                MainLoop();
                break;
            case NONE:
                MainLoop();
                break;

        }
    }




}

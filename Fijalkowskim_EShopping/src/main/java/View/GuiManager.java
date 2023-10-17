package View;

import Controller.Controller;
import Model.ShopItemContainer;
import Model.UserData;
import java.io.IOException;
import java.util.Scanner;
public class GuiManager {
    public enum InputAction {QUIT, NEXT_ITEM, PREVIOUS_ITEM, BUY, NONE};
    Scanner scanner;
    public GuiManager() {
        scanner = new Scanner(System.in);
    }

    public void DisplayMainMenu(UserData userData, ShopItemContainer currentItem)
    {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Your cash: " + userData.getCash());
        System.out.println("------------------------------------------------------");
        System.out.println("Press 'a' to scroll left | Press 'd' to scroll right | Press 'b' to buy an item| Press 'q' to quit");
        System.out.println("------------------------------------------------------");
        if(currentItem != null)
        {
            System.out.println("Item: " + currentItem.getShopItem().getName());
            System.out.println("Price: " + currentItem.getShopItem().getPrice() + "$");
            System.out.println("In stock: " + currentItem.getCount());
        }
        else
        {
            System.out.println("No items available in shop");
        }
        System.out.println("--------------------------------------------------------------------------------");

    }
    public void DisplayMessage(String message) {
        System.out.println("#############################\n");
        System.out.println(message);
        System.out.println("\n#############################");
        System.out.println("PRESS ANY KEY AND CLICK ENTER TO CONTINUE");
        scanner.next();
    }
    public InputAction GetUserInput()
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


}

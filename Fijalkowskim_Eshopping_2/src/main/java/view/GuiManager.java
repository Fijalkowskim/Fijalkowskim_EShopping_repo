package view;

import model.ShopItemContainer;
import model.UserData;

import java.io.IOException;
import java.util.Scanner;
/**
 * Class responsible for displaying content and getting user input.
 * @author Fijalkowskim
 * @version %I%, %G%
 */
public class GuiManager {
    /**
     * Type of available actions caused by user input.
     */
    public enum InputAction {QUIT, NEXT_ITEM, PREVIOUS_ITEM, BUY_ITEM, NONE, SORT_ASCENDING, SORT_DESCENDING, RESET_SORTING};
    Scanner scanner;

    /**
     * Initialises scanner for user input.
     */
    public GuiManager() {
        scanner = new Scanner(System.in);
    }

    /**
     * Displays content of application's main menu such as instructions, user's cash and currently selected item.
     * @param userData Data of the user.
     * @param currentItem Currently selected item to display.
     * @param itemListIndex Index in list of current item.
     * @param numberOfItems Total number of items in list.
     */
    public void DisplayMainMenu(UserData userData, ShopItemContainer currentItem, int itemListIndex, int numberOfItems) {
        System.out.println("\n\n\n\n\n------------------------------------------------------");
        System.out.println("Your cash: " + userData.getCash());
        System.out.println("------------------------------------------------------");
        if(currentItem != null)
        {
            System.out.println("Item: " + currentItem.getShopItem().getName());
            System.out.println("Price: " + currentItem.getShopItem().getPrice() + "$");
            System.out.println("In stock: " + currentItem.getCount());
        }
        else
        {
            System.out.println("No items available");
        }
        System.out.println("------------------------------------------------------");
        System.out.println("Item " + itemListIndex + "/" + numberOfItems);
        System.out.println("--------------------------------------------------------------------------------------------------");

        System.out.println("Press 'a' to scroll left | Press 'd' to scroll right | Press 'b' to buy an item| Press 'q' to quit");
        System.out.println("Press 'e' to sort ascending | Press 'r' to reset sorting | Press 't' to sort descending");
    }

    /**
     * Displays given message.
     * @param message Any string message.
     */
    public void DisplayMessage(String message) {
        System.out.println("\n\n\n\n\n#############################\n");
        System.out.println(message);
        System.out.println("\n#############################");
        System.out.println("PRESS ANY KEY AND CLICK ENTER TO CONTINUE");
        scanner.next();
    }

    /**
     * Waits for user input and returns appropriate action.
     * @return Input action.
     */
    public InputAction GetUserInput() {
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
                    returnAction = InputAction.BUY_ITEM;
                    break;
                case 'q':
                    returnAction = InputAction.QUIT;
                    break;
                case 'e':
                    returnAction = InputAction.SORT_ASCENDING;
                    break;
                case 'r':
                    returnAction = InputAction.RESET_SORTING;
                    break;
                case 't':
                    returnAction = InputAction.SORT_DESCENDING;
                    break;
                default:

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return returnAction;
    }
}

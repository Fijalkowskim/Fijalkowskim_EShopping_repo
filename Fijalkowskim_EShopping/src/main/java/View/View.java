package View;

import Model.ShopItemContainer;
import Model.UserData;

import java.util.Scanner;



public class View {

    public enum ItemInteraction {BOUGHT, NOT_ENOUGH_MONEY}
    Scanner scanner;
    public View() {
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
    public void DisplayItemInteraction(ItemInteraction itemInteraction)
    {
        System.out.println("#############################");
        switch (itemInteraction)
        {
            case BOUGHT:
                System.out.println("ITEM BOUGHT!");

                break;
            case NOT_ENOUGH_MONEY:
                System.out.println("NOT ENOUGH MONEY!");

                    break;
        }
        System.out.println("#############################");
        scanner.next();


    }


}

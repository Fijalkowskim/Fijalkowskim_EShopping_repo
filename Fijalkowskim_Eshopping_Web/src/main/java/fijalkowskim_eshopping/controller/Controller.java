package fijalkowskim_eshopping.controller;

import fijalkowskim_eshopping.model.*;

import java.util.Dictionary;
import java.util.Map;

/**
 * Main controller of the program. It is the only class used in main method.
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

    public static Controller getInstance() {
        return INSTANCE;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public int getCurrentItemIndex() {
        return currentItemIndex;
    }

    public ShopStock getTargetedShopStock() {
        return targetedShopStock;
    }

    public ShopItemContainer getCurrentShopItemContainer() {
        return currentShopItemContainer;
    }

    public void TryToBuyItem() throws NotEnoughMoneyException,ItemNotInStockException,ItemNotInDatabaseException {
        try {
            ShopItemContainer itemContainer = targetedShopStock.getItemDatabase().get(currentItemIndex);
            dataManager.BuyAnItem(itemContainer.getShopItem());
        } catch (NotEnoughMoneyException ex) {
            throw new NotEnoughMoneyException("Not enough money");
        }catch(ItemNotInStockException ex){
            throw new ItemNotInStockException("Item is not in stock");
        }catch(ItemNotInDatabaseException ex) {
            throw new ItemNotInDatabaseException("Item is not in database");
        }
    }

    public ShopItemContainer ChangeItem(boolean nextItem) {
        currentItemIndex = nextItem ?
                currentItemIndex == targetedShopStock.getItemDatabase().size() - 1 ? 0 :
                        currentItemIndex + 1 : currentItemIndex == 0 ? targetedShopStock.getItemDatabase().size() - 1 : currentItemIndex - 1;
        currentShopItemContainer = targetedShopStock.GetItemContainerByIndex(currentItemIndex);
        return targetedShopStock.GetItemContainerByIndex(currentItemIndex);
    }

    public void ShowSpecificItem(ShopItem item) {
        if (item == null || targetedShopStock == null)
            return;
        try {
            currentShopItemContainer = targetedShopStock.GetItemContainerInDatabase(item);
            currentItemIndex = targetedShopStock.GetItemIndex(item);
        } catch (ItemNotInDatabaseException e) {
            // Obsługa wyjątku lub logowanie
        }
    }

    public void ChangeSorting(SortingOrder newSortingOrder) {
        if (currentSortingOrder == newSortingOrder)
            return;
        currentSortingOrder = newSortingOrder;
        targetedShopStock =
                newSortingOrder == SortingOrder.ASCENDING ?
                        dataManager.GetSortedShopStock(true) :
                        newSortingOrder == SortingOrder.DESCENDING ?
                                dataManager.GetSortedShopStock(false) :
                                dataManager.getShopStock();
    }
    public void LoadSavedData(float savedCash, Map<Integer, Integer> savedItemsAmount, int savedPageIndex){
            if(savedCash >= 0)
                dataManager.getUserData().setCash(savedCash);
            if(savedPageIndex >= 0 && savedPageIndex < targetedShopStock.getItemDatabase().size())
            {
                currentItemIndex = savedPageIndex;
                currentShopItemContainer = targetedShopStock.GetItemContainerByIndex(currentItemIndex);
            }

            if(savedItemsAmount != null){
                for (Map.Entry<Integer, Integer> entry : savedItemsAmount.entrySet()){
                    targetedShopStock.SetItemCount(entry.getKey(), entry.getValue());
                }
            }
    }


}
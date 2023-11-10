package fijalkowskim.fijalkowskim_eshopping_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import java.net.URL;

public class HelloController {
    /**
     * Manager handling all application data.
     */
    public DataManager dataManager;


    ShopItemContainer currentItem;
    int currentItemIndex;
    /**
     * Shop stock that is already spectated (can be changed when sorting is used).
     */
    ShopStock targetedShopStock;


    /**
     * Tries to buy an item with current index using DataManager method TryToBuyItem. According to the result, the appropriate message is displayed by GuiManager.
     * If item cannot be bought, appropriate ItemBuyException will be thrown.
     */
    void TryToBuyItem() {
        try {
            dataManager.BuyAnItem(currentItem.getShopItem());
            SetCashLabel(dataManager.getUserData().getCash());
            SetDisplayedItem(currentItem);
            infoLabel.setText("Item bought successfully");
        } catch (NotEnoughMoneyException | ItemNotInStockException | ItemNotInDatabaseException ex) {
            infoLabel.setText("Error: " + ex.getMessage());

        }
    }
    /*public HelloController(){
        this(0);
    }
    public HelloController(float startCash) {

        this.dataManager = new DataManager();
        dataManager.LoadShopStock();
        currentItemIndex = 0;
        targetedShopStock = dataManager.getShopStock();
        dataManager.getUserData().setCash(startCash);
    }*/
    @FXML
    private Label cash;
    @FXML
    private Label itemName;
    @FXML
    private Label itemDescription;
    @FXML
    private Label itemPrice;
    @FXML
    private Label itemAmount;
    @FXML
    private Label infoLabel;
    @FXML
    private ImageView itemImage;
    @FXML
    private void initialize() {
        this.dataManager = new DataManager();
        dataManager.LoadShopStock();
        currentItemIndex = 0;
        targetedShopStock = dataManager.getShopStock();
        dataManager.getUserData().setCash(1000);
        SetCashLabel(dataManager.getUserData().getCash());
        currentItem = dataManager.getShopStock().GetItemContainerByIndex(currentItemIndex);
        SetDisplayedItem(currentItem);
        infoLabel.setText("");
    }
    @FXML
    public void previousButtonClick(ActionEvent actionEvent) {
        currentItemIndex = currentItemIndex == 0 ? targetedShopStock.getItemDatabase().size() - 1 : currentItemIndex - 1;
        currentItem = dataManager.getShopStock().GetItemContainerByIndex(currentItemIndex);
        SetDisplayedItem(currentItem);

    }
    @FXML
    public void nextButtonClick(ActionEvent actionEvent) {
        currentItemIndex = currentItemIndex == targetedShopStock.getItemDatabase().size() - 1 ? 0 : currentItemIndex + 1;
        currentItem = dataManager.getShopStock().GetItemContainerByIndex(currentItemIndex);
        SetDisplayedItem(currentItem);
    }
    @FXML
    public void buyButtonClick(ActionEvent actionEvent) {
        TryToBuyItem();
    }
    public void SetCashLabel(float newCash){
        this.cash.setText("Your cash: " + String.format("%.02f", newCash) + "zł");
    }
    public void SetDisplayedItem(ShopItemContainer newItem){
        itemName.setText(newItem.getShopItem().getName());
        itemDescription.setText(newItem.getShopItem().getDescription());
        itemPrice.setText(Float.toString(newItem.getShopItem().getPrice()) + "zł");
        itemAmount.setText("Items available: " + Integer.toString(newItem.getCount()));
        Image image= new Image(HelloController.class.getResource(newItem.getShopItem().getImageURL()).toString());
        itemImage.setImage(image);
    }
    public void SetCurrentItemData(){

    }

}
package fijalkowskim.fijalkowskim_eshopping.view;

import fijalkowskim.fijalkowskim_eshopping.controller.Controller;
import fijalkowskim.fijalkowskim_eshopping.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * Class responsible for displaying content and getting user input.
 * @author Fijalkowskim
 * @version 1.1
 */
public class GuiManager {
    @FXML
    public Text pageNumber;
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
    private ImageView itemImage;

    private Controller controller;
    Image defaultItemImage;
    @FXML
    private void initialize() {
        cash.setText("");
        itemName.setText("");
        itemDescription.setText("");
        itemPrice.setText("");
        itemAmount.setText("");
        pageNumber.setText("");
        itemImage.setImage(null);
        defaultItemImage = new Image(GuiManager.class.getResource("noItem.png").toString());
    }
    public void setController(Controller controller){
        this.controller = controller;
        SetCashLabel(controller.getDataManager().getUserData().getCash());
        SetDisplayedItem(controller.getCurrentShopItemContainer());
        SetPageNumber(controller.getCurrentItemIndex() + 1, controller.getTargetedShopStock().getItemDatabase().size());
    }
    @FXML
    public void previousButtonClick(ActionEvent actionEvent) {
        SetDisplayedItem(controller.ChangeItem(false));
    }
    @FXML
    public void nextButtonClick(ActionEvent actionEvent) {

        SetDisplayedItem(controller.ChangeItem(true));
    }
    @FXML
    public void buyButtonClick(ActionEvent actionEvent) {
        controller.TryToBuyItem();
    }

    public void SetCashLabel(float newCash){
        this.cash.setText(String.format("%.02f", newCash) + "zł");
    }
    public void SetPageNumber(int page, int NOPages){
        pageNumber.setText(Integer.toString(page) + "/" + Integer.toString(NOPages));
    }
    public void SetDisplayedItem(ShopItemContainer newItem){
        itemName.setText(newItem.getShopItem().getName());
        itemDescription.setText(newItem.getShopItem().getDescription());
        itemPrice.setText(Float.toString(newItem.getShopItem().getPrice()) + "zł");
        itemAmount.setText(Integer.toString(newItem.getCount()) + " IN STOCK");
        itemImage.setImage(newItem.getShopItem().getImage() == null ? defaultItemImage : newItem.getShopItem().getImage());
    }

}
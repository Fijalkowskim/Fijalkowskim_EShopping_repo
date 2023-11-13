package fijalkowskim.fijalkowskim_eshopping.view;

import fijalkowskim.fijalkowskim_eshopping.controller.Controller;
import fijalkowskim.fijalkowskim_eshopping.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class responsible for displaying content and getting user input.
 * @author Fijalkowskim
 * @version 1.1
 */
public class GuiManager {
    @FXML
    public Text pageNumber;
    @FXML
    public ListView<ShopItem> itemList;
    @FXML
    public ChoiceBox<SortingOrder> sortChoiceBox;
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

        SetListViewDisplay();
        SetSortingChoiceBox();
    }


    public void setController(Controller controller){
        this.controller = controller;
        SetCashLabel(controller.getDataManager().getUserData().getCash());
        SetDisplayedItem(controller.getCurrentShopItemContainer());
        SetPageNumber(controller.getCurrentItemIndex() + 1, controller.getTargetedShopStock().getItemDatabase().size());

        List<ShopItem> items = controller.getTargetedShopStock().getItemDatabase().stream()
                .map(ShopItemContainer::getShopItem)
                .collect(Collectors.toList());

        ObservableList<ShopItem> observableItems = FXCollections.observableArrayList(items);
        itemList.setItems(observableItems);
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
    @FXML
    public void changeSorting(ActionEvent event){
        controller.ChangeSorting(sortChoiceBox.getValue());
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
    void ItemSelectedFromListVew(ShopItem item){
        if(controller == null)
            return;
        controller.ShowSpecificItem(item);
    }
    void SetListViewDisplay(){
        itemList.setCellFactory(param -> new TextFieldListCell<>(new StringConverter<>() {
            @Override
            public String toString(ShopItem item) {
                return item.getName() + " - " + item.getPrice() + "zł";
            }
            @Override
            public ShopItem fromString(String string) {
                return null;
            }
        }));

        itemList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ItemSelectedFromListVew(newValue);
            }
        });
        itemList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ShopItem selectedItem = (ShopItem) itemList.getSelectionModel().getSelectedItem();

                if (selectedItem != null) {
                    ItemSelectedFromListVew(selectedItem);
                }
            }
        });
    }
    void SetSortingChoiceBox(){
        sortChoiceBox.setItems(FXCollections.observableArrayList(SortingOrder.values()));
        sortChoiceBox.setValue(SortingOrder.NO_SORTING);
        sortChoiceBox.setOnAction(this::changeSorting);
    }

}
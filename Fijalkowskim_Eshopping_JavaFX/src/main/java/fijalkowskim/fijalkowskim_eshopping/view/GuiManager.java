package fijalkowskim.fijalkowskim_eshopping.view;

import fijalkowskim.fijalkowskim_eshopping.controller.Controller;
import fijalkowskim.fijalkowskim_eshopping.model.*;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Class responsible for displaying content and interacting with user.
 * @author Fijalkowskim
 * @version 2.0
 */
public class GuiManager {
    @FXML
    private Text pageNumber;
    @FXML
    private ListView<ShopItem> itemList;
    @FXML
    private ChoiceBox<SortingOrder> sortChoiceBox;
    @FXML
    private Button previousItemBtn;
    @FXML
    private Button nextItemBtn;
    @FXML
    private Button buyButton;
    @FXML
    private Text infoText;
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
    Timeline infoTextAnimation;
    Scene scene;
    /**
     * Initializes the GUI manager.
     * @param scene The JavaFX scene associated with this manager.
     */
    @FXML
    public void initialize(Scene scene) {
        this.scene = scene;

        cash.setText("");
        itemName.setText("");
        itemDescription.setText("");
        itemPrice.setText("");
        itemAmount.setText("");
        pageNumber.setText("");
        infoText.setText("");

        itemImage.setImage(null);
        defaultItemImage = new Image(Objects.requireNonNull(GuiManager.class.getResource("noItem.png")).toString());

        SetListViewDisplay();
        SetSortingChoiceBox();
        SetKeyBinds();
    }
    /**
     * Sets the key bindings for various actions in the GUI.
     */
    void SetKeyBinds(){
        scene.addMnemonic(new Mnemonic(nextItemBtn, new KeyCodeCombination(KeyCode.D, KeyCombination.ALT_DOWN)));
        scene.addMnemonic(new Mnemonic(previousItemBtn, new KeyCodeCombination(KeyCode.A, KeyCombination.ALT_DOWN)));
        scene.addMnemonic(new Mnemonic(buyButton, new KeyCodeCombination(KeyCode.B, KeyCombination.ALT_DOWN)));
        scene.addMnemonic(new Mnemonic(itemList, new KeyCodeCombination(KeyCode.L, KeyCombination.ALT_DOWN)));

        scene.setOnKeyPressed(event -> {
            if (new KeyCodeCombination(KeyCode.S, KeyCombination.ALT_DOWN).match(event)) {
                sortChoiceBox.show();
            }
        });
    }
    /**
     * Sets the controller for the GUI manager.
     * @param controller The controller to be set.
     */
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
    /**
     * Handles the action when the "Previous" button is clicked.
     * @param actionEvent The event triggered by clicking the button.
     */
    @FXML
    public void previousButtonClick(ActionEvent actionEvent) {
        SetDisplayedItem(controller.ChangeItem(false));
    }
    /**
     * Handles the action when the "Next" button is clicked.
     * @param actionEvent The event triggered by clicking the button.
     */
    @FXML
    public void nextButtonClick(ActionEvent actionEvent) {
        SetDisplayedItem(controller.ChangeItem(true));
    }
    /**
     * Handles the action when the "Buy" button is clicked.
     * @param actionEvent The event triggered by clicking the button.
     */
    @FXML
    public void buyButtonClick(ActionEvent actionEvent) {
        controller.TryToBuyItem();
    }
    /**
     * Handles the action when the sorting choice changes.
     * @param event The event triggered by changing the sorting choice.
     */
    @FXML
    public void changeSorting(ActionEvent event){
        controller.ChangeSorting(sortChoiceBox.getValue());
    }
    /**
     * Sets the cash label to display the user's cash.
     * @param newCash The new cash value to be displayed.
     */
    public void SetCashLabel(float newCash){
        this.cash.setText(String.format("%.02f", newCash) + "$");
    }
    /**
     * Sets the page number label to display the current page and total number of pages.
     * @param page The current page.
     * @param NOPages The total number of pages.
     */
    public void SetPageNumber(int page, int NOPages){
        pageNumber.setText(Integer.toString(page) + "/" + Integer.toString(NOPages));
    }
    /**
     * Sets the displayed item details in the GUI.
     * @param newItem The new item to be displayed.
     */
    public void SetDisplayedItem(ShopItemContainer newItem){
        itemName.setText(newItem.getShopItem().getName());
        itemDescription.setText(newItem.getShopItem().getDescription());
        itemPrice.setText(Float.toString(newItem.getShopItem().getPrice()) + "$");
        itemAmount.setText(Integer.toString(newItem.getCount()) + " IN STOCK");
        itemImage.setImage(newItem.getShopItem().getImage() == null ? defaultItemImage : newItem.getShopItem().getImage());
    }
    /**
     * Displays information text with a specified display time.
     * @param info The information text to be displayed.
     * @param displayTime The time in seconds for which the information text will be displayed.
     */
    public void DisplayInfoText(String info, float displayTime) {
        if(infoTextAnimation != null)
            infoTextAnimation.stop();
        infoText.setScaleX(0);
        infoText.setScaleY(0);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.1f), infoText);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);

        infoTextAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    infoText.setText(info);
                    infoText.setVisible(true);
                    scaleTransition.playFromStart();
                }),
                new KeyFrame(Duration.seconds(displayTime), e -> {
                    infoText.setVisible(false);
                    infoText.setText("");
                })
        );

        infoTextAnimation.setCycleCount(1);
        infoTextAnimation.play();
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
                return item.getName() + " - " + item.getPrice() + "$";
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
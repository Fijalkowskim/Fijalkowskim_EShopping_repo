module fijalkowskim.fijalkowskim_eshopping {
    requires javafx.controls;
    requires javafx.fxml;


    opens fijalkowskim.fijalkowskim_eshopping to javafx.fxml;
    opens fijalkowskim.fijalkowskim_eshopping.controller to javafx.fxml;
    opens fijalkowskim.fijalkowskim_eshopping.view to javafx.fxml;
    opens fijalkowskim.fijalkowskim_eshopping.model to javafx.fxml;
    exports fijalkowskim.fijalkowskim_eshopping;
    exports fijalkowskim.fijalkowskim_eshopping.controller;
    exports fijalkowskim.fijalkowskim_eshopping.view;
    exports fijalkowskim.fijalkowskim_eshopping.model;
}

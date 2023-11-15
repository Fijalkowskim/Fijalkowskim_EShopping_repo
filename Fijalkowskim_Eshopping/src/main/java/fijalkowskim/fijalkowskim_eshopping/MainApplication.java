package fijalkowskim.fijalkowskim_eshopping;

import fijalkowskim.fijalkowskim_eshopping.controller.Controller;
import fijalkowskim.fijalkowskim_eshopping.view.GuiManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class of the application.
 * @author Fijalkowskim
 * @version 2.0
 */
public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 900);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        GuiManager guiManager  = fxmlLoader.getController();
        Controller controller = new Controller(guiManager);
        guiManager.initialize(scene);

        if(getParameters() != null && !getParameters().getRaw().isEmpty()) {
            try{
                float startCash = Float.parseFloat(getParameters().getRaw().get(0));
                controller.getDataManager().getUserData().setCash(startCash);
            }
            catch(Exception ex){}
        }
        else
            controller.getDataManager().getUserData().setCash(1000);
        guiManager.setController(controller);
    }

    public static void main(String[] args) {
        launch();
    }
}
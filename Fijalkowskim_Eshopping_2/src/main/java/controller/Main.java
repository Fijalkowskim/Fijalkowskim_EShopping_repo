package controller;

import fijalkowskim.fijalkowskim_eshopping_2.HelloController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class of the application.
 * @author Fijalkowskim
 * @version %I%, %G%
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Main method interacting only with Controller class.
     */
    public static void main(String[] args)
    {

        /*HelloController controller;

        if(args != null && args.length>0) {
            try{
            float startCash = Float.parseFloat(args[0]);
            controller = new HelloController(startCash);
            }
            catch(Exception ex){controller = new HelloController();}
        }
        else
            controller = new HelloController(500f);
        launch();*/
    }



}
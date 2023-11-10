package fijalkowskim.fijalkowskim_eshopping_2;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        /*HelloController controller;

        if(args != null && args.length>0) {
            try{
                float startCash = Float.parseFloat(args[0]);
                controller = new HelloController(startCash);
            }
            catch(Exception ex){controller=new HelloController();}
        }
        else
            controller = new HelloController(400f);

        controller.MainLoop();*/
        launch();
    }
}
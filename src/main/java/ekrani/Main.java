package main.java.ekrani;

import hr.java.covidportal.model.Simptom;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main klasa JavaFX-a
 */
public class Main extends Application {

    private static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("pocetniEkran.fxml"));
        primaryStage.setTitle("CovidPortal");
        primaryStage.setScene(new Scene(root, 768, 432));
        primaryStage.show();
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage newStage) {
        mainStage = newStage;
    }

    public static void setNewScene(Scene scene) {
        mainStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }

}

package ics.upjs.sk.spravaucebni.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainMenuSceneController {
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button pridatPouzivatelaButton;

    @FXML
    private Button upravitPouzivatelaButton;

    @FXML
    private Button upravitUcebnuButton;

    @FXML
    void initialize() {
       
//        pridatPouzivatelaButton.setOnAction(eh -> {
//             NovyPouzivatelSceneController controller = new NovyPouzivatelSceneController();
//             nextWindow(controller,"NovyPouzivatelScene.fxml", "Vytvorenie nového použivateľa");
//        });
        upravitPouzivatelaButton.setOnAction(eh -> {
            VyberPouzivatelaSceneController controller = new VyberPouzivatelaSceneController();
            nextWindow(controller,"VyberPouzivatelaScene.fxml", "Úprava použivateľa");
        });
        upravitUcebnuButton.setOnAction(eh -> {
            VyberUcebnuSceneController controller = new VyberUcebnuSceneController();
            nextWindow(controller,"VyberUcebnuScene.fxml", "Výber učebne");
        });
    }
    
    
    private void nextWindow (Object controller, String resourceFXMLString, String title){
         try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(resourceFXMLString));
            loader.setController(controller);

            Parent parentPane = loader.load();
            Scene scene = new Scene(parentPane);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(title);
            
            stage.setMaxWidth(626);
            stage.setMinWidth(300);
            stage.setMaxHeight(626);
            stage.setMinHeight(360);
            
            stage.getIcons().add(new Image(MainMenuSceneController.class.getResourceAsStream("settings.png")));
            scene.getStylesheets().add(MainMenuSceneController.class.getResource("MainMenuScene.css").toExternalForm());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }
}

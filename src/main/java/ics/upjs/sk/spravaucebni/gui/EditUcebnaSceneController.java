package ics.upjs.sk.spravaucebni.gui;

import ics.upjs.sk.spravaucebni.Ucebna;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class EditUcebnaSceneController {
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button pocitaceButton;
    
    @FXML
    private Button tabuleButton;

    @FXML
    private Button projektoryButton;

    @FXML
    private Button spotrebaButton;

    @FXML
    private Button oznamyButton;

    private Ucebna ucebna;

    public EditUcebnaSceneController(Ucebna ucebna) {
        this.ucebna = ucebna;
    }
    
    @FXML
    void initialize() {
        
        
        pocitaceButton.setOnAction(eh -> {
             VyberPocitacSceneController controller = new VyberPocitacSceneController(ucebna.getId());
             nextWindow(controller,"VyberPocitacScene.fxml", "Počítače učebne: " + ucebna.getNazov());
        });
        projektoryButton.setOnAction(eh -> {
            VyberProjektorSceneController controller = new VyberProjektorSceneController(ucebna.getId());
            nextWindow(controller,"VyberProjektorScene.fxml", "Projektory učebne: " + ucebna.getNazov());
        });
        tabuleButton.setOnAction(eh -> {
            VyberTabuluSceneController controller = new VyberTabuluSceneController(ucebna.getId());
            nextWindow(controller,"VyberTabuluScene.fxml", "Tabule učebne: " + ucebna.getNazov());
        });
        spotrebaButton.setOnAction(eh -> {
            SpotrebaSceneController controller = new SpotrebaSceneController(ucebna.getId());
            nextWindow(controller,"SpotrebaScene.fxml", "Spotreby učebne: " + ucebna.getNazov());
        });
        oznamyButton.setOnAction(eh -> {
            VyberOznamSceneController controller = new VyberOznamSceneController(ucebna.getId());
            nextWindow(controller,"VyberOznamScene.fxml", "Oznamy učebne: " + ucebna.getNazov());
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
          
            
            if(resourceFXMLString.equalsIgnoreCase("SpotrebaScene.fxml")){
                stage.setMinWidth(425);
                stage.setMinHeight(555);
            }else{
                stage.setMinWidth(300);
                stage.setMinHeight(370);
            }
            
            stage.setMaxWidth(626);
            stage.setMaxHeight(626);

            stage.getIcons().add(new Image(EditUcebnaSceneController.class.getResourceAsStream("settings.png")));
            
            stage.show();
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }
}

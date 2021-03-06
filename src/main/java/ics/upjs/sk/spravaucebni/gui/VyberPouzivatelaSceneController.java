package ics.upjs.sk.spravaucebni.gui;

import ics.upjs.sk.spravaucebni.Pouzivatel;
import ics.upjs.sk.spravaucebni.storage.DaoFactory;
import ics.upjs.sk.spravaucebni.storage.PouzivatelDao;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VyberPouzivatelaSceneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<Pouzivatel> vyberPouzivatelaListView;

    @FXML
    private Button zmazatPouzivatelaButton;
    
    @FXML
    private Button pridatPouzivatelaButton;
    
    @FXML
    private Button prehladProjektorovButton;

    private VyberPouzivatelaFxModel model = new VyberPouzivatelaFxModel();
    
    @FXML
    void initialize() {
       vyberPouzivatelaListView.setItems(model.getPouzivatelov());
       zmazatPouzivatelaButton.setDisable(true);
       prehladProjektorovButton.setDisable(true);
       vyberPouzivatelaListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pouzivatel>() {
            @Override
            public void changed(ObservableValue<? extends Pouzivatel> observable, Pouzivatel oldValue, Pouzivatel newValue) {
                model.setVybratyPouzivatel(newValue);
                if (newValue == null) {
                    zmazatPouzivatelaButton.setDisable(true);
                    prehladProjektorovButton.setDisable(true);
                    
                } else {
                    zmazatPouzivatelaButton.setDisable(false);
                    prehladProjektorovButton.setDisable(false);
                }
            }
        });
        model.getVybratyPouzivatelProperty().addListener(new ChangeListener<Pouzivatel>() {
            @Override
            public void changed(ObservableValue observable, Pouzivatel oldValue, Pouzivatel newValue) {
                if (newValue == null) {
                    vyberPouzivatelaListView.getSelectionModel().clearSelection();
                } else {
                    vyberPouzivatelaListView.getSelectionModel().select(newValue);
                }
            }
        });
        
        prehladProjektorovButton.setOnAction(eh -> {
            PrehladProjektorovSceneController controller = new PrehladProjektorovSceneController(model.getVybratyPouzivatel().getId());
            
            try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("PrehladProjektorovScene.fxml"));
            loader.setController(controller);

            Parent parentPane = loader.load();
            Scene scene = new Scene(parentPane);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Prehľad projektorov použivateľa");
          /*
            stage.setMinWidth(335);
            stage.setMinHeight(335);
            stage.setMaxWidth(626);
            stage.setMaxHeight(626);
            */
            stage.getIcons().add(new Image(VyberPouzivatelaSceneController.class.getResourceAsStream("settings.png")));
            
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
        });
        
        zmazatPouzivatelaButton.setOnAction(eh -> {
            PouzivatelDao pouzivatelDao = DaoFactory.INSTANCE.getPouzivatelDao();
            Pouzivatel p = model.getVybratyPouzivatel();
            pouzivatelDao.delete(p.getId());
            model.nacitaj();
        });
        
        pridatPouzivatelaButton.setOnAction(eh ->{
            PouzivatelSceneController controller = new PouzivatelSceneController();
            
            try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("PouzivatelScene.fxml"));
            loader.setController(controller);

            Parent parentPane = loader.load();
            Scene scene = new Scene(parentPane);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Pridanie nového používateľa");
          
            stage.setMinWidth(335);
            stage.setMinHeight(335);
            stage.setMaxWidth(626);
            stage.setMaxHeight(626);
            
            stage.getIcons().add(new Image(VyberPouzivatelaSceneController.class.getResourceAsStream("settings.png")));
            
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            model.nacitaj();
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
        });
    }
}
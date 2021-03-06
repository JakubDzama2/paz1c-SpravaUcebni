package ics.upjs.sk.spravaucebni.gui;

import ics.upjs.sk.spravaucebni.Pocitac;
import ics.upjs.sk.spravaucebni.Ucebna;
import ics.upjs.sk.spravaucebni.storage.DaoFactory;
import ics.upjs.sk.spravaucebni.storage.PocitacDao;
import ics.upjs.sk.spravaucebni.storage.UcebnaDao;
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

public class VyberPocitacSceneController {
    
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<Pocitac> vyberPocitacListView;

    @FXML
    private Button zmazatPocitacButton;
    
    @FXML
    private Button pridatButton;
    
    @FXML
    private Button pokracovatButton;

    private VyberPocitacFxModel model;
    private Long ucebnaId;

    public VyberPocitacSceneController(Long ucebnaId) {
        this.ucebnaId = ucebnaId;
        this.model = new VyberPocitacFxModel(ucebnaId);
    }
    
    @FXML
    void initialize() {
       vyberPocitacListView.setItems(model.getPocitace());
       zmazatPocitacButton.setDisable(true);
       pokracovatButton.setDisable(true);
       
       vyberPocitacListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pocitac>() {
           @Override
           public void changed(ObservableValue<? extends Pocitac> observable, Pocitac oldValue, Pocitac newValue) {
               model.setVybratyPocitac(newValue);
               if (newValue == null) {
                   zmazatPocitacButton.setDisable(true);
                   pokracovatButton.setDisable(true);
               } else {
                   zmazatPocitacButton.setDisable(false);
                   pokracovatButton.setDisable(false);
               }
           }
       });
       
       model.getVybratyPocitacProperty().addListener(new ChangeListener<Pocitac>() {
           @Override
           public void changed(ObservableValue<? extends Pocitac> observable, Pocitac oldValue, Pocitac newValue) {
               if (newValue == null) {
                   vyberPocitacListView.getSelectionModel().clearSelection();
               } else {
                   vyberPocitacListView.getSelectionModel().select(newValue);
               }
           }
       });
       
       zmazatPocitacButton.setOnAction(eh -> {
           PocitacDao pocitacDao = DaoFactory.INSTANCE.getPocitacDao();
           pocitacDao.delete(model.getVybratyPocitac().getId());
           model.inicializuj();
       });
       
       pridatButton.setOnAction(eh -> {
           PocitacSceneController pocitacSceneController = new PocitacSceneController(ucebnaId);
           nextWindow(pocitacSceneController, "PocitacScene.fxml", "Nový počítač");
           if (pocitacSceneController.jeUlozeny()) {
               model.inicializuj();
           }
       });
       
       pokracovatButton.setOnAction(eh -> {
           PocitacSceneController pocitacSceneController = new PocitacSceneController(model.getVybratyPocitac(), ucebnaId);
           nextWindow(pocitacSceneController, "PocitacScene.fxml", "Úprava počítača");
           if (pocitacSceneController.jeUlozeny()) {
               model.inicializuj();
           }
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
          
            stage.setMinWidth(355);
            stage.setMinHeight(250);
            stage.setMaxWidth(626);
            stage.setMaxHeight(626);
            
            stage.getIcons().add(new Image(VyberPocitacSceneController.class.getResourceAsStream("settings.png")));
            
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }
}

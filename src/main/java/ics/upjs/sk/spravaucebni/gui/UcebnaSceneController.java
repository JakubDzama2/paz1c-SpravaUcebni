package ics.upjs.sk.spravaucebni.gui;

import ics.upjs.sk.spravaucebni.Pouzivatel;
import ics.upjs.sk.spravaucebni.Ucebna;
import ics.upjs.sk.spravaucebni.storage.DaoFactory;
import ics.upjs.sk.spravaucebni.storage.PouzivatelDao;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class UcebnaSceneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ulozitButton;

    @FXML
    private TextField nazovUcebneTextField;
    
    @FXML
    private ComboBox<Pouzivatel> pouzivatelCombobox;

    private UcebnaFxModel model;
    private Ucebna aktualnaUcebna;
    private boolean ulozeny;

    public UcebnaSceneController(Ucebna ucebna) {
        this.aktualnaUcebna = ucebna;
        model = new UcebnaFxModel(aktualnaUcebna);
    }

    public UcebnaSceneController() {
        model = new UcebnaFxModel(null);
    }
    
    private void setCombobox() {
        if (aktualnaUcebna != null) {
            PouzivatelDao pouzivatelDao = DaoFactory.INSTANCE.getPouzivatelDao();
            Pouzivatel p = pouzivatelDao.getById(aktualnaUcebna.getIdPouzivatela());
            if (p != null) {
                pouzivatelCombobox.getSelectionModel().select(p);
            } else {
                pouzivatelCombobox.getSelectionModel().selectLast();
            }
        } else {
            pouzivatelCombobox.getSelectionModel().selectLast();
        }
    }
    
    @FXML
    void initialize() {
        nazovUcebneTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.equals("")) {
                ulozitButton.setDisable(true);
            } else {
                ulozitButton.setDisable(false);
            }
        }));
        model.getNazovProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.equals("")) {
                ulozitButton.setDisable(true);
            } else {
                ulozitButton.setDisable(false);
            }
        }));
        nazovUcebneTextField.textProperty().bindBidirectional(model.getNazovProperty());
        
        pouzivatelCombobox.setItems(model.getPouzivatelia());
        setCombobox();
        pouzivatelCombobox.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            model.setPouzivatel(newValue);
        }));
        model.getPouzivatelProperty().addListener(((observable, oldValue, newValue) -> {
//            if (newValue == null) {
//                pouzivatelCombobox.getSelectionModel().clearSelection();
//            } else {
                pouzivatelCombobox.getSelectionModel().select(newValue);
//            }
        }));
        
        ulozitButton.setOnAction(eh -> {
            if (model.ulozAktualnuUcebnu()) {
                ulozeny = true;
                ulozitButton.getScene().getWindow().hide();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Niektoré zadané údaje sú chybné");
                
                alert.setContentText("Učebňa s názvom: " + model.getNazov() + " už existuje. Prosím, zmeňte názov učebne.");

                alert.showAndWait();
            }
            
        });
        
    }
    
        public boolean jeUlozeny() {
        return ulozeny;
    }
}


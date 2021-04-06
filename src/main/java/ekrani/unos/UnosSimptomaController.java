package main.java.ekrani.unos;

import hr.java.covidportal.model.Simptom;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.BazaPodataka;
import main.java.ekrani.DataLoader;
import main.java.ekrani.Main;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller klasa prozora za unos novih simptoma
 */
public class UnosSimptomaController implements Initializable {

    @FXML
    private TextField nazivText;

    @FXML
    Menu menu = new Menu("Ucestalost ▼");

    @FXML
    MenuBar menuBar = new MenuBar();

    List<RadioMenuItem> ucestalostMenuList = new ArrayList<>();

    ToggleGroup ucestalostToggle = new ToggleGroup();

    /**
     * Metoda koja omogucava unosenje novih simptoma
     */
    @FXML
    public void unesi(){

        boolean uspjeh = false;

        String uneseniNaziv = nazivText.getText();
        String odabranaUcestalost = null;
        for(RadioMenuItem i : ucestalostMenuList){
            if(i.isSelected()){
                odabranaUcestalost = i.getText();
            }
        }

        if(uneseniNaziv == "" || odabranaUcestalost == null){
            uspjeh = false;
        } else {
            uspjeh = true;
        }

        DataLoader ucitaj = new DataLoader();

        Long noviId = Long.valueOf(0);
        for(Simptom s: ucitaj.getSimptomi()){
            noviId = s.getId() + 1;
        }

        if(uspjeh){
            try {

                Simptom novi = new Simptom(noviId,uneseniNaziv,odabranaUcestalost);
                BazaPodataka baza = new BazaPodataka();
                baza.addNewSimp(novi);
                uspjeh = true;
            } catch (SQLException e) {
                e.printStackTrace();
                uspjeh = false;
            }
            if(uspjeh){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Uspjeh!");
                alert.setHeaderText("Simptom je uspješno unesen!");
                alert.setContentText("Svaka čast!");
                alert.showAndWait();
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Neuspjeh!");
                alert.setHeaderText("Simptom je neuspješno unesen!");
                alert.setContentText("Više sreće drugi put.");
                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Neuspjeh!");
            alert.setHeaderText("Simptom je neuspješno unesen!");
            alert.setContentText("Više sreće drugi put.");
            alert.showAndWait();
        }


    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ucestalostMenuList.add(new RadioMenuItem("Rijetko"));
        ucestalostMenuList.add(new RadioMenuItem("Srednje"));
        ucestalostMenuList.add(new RadioMenuItem("Često"));
        ucestalostMenuList.add(new RadioMenuItem("Produktivni"));
        ucestalostMenuList.add(new RadioMenuItem("Jaka"));
        ucestalostMenuList.add(new RadioMenuItem("Visoka"));
        ucestalostMenuList.add(new RadioMenuItem("Uvijek"));
        ucestalostMenuList.add(new RadioMenuItem("Intenzivno"));

        for(RadioMenuItem i: ucestalostMenuList){
            menu.getItems().add(i);
            i.setToggleGroup(ucestalostToggle);
        }

        menuBar.getMenus().add(menu);

        Main.getMainStage().setTitle("Unos novog simptoma");
    }

}

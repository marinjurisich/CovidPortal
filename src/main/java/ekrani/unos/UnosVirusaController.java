package main.java.ekrani.unos;

import hr.java.covidportal.model.Bolest;
import hr.java.covidportal.model.Simptom;
import hr.java.covidportal.model.Virus;
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
 * Controller klasa prozora za unos novih virusa
 */
public class UnosVirusaController implements Initializable {

    @FXML
    private TextField nazivText;

    @FXML
    Menu menu = new Menu("Simptomi ▼");

    @FXML
    MenuBar menuBar = new MenuBar();

    List<CheckMenuItem> menuList = new ArrayList<>();

    /**
     * Metoda koja omogucava unos novih virusa
     */
    @FXML
    public void unesi(){

        List<String> naziviSimptoma = new ArrayList<>();

        boolean uspjeh = false;

        for (CheckMenuItem i : menuList){
            if(i.isSelected()){
                naziviSimptoma.add(i.getText());
            }
        }

        String uneseniNaziv = nazivText.getText();


        DataLoader ucitaj = new DataLoader();

        List<Long> idSimptoma = new ArrayList<>();
        List<Simptom> simptomiBolesti = new ArrayList<>();

        for(Simptom s : ucitaj.getSimptomi()){
            for (String n : naziviSimptoma){
                if(s.getNaziv().toLowerCase().equals(n.toLowerCase())){
                    idSimptoma.add(s.getId());
                    simptomiBolesti.add(s);
                }
            }
        }

        Long noviId = Long.valueOf(0);
        for(Bolest b: ucitaj.getBolesti()){
            noviId = b.getId() + 1;
        }

        if(idSimptoma.size() == 0 || uneseniNaziv.length() == 0){
            uspjeh = false;
        }else{
            uspjeh = true;
        }

        if(uspjeh){
            try {

                BazaPodataka baza = new BazaPodataka();
                Virus novi = new Virus(noviId,uneseniNaziv,simptomiBolesti);
                baza.addNewBolest(novi);
                baza.unesiSimptomeBolesti(idSimptoma, noviId);

                uspjeh = true;
            } catch (SQLException e) {
                e.printStackTrace();
                uspjeh = false;
            }
            if(uspjeh){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Uspjeh!");
                alert.setHeaderText("Virus je uspješno unesen!");
                alert.setContentText("Svaka čast!");
                alert.showAndWait();
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Neuspjeh!");
                alert.setHeaderText("Virus je neuspješno unesen!");
                alert.setContentText("Više sreće drugi put.");
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Neuspjeh!");
            alert.setHeaderText("Virus je neuspješno unesen!");
            alert.setContentText("Više sreće drugi put.");
            alert.showAndWait();
        }
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Main.getMainStage().setTitle("Unos novog virusa");
        DataLoader ucitaj = new DataLoader();
        List<Simptom> listaSimptoma = ucitaj.getSimptomi();

        for(Simptom s: listaSimptoma){
            menuList.add(new CheckMenuItem(s.getNaziv()));
        }

        for(CheckMenuItem i: menuList){
            menu.getItems().add(i);
        }

        menuBar.getMenus().add(menu); ;
    }
}

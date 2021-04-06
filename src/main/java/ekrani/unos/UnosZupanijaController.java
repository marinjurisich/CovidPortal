package main.java.ekrani.unos;

import hr.java.covidportal.model.Zupanija;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import main.BazaPodataka;
import main.java.ekrani.DataLoader;
import main.java.ekrani.Main;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controller klasa prozora za unos novih zupanija
 */
public class UnosZupanijaController implements Initializable {

    @FXML
    private TextField nazivText;

    @FXML
    private TextField brojStanovnikaText;

    @FXML
    private TextField brojZarazenihText;

    /**
     * Metoda koja omogucava unosenje novih zupanija
     */
    @FXML
    public void unesi(){

        boolean uspjeh = false;

        String uneseniNaziv = nazivText.getText();
        Integer uneseniBrojStanovnika = 0;
        Integer uneseniBrojZarazenih = 0;
        try{
            uneseniBrojStanovnika = Integer.valueOf(brojStanovnikaText.getText());
            uneseniBrojZarazenih = Integer.valueOf(brojZarazenihText.getText());
        }catch (NumberFormatException e){
            e.printStackTrace();
            uspjeh = false;
        }


        if(uneseniNaziv.length() == 0 || uneseniBrojStanovnika == 0 || uneseniBrojZarazenih == 0){
            uspjeh = false;
        }else {
            uspjeh = true;
        }

        DataLoader ucitaj = new DataLoader();

        Long noviId = Long.valueOf(0);
        for(Zupanija z: ucitaj.getZupanije()){
            noviId = z.getId() + 1;
        }

        if(uspjeh){
            try {

                Zupanija nova = new Zupanija(noviId,uneseniNaziv,uneseniBrojStanovnika,uneseniBrojZarazenih);
                BazaPodataka baza = new BazaPodataka();
                baza.addNewZupanija(nova);
                uspjeh = true;
            } catch (SQLException e) {
                e.printStackTrace();
                uspjeh = false;
            }
            if(uspjeh){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Uspjeh!");
                alert.setHeaderText("Zupanija je uspješno unesena!");
                alert.setContentText("Svaka čast!");
                alert.showAndWait();
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Neuspjeh!");
                alert.setHeaderText("Zupanija je neuspješno unesena!");
                alert.setContentText("Više sreće drugi put.");
                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Neuspjeh!");
            alert.setHeaderText("Zupanija je neuspješno unesena!");
            alert.setContentText("Više sreće drugi put.");
            alert.showAndWait();
        }
        


    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getMainStage().setTitle("Unos nove županije");
    }
}

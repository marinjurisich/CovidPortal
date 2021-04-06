package main.java.ekrani.pretraga;

import hr.java.covidportal.model.Zupanija;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.ekrani.DataLoader;
import main.java.ekrani.Main;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controller klasa prozora za pretragu zupanija
 */
public class PretragaZupanijaController implements Initializable {

    @FXML
    private TableColumn<Zupanija, String> nazivZupanijeColumn;
    @FXML
    private TableColumn<Zupanija, Integer> stupacBrojaStanovnika;
    @FXML
    private TableColumn<Zupanija, Integer> stupacBrojaZarazenih;

    @FXML
    private TableView<Zupanija> zupanijaTable;

    @FXML
    private TextField trazeno;

    /**
     * Metoda koja omogucuje pretrazivanje zupanija
     */
    @FXML
    public void pretrazi(){

        String tekst = trazeno.getText().toLowerCase();

        DataLoader ucitaj = new DataLoader();

        ObservableList<Zupanija> filtriraneZupanije = FXCollections
                .observableArrayList(ucitaj.getZupanije()
                        .stream()
                        .filter(zupanija -> zupanija.getNaziv().toLowerCase().contains(tekst))
                        .collect(Collectors.toList()));

        nazivZupanijeColumn.setCellValueFactory(new PropertyValueFactory<Zupanija, String>("naziv"));
        stupacBrojaStanovnika.setCellValueFactory(new PropertyValueFactory<Zupanija, Integer>("brojStanovnika"));
        stupacBrojaZarazenih.setCellValueFactory(new PropertyValueFactory<Zupanija, Integer>("brojZarazenih"));

        zupanijaTable.setItems(FXCollections.observableList(filtriraneZupanije));
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DataLoader ucitaj = new DataLoader();

        ObservableList<Zupanija> listaZupanija = FXCollections
                .observableArrayList(ucitaj.getZupanije());

        Main.getMainStage().setTitle("Županije");

        nazivZupanijeColumn.setCellValueFactory(new PropertyValueFactory<Zupanija, String>("naziv"));
        stupacBrojaStanovnika.setCellValueFactory(new PropertyValueFactory<Zupanija, Integer>("brojStanovnika"));
        stupacBrojaZarazenih.setCellValueFactory(new PropertyValueFactory<Zupanija, Integer>("brojZarazenih"));

        zupanijaTable.setItems(FXCollections.observableList(listaZupanija));
    }

}

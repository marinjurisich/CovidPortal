package main.java.ekrani.pretraga;

import hr.java.covidportal.model.Simptom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.ekrani.DataLoader;
import main.java.ekrani.Main;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PretragaSimptomaController implements Initializable {

    @FXML
    private TableColumn<Simptom, String> nazivSimptomaColumn;
    @FXML
    private TableColumn<Simptom, String> vrijednostSimptomaColumn;


    @FXML
    private TableView<Simptom> simptomTable;


    @FXML
    private TextField trazeno;

    /**
     * Metoda koja omogucuje pretrazivanje simptoma
     */
    @FXML
    public void pretrazi(){

        String tekst = trazeno.getText().toLowerCase();

        DataLoader ucitaj = new DataLoader();

        ObservableList<Simptom> filtriraniSimptomi = FXCollections
                .observableArrayList(ucitaj.getSimptomi()
                        .stream()
                        .filter(simptom -> simptom.getNaziv().toLowerCase().contains(tekst))
                        .collect(Collectors.toList()));

        nazivSimptomaColumn.setCellValueFactory(new PropertyValueFactory<Simptom, String>("naziv"));
        vrijednostSimptomaColumn.setCellValueFactory(new PropertyValueFactory<Simptom, String>("vrijednost"));

        simptomTable.setItems(FXCollections.observableList(filtriraniSimptomi));
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DataLoader ucitaj = new DataLoader();

        ObservableList<Simptom> listaSimptoma= FXCollections
                .observableArrayList(ucitaj.getSimptomi());

        Main.getMainStage().setTitle("Simptomi");

        nazivSimptomaColumn.setCellValueFactory(new PropertyValueFactory<Simptom, String>("naziv"));
        vrijednostSimptomaColumn.setCellValueFactory(new PropertyValueFactory<Simptom, String>("vrijednost"));

        simptomTable.setItems(FXCollections.observableList(listaSimptoma));
    }
}

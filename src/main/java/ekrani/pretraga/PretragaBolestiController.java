package main.java.ekrani.pretraga;

import hr.java.covidportal.model.Bolest;
import hr.java.covidportal.model.Simptom;
import hr.java.covidportal.model.Virus;
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
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PretragaBolestiController implements Initializable {

    @FXML
    private TableColumn<Bolest, String> nazivBolestiColumn;
    @FXML
    private TableColumn<Bolest, Simptom> simptomiBolestiColumn;

    @FXML
    private TableView<Bolest> bolestTable;

    @FXML
    private TextField trazeniNaziv;

    @FXML
    private TextField trazeniSimptomi;

    /**
     * Metoda koja omogucuje pretrazivanje bolesti
     */
    @FXML
    public void pretrazi(){

        String tekstNaziv = trazeniNaziv.getText().toLowerCase();
        String tekstSimptomi = trazeniSimptomi.getText().toLowerCase();

        DataLoader ucitaj = new DataLoader();

        ObservableList<Bolest> filtriraneBolesti = FXCollections
                .observableArrayList(ucitaj.getBolesti()
                        .stream()
                        .filter(bolest -> bolest.getNaziv().toLowerCase().contains(tekstNaziv)
                                && bolest.getSimptomi().toString().toLowerCase().contains(tekstSimptomi)
                                && !(bolest instanceof Virus))
                        .collect(Collectors.toList()));

        nazivBolestiColumn.setCellValueFactory(new PropertyValueFactory<Bolest, String>("naziv"));
        simptomiBolestiColumn.setCellValueFactory(new PropertyValueFactory<Bolest, Simptom>("simptomi"));

        bolestTable.setItems(FXCollections.observableList(filtriraneBolesti));
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DataLoader ucitaj = new DataLoader();

        List<Bolest> listaBolesti = ucitaj.getBolesti().stream()
                .filter(bolest -> !(bolest instanceof Virus))
                .collect(Collectors.toList());

        Main.getMainStage().setTitle("Bolesti");

        nazivBolestiColumn.setCellValueFactory(new PropertyValueFactory<Bolest, String>("naziv"));
        simptomiBolestiColumn.setCellValueFactory(new PropertyValueFactory<Bolest, Simptom>("simptomi"));

        bolestTable.setItems(FXCollections.observableList(listaBolesti));
    }
}

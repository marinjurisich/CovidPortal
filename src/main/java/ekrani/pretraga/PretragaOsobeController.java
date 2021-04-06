package main.java.ekrani.pretraga;

import hr.java.covidportal.model.Bolest;
import hr.java.covidportal.model.Osoba;
import hr.java.covidportal.model.Zupanija;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;
import main.BazaPodataka;
import main.java.ekrani.DataLoader;
import main.java.ekrani.Main;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PretragaOsobeController implements Initializable {

    @FXML
    private TableColumn<Osoba, String> imeOsobeColumn;
    @FXML
    private TableColumn<Osoba, String> prezimeOsobeColumn;
    @FXML
    private TableColumn<Osoba, Bolest> bolestOsobeColumn;
    @FXML
    private TableColumn<Osoba, Zupanija> zupanijaOsobeColumn;

    @FXML
    private TableView<Osoba> osobaTable;

    @FXML
    private TextField trazenoIme;

    @FXML
    private TextField trazenoPrezime;

    @FXML
    private TextField trazenaBolest;

    @FXML
    private TextField trazenaZupanija;

    /**
     * Metoda koja omogucuje pretrazivanje osoba
     */
    @FXML
    public void pretrazi(){

        String tekstIme = trazenoIme.getText().toLowerCase();
        String tekstPrezime= trazenoPrezime.getText().toLowerCase();
        String tekstBolest= trazenaBolest.getText().toLowerCase();
        String tekstZupanija= trazenaZupanija.getText().toLowerCase();

        DataLoader ucitaj = new DataLoader();

        ObservableList<Osoba> filtriraneOsobe = FXCollections
                .observableArrayList(ucitaj.getOsobe()
                        .stream()
                        .filter(osoba -> osoba.getIme().toLowerCase().contains(tekstIme)
                                && osoba.getPrezime().toLowerCase().contains(tekstPrezime)
                                && osoba.getZarazenBolescu().toString().toLowerCase().contains(tekstBolest)
                                && osoba.getZupanija().toString().toLowerCase().contains(tekstZupanija))
                        .collect(Collectors.toList()));

        imeOsobeColumn.setCellValueFactory(new PropertyValueFactory<Osoba, String>("ime"));
        prezimeOsobeColumn.setCellValueFactory(new PropertyValueFactory<Osoba, String>("prezime"));
        bolestOsobeColumn.setCellValueFactory(new PropertyValueFactory<Osoba, Bolest>("zarazenBolescu"));
        zupanijaOsobeColumn.setCellValueFactory(new PropertyValueFactory<Osoba, Zupanija>("zupanija"));

        osobaTable.setItems(FXCollections.observableList(filtriraneOsobe));
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DataLoader ucitaj = new DataLoader();

        List<Osoba> listaOsoba = ucitaj.getOsobe();

        Main.getMainStage().setTitle("Osobe");

        imeOsobeColumn.setCellValueFactory(new PropertyValueFactory<Osoba, String>("ime"));
        prezimeOsobeColumn.setCellValueFactory(new PropertyValueFactory<Osoba, String>("prezime"));
        bolestOsobeColumn.setCellValueFactory(new PropertyValueFactory<Osoba, Bolest>("zarazenBolescu"));
        zupanijaOsobeColumn.setCellValueFactory(new PropertyValueFactory<Osoba, Zupanija>("zupanija"));

        osobaTable.setItems(FXCollections.observableList(listaOsoba));


        //1. zadatak

        osobaTable.setRowFactory( tv -> {
            TableRow<Osoba> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                MouseButton button = event.getButton();
                if(button==MouseButton.SECONDARY || event.isSecondaryButtonDown()){

                    Osoba osoba = row.getItem();

                    BazaPodataka baza = new BazaPodataka();

                    try {
                        baza.deleteOsoba(osoba); //metoda se nalazi u klasi BazaPodataka
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    final ContextMenu rowMenu = new ContextMenu();
                    MenuItem removeItem = new MenuItem("Delete");
                    removeItem.setOnAction(event1 -> osobaTable.getItems().remove(row.getItem()));

                    rowMenu.getItems().addAll(removeItem);

                    row.contextMenuProperty().bind(
                            Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                    .then(rowMenu)
                                    .otherwise((ContextMenu)null));
                }

            });
            return row ;
        });


        // 2. zadatak

        osobaTable.setRowFactory( tv -> {
            TableRow<Osoba> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Osoba osoba = row.getItem();

                    BazaPodataka baza = new BazaPodataka();
                    List<Osoba> kontakti = new ArrayList<>();
                    try {
                        kontakti = baza.getKontakteOsobe(osoba.getId());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Kontakti!");
                    alert.setHeaderText("Ovo su kontakti osobe: " + osoba + ";");
                    alert.setContentText(String.valueOf(kontakti));
                    alert.showAndWait();

                }
            });
            return row ;
        });

    }
}

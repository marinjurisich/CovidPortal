package main.java.ekrani;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller klasa pocetnog ekrana GUI-a
 */
public class PocetniEkranController implements Initializable {

    //----Funkcije za otvaranje prozora za pretragu----//

    /**
     * Otvara prozor za pretragu zupanija
     * @throws IOException
     */
    @FXML
    public void prikaziEkranZaPretraguZupanija() throws IOException {

        Parent pocetna = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("pretragaZupanija.fxml")));
        Scene pocetnaScene = new Scene(pocetna, 768, 432);
        Main.getMainStage().setTitle("CovidPortal");
        Main.getMainStage().setScene(pocetnaScene);
    }

    /**
     * Otvara prozor za pretragu simptoma
     * @throws IOException
     */
    @FXML
    public void prikaziEkranZaPretraguSimptoma() throws IOException {

        Parent pocetna = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("pretragaSimptoma.fxml")));
        Scene pocetnaScene = new Scene(pocetna, 768, 432);
        Main.getMainStage().setTitle("CovidPortal");
        Main.getMainStage().setScene(pocetnaScene);
    }

    /**
     * Otvara prozor za pretragu bolesti
     * @throws IOException
     */
    @FXML
    public void prikaziEkranZaPretraguBolesti() throws IOException {

        Parent pocetna = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("pretragaBolesti.fxml")));
        Scene pocetnaScene = new Scene(pocetna, 768, 432);
        Main.getMainStage().setTitle("CovidPortal");
        Main.getMainStage().setScene(pocetnaScene);
    }

    /**
     * Otvara prozor za pretragu virusa
     * @throws IOException
     */
    @FXML
    public void prikaziEkranZaPretraguVirusa() throws IOException {

        Parent pocetna = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("pretragaVirusa.fxml")));
        Scene pocetnaScene = new Scene(pocetna, 768, 432);
        Main.getMainStage().setTitle("CovidPortal");
        Main.getMainStage().setScene(pocetnaScene);
    }

    /**
     * Otvara prozor za pretragu Osoba
     * @throws IOException
     */
    @FXML
    public void prikaziEkranZaPretraguOsobe() throws IOException {

        Parent pocetna = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("pretragaOsobe.fxml")));
        Scene pocetnaScene = new Scene(pocetna, 768, 432);
        Main.getMainStage().setTitle("CovidPortal");
        Main.getMainStage().setScene(pocetnaScene);
    }

    //----Funkcije za otvaranje prozora za unos----//

    /**
     *Otvara prozor za unos nove osobe
     * @throws IOException
     */
    @FXML
    public void prikaziEkranZaUnosZupanija() throws IOException {
        Parent pocetna = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("unosZupanija.fxml")));
        Scene pocetnaScene = new Scene(pocetna, 768, 432);
        Main.getMainStage().setTitle("CovidPortal");
        Main.getMainStage().setScene(pocetnaScene);
    }

    /**
     * Otvara prozor za unos novog simptoma
     * @throws IOException
     */
    @FXML
    public void prikaziEkranZaUnosSimptoma() throws IOException {
        Parent pocetna = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("unosSimptoma.fxml")));
        Scene pocetnaScene = new Scene(pocetna, 768, 432);
        Main.getMainStage().setTitle("CovidPortal");
        Main.getMainStage().setScene(pocetnaScene);
    }

    /**
     * Otvara prozor za unos nove bolesti
     * @throws IOException
     */
    @FXML
    public void prikaziEkranZaUnosBolesti() throws IOException {
        Parent pocetna = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("unosBolesti.fxml")));
        Scene pocetnaScene = new Scene(pocetna, 768, 432);
        Main.getMainStage().setTitle("CovidPortal");
        Main.getMainStage().setScene(pocetnaScene);
    }

    /**
     * Otvara prozor za unos novog virusa
     * @throws IOException
     */
    @FXML
    public void prikaziEkranZaUnosVirusa() throws IOException {
        Parent pocetna = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("unosVirusa.fxml")));
        Scene pocetnaScene = new Scene(pocetna, 768, 432);
        Main.getMainStage().setTitle("CovidPortal");
        Main.getMainStage().setScene(pocetnaScene);
    }

    /**
     * Otvara prozor za unos nove osobe
     * @throws IOException
     */
    @FXML
    public void prikaziEkranZaUnosOsoba() throws IOException {
        Parent pocetna = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("unosOsoba.fxml")));
        Scene pocetnaScene = new Scene(pocetna, 768, 432);
        Main.getMainStage().setTitle("CovidPortal");
        Main.getMainStage().setScene(pocetnaScene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}

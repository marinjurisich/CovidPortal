package main.java.ekrani.unos;

import hr.java.covidportal.model.Bolest;
import hr.java.covidportal.model.Osoba;
import hr.java.covidportal.model.Zupanija;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.BazaPodataka;
import main.java.ekrani.DataLoader;
import main.java.ekrani.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller klasa prozora za unos osoba
 */
public class UnosOsobaController implements Initializable {

    @FXML
    private TextField imeText;

    @FXML
    private TextField prezimeText;

    @FXML
    private TextField starostText;

    @FXML
    Menu bolestMenu = new Menu("Bolesti ▼");

    @FXML
    Menu zupanijaMenu = new Menu("Zupanija ▼");

    @FXML
    Menu kontaktiMenu = new Menu("Kontakti ▼");

    @FXML
    MenuBar menuBarB = new MenuBar();

    @FXML
    MenuBar menuBarZ = new MenuBar();

    @FXML
    MenuBar menuBarK = new MenuBar();

    List<CheckMenuItem> kontaktiMenuList = new ArrayList<>();
    List<RadioMenuItem> bolestiMenuList = new ArrayList<>();
    List<RadioMenuItem> zupanijeMenuList = new ArrayList<>();

    ToggleGroup bolestiToggle = new ToggleGroup();
    ToggleGroup zupanijeToggle = new ToggleGroup();

    /**
     * Metoda koja omogucava unos novih osoba
     */
    @FXML
    public void unesi() throws SQLException, IOException {

        boolean uspjeh = false;

        DataLoader ucitaj = new DataLoader();

        String unesenoIme = imeText.getText();
        String unesenoPrezime = prezimeText.getText();
        String uneseniDatum = starostText.getText();

        Date datum = Date.valueOf(uneseniDatum);

        if (unesenoIme.length() == 0 || unesenoPrezime.length() == 0){
            uspjeh = false;
        }else uspjeh = true;



        String odabranaBolest = null;
        for(RadioMenuItem i : bolestiMenuList){
            if(i.isSelected()){
                odabranaBolest = i.getText();
            }
        }

        Long idBolest = null;
        for(Bolest b : ucitaj.getBolesti()){
            if (b.getNaziv().equals(odabranaBolest)){
                idBolest = b.getId();
            }
        }

        String odabranaZupanija = null;
        for(RadioMenuItem i : zupanijeMenuList){
            if(i.isSelected()){
                odabranaZupanija = i.getText();
            }
        }

        Long idZupanije = null;
        for(Zupanija z : ucitaj.getZupanije()){
            if (z.getNaziv().equals(odabranaZupanija)){
                idZupanije = z.getId();
            }
        }


        List<String> naziviOsoba = new ArrayList<>();

        for (CheckMenuItem i : kontaktiMenuList){
            if(i.isSelected()){
                naziviOsoba.add(i.getText());
            }
        }
        List<Long> idOsoba = new ArrayList<>();
        for(Osoba o : ucitaj.getOsobe()){
            String imePrezime = o.getIme() + " " + o.getPrezime();
            for (String n : naziviOsoba){
                if(imePrezime.equals(n)){
                    idOsoba.add(o.getId());
                }
            }
        }

        List<Osoba> listaKontakata = new ArrayList<>();
        BazaPodataka baza = new BazaPodataka();
        for (Long i : idOsoba){
            listaKontakata.add(baza.getOneOsoba(i));
        }

        Long noviId = Long.valueOf(0);
        for(Osoba o: ucitaj.getOsobe()){
            noviId = o.getId() + 1;
        }

        if(odabranaBolest == null || odabranaZupanija == null || idOsoba.size() == 0 || uspjeh == false){
            uspjeh = false;
        } else uspjeh = true;

        if(uspjeh){

            Osoba nova = new Osoba.Builder(noviId,unesenoIme,unesenoPrezime)
                    .withDatumRod(datum)
                    .withZupanija(baza.getOneZupanija(idZupanije))
                    .withBolest(baza.getOneBolest(idBolest))
                    .withOsoba(listaKontakata)
                    .build();

            baza.addNewOsoba(nova);
            baza.setKontakteOsobe(idOsoba,noviId);

            uspjeh = true;
            if(uspjeh){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Uspjeh!");
                alert.setHeaderText("Osoba je uspješno unesena!");
                alert.setContentText("Svaka čast!");
                alert.showAndWait();
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Neuspjeh!");
                alert.setHeaderText("Osoba je neuspješno unesena!");
                alert.setContentText("Više sreće drugi put.");
                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Neuspjeh!");
            alert.setHeaderText("Osoba je neuspješno unesena!");
            alert.setContentText("Više sreće drugi put.");
            alert.showAndWait();
        }

    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Main.getMainStage().setTitle("Unos novog virusa");
        DataLoader ucitaj = new DataLoader();

        for(Zupanija z: ucitaj.getZupanije()){
            zupanijeMenuList.add(new RadioMenuItem(z.getNaziv()));
        }
        for(RadioMenuItem i: zupanijeMenuList){
            zupanijaMenu.getItems().add(i);
            i.setToggleGroup(zupanijeToggle);
        }
        menuBarZ.getMenus().add(zupanijaMenu);



        for(Bolest b: ucitaj.getBolesti()){
            bolestiMenuList.add(new RadioMenuItem(b.getNaziv()));
        }
        for(RadioMenuItem i: bolestiMenuList){
            bolestMenu.getItems().add(i);
            i.setToggleGroup(bolestiToggle);
        }
        menuBarB.getMenus().add(bolestMenu);



        for(Osoba o: ucitaj.getOsobe()){
            kontaktiMenuList.add(new CheckMenuItem(o.getIme() + " " + o.getPrezime()));
        }
        for(CheckMenuItem i: kontaktiMenuList){
            kontaktiMenu.getItems().add(i);
        }
        menuBarK.getMenus().add(kontaktiMenu);

    }
}

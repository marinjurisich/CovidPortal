package main;

import hr.java.covidportal.model.*;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.List;

/**
 * Klasa koja sluzi kao veza s bazom podataka, te sadrzi metode za otvaranje i zatvaranje veze s bazom i metode
 * za dohvacanje i slanje podataka s/na bazu podataka
 */
public class BazaPodataka {

    private static final String DB_CONFIG_FILE = "src\\main\\resources\\database.properties";

    public BazaPodataka() {
    }

    /**
     * Otvara vezu s bazom podataka
     * @return objekt klase Connection
     * @throws SQLException
     */
    public  Connection openConnection() throws SQLException {

        Properties svojstva = new Properties();
        try {
            svojstva.load(new FileReader(DB_CONFIG_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String urlBazePodataka = svojstva.getProperty("bazaPodatakaUrl");
        String korisnickoIme = svojstva.getProperty("korisnickoIme");
        String lozinka = svojstva.getProperty("lozinka");
        Connection veza = DriverManager.getConnection(urlBazePodataka,
                korisnickoIme,lozinka);
        return veza;
    }

    /**
     * Prima objekt klase Connection i zatvara vezu s bazom podataka primljenog objekta
     * @param veza
     */
    public  void closeConnection(Connection veza){
        try {
            veza.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Dohvaća sve simptome iz tablice SIMPTOM
     * @return Vraca simptome u obliku liste
     * @throws SQLException
     */
    public  List<Simptom> getAllSimps() throws SQLException {
        List<Simptom> listaSimptoma = new ArrayList<>();
        Connection veza = openConnection();
        Statement stmt = veza.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM SIMPTOM");

        while (rs.next()) {
            Long id = rs.getLong("ID");
            String naziv = rs.getString("NAZIV");
            String vrijednost = rs.getString("VRIJEDNOST");
            Simptom newSimp = new Simptom(id,naziv,vrijednost);
            listaSimptoma.add(newSimp);
        }

        closeConnection(veza);

        return listaSimptoma;
    }

    /**
     * Dohvaca jedan Simptom iz baze podataka pomocu njegovog id-a
     * @param id Long koji odreduje id u tablici
     * @return vraca objekt klase Simptom s podatcima iz tablice
     * @throws SQLException
     */
    public Simptom getOneSimp(Long id) throws SQLException {
        Simptom dohvaceniSimptom = null;
        Connection veza = openConnection();
        Statement stmt = veza.createStatement();
        String upit = "SELECT * FROM SIMPTOM WHERE ID = " + id;
        ResultSet rs = stmt.executeQuery(upit);

        while (rs.next()){
            Long idSimp = rs.getLong("ID");
            String naziv = rs.getString("NAZIV");
            String vrijednost = rs.getString("VRIJEDNOST");

            Simptom novi = new Simptom(idSimp,naziv,vrijednost);
            dohvaceniSimptom = novi;
        }

        closeConnection(veza);
        return dohvaceniSimptom;
    }

    /**
     * Prima objekt klase Simptom i zapisuje njegove podatke u tablicu SIMPTOM u bazi podataka
     * @param simptom
     * @throws SQLException
     */
    public  void addNewSimp(Simptom simptom) throws SQLException {
        Connection veza = openConnection();
        PreparedStatement unesiSimptom = veza.prepareStatement(
                " INSERT INTO SIMPTOM(NAZIV, VRIJEDNOST) VALUES (?,?)");
        unesiSimptom.setString(1,simptom.getNaziv());
        unesiSimptom.setString(2, simptom.getVrijednost());
        unesiSimptom.executeUpdate();
        closeConnection(veza);
    }

    /**
     * Dohvaca sve simptome bolesti iz pomocne tablice BOLEST_SIMPTOM
     * @param id id bolesti ciji se simptomi dohvacaju
     * @return vraca listu simptoma
     * @throws SQLException
     */
    public List<Simptom> dohvatiSimptomeBolesti(Long id) throws SQLException {
        List<Simptom> listaSimptoma = new ArrayList<>();
        Connection veza = openConnection();
        Statement stmt = veza.createStatement();
        String upit = "SELECT SIMPTOM_ID FROM BOLEST_SIMPTOM WHERE BOLEST_ID = " + id;
        ResultSet rs = stmt.executeQuery(upit);
        Set<Long> odabraniSimptomi = new HashSet<>();

        while (rs.next()){
            Long idSimp = rs.getLong("SIMPTOM_ID");
            odabraniSimptomi.add(idSimp);
        }

        for(Long idSimptoma : odabraniSimptomi){
            listaSimptoma.add(getOneSimp(idSimptoma));
        }

        closeConnection(veza);
        return listaSimptoma;
    }

    /**
     * Unosi simptome bolesti u pomocnu tablicu BOLEST_SIMPTOM
     * @param idSimp Lista Long-ova koja sadrzi id-eve simptoma
     * @param idBolesti Long koji sluzi kao indentifikator bolesti ciji se simptomi unose
     * @throws SQLException
     */
    public void unesiSimptomeBolesti(List<Long> idSimp, Long idBolesti) throws SQLException {
        Connection veza = openConnection();

        for(Long i : idSimp){
            PreparedStatement unesiSimptom = veza.prepareStatement(
                    " INSERT INTO BOLEST_SIMPTOM(BOLEST_ID, SIMPTOM_ID) VALUES (?,?)");
            unesiSimptom.setLong(1, idBolesti);
            unesiSimptom.setLong(2,i);
            unesiSimptom.executeUpdate();
        }
    }

    /**
     * Dohvaca sve bolesti iz tablice
     * @return Vraca listu objekata klase Bolest
     * @throws SQLException
     */
    public List<Bolest> getAllBolesti() throws SQLException {
        List<Bolest> lista = new ArrayList<>();
        Connection veza = openConnection();
        Statement stmt = veza.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM BOLEST");

        while (rs.next()) {
            Long id = rs.getLong("ID");
            String naziv = rs.getString("NAZIV");
            Boolean isItViral = rs.getBoolean("VIRUS");
            if (isItViral){
                Virus nova = new Virus(id,naziv,dohvatiSimptomeBolesti(id));
                lista.add(nova);
            }else{
                Bolest nova = new Bolest(id,naziv,dohvatiSimptomeBolesti(id));
                lista.add(nova);
            }
        }

        closeConnection(veza);

        return lista;
    }

    /**
     * Dohvaca jedan objekt klase Bolest
     * @param id Bolesti koju treba dohvatiti
     * @return Objekt klase Bolest inicijaliziran s informacijama iz tablice
     * @throws SQLException
     */
    public Bolest getOneBolest(Long id) throws SQLException {
        Bolest bolest = null;
        Connection veza = openConnection();
        Statement stmt = veza.createStatement();
        String upit = "SELECT * FROM BOLEST WHERE ID = " + id;
        ResultSet rs = stmt.executeQuery(upit);

        while(rs.next()){
            String naziv = rs.getString("NAZIV");
            Boolean virus = rs.getBoolean("VIRUS");

            if(virus){
                Virus novi = new Virus(id,naziv,dohvatiSimptomeBolesti(id));
                bolest = novi;
            }else{
                Bolest novi = new Bolest(id,naziv,dohvatiSimptomeBolesti(id));
                bolest = novi;
            }
        }
        return bolest;
    }

    /**
     * Dodaje podatke o bolesti u bazu podataka
     * @param bolest objekt klase Bolest cije se varijable stavljaju u bazu
     * @throws SQLException
     */
    public void addNewBolest(Bolest bolest) throws SQLException {
        Connection veza = openConnection();
        boolean viralno = false;
        if (bolest instanceof Virus){
            viralno = true;
        }

        PreparedStatement unesiBolest = veza.prepareStatement(
                " INSERT INTO BOLEST(NAZIV, VIRUS) VALUES (?,?)");
        unesiBolest.setString(1, bolest.getNaziv());
        unesiBolest.setBoolean(2,viralno);
        unesiBolest.executeUpdate();
    }

    /**
     * Dohvaca sve podatke o zupanijama iz baze podataka
     * @return listu objekata klase Zupanija
     * @throws SQLException
     */
    public List<Zupanija> getAllZupanije() throws SQLException {
        List<Zupanija> lista = new ArrayList<>();
        Connection veza = openConnection();
        Statement stmt = veza.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM ZUPANIJA");
        while (rs.next()) {
            Long id = rs.getLong("ID");
            String naziv = rs.getString("NAZIV");
            Integer brojStanovnika = rs.getInt("BROJ_STANOVNIKA");
            Integer brojZarazenih = rs.getInt("BROJ_ZARAZENIH_STANOVNIKA");
            Zupanija nova = new Zupanija(id,naziv,brojStanovnika,brojZarazenih);
            lista.add(nova);
        }
        closeConnection(veza);
        return lista;
    }

    /**
     * Dohvaca jednu zupaniju iz baze podataka
     * @param id id zupanije koja se dohvaca
     * @return vraca objekt klase Zupanija
     * @throws SQLException
     */
    public Zupanija getOneZupanija(Long id) throws SQLException {
        Zupanija zupanija = null;
        Connection veza = openConnection();
        String upit = "SELECT * FROM ZUPANIJA WHERE ID = " + id;
        Statement stmt = veza.createStatement();
        ResultSet rs = stmt.executeQuery(upit);

        while (rs.next()) {
            String naziv = rs.getString("NAZIV");
            Integer brojStanovnika = rs.getInt("BROJ_STANOVNIKA");
            Integer brojZarazenih = rs.getInt("BROJ_ZARAZENIH_STANOVNIKA");
            Zupanija nova = new Zupanija(id,naziv,brojStanovnika,brojZarazenih);
            zupanija = nova;
        }
        return zupanija;
    }

    /**
     * Dodaje novu zupaniju u bazu podataka
     * @param zupanija Objekt klase Zupanija koji se dodaje u bazu podataka
     * @throws SQLException
     */
    public void addNewZupanija(Zupanija zupanija) throws SQLException {
        Connection veza = openConnection();
        PreparedStatement unesiZupaniju = veza.prepareStatement(
                "INSERT INTO ZUPANIJA(NAZIV, BROJ_STANOVNIKA,BROJ_ZARAZENIH_STANOVNIKA) VALUES(?,?,?)");
        unesiZupaniju.setString(1, zupanija.getNaziv());
        unesiZupaniju.setInt(2,zupanija.getBrojStanovnika());
        unesiZupaniju.setInt(3,zupanija.getBrojZarazenih());
        unesiZupaniju.executeUpdate();
    }

    /**
     * Dohvaca sve informacije o osobama iz baze podataka
     * @return vraca listu objekata klase Osoba
     * @throws SQLException
     */
    public List<Osoba> getAllOsobe() throws SQLException {
        List<Osoba> lista = new ArrayList<>();
        Connection veza = openConnection();
        Statement stmt = veza.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM OSOBA");

        while(rs.next()){
            Long id = rs.getLong("ID");
            String ime = rs.getString("IME");
            String prezime = rs.getString("PREZIME");
            Date datumRod = rs.getDate("DATUM_RODJENJA");
            Long idZup = rs.getLong("ZUPANIJA_ID");
            Long idBol = rs.getLong("BOLEST_ID");
            Osoba nova = new Osoba.Builder(id,ime,prezime)
                    .withDatumRod(datumRod)
                    .withZupanija(getOneZupanija(idZup))
                    .withBolest(getOneBolest(idBol))
                    .withOsoba(getKontakteOsobe(id))
                    .build();
            lista.add(nova);
        }
        closeConnection(veza);
        return lista;
    }

    /**
     * Dohvaca kontake osobe iz pomocne tablice KONTAKTIRANE_OSOBE
     * @param id id osobe ciji se kontakti dohvacaju
     * @return Lista objekata Osoba
     * @throws SQLException
     */
    public List<Osoba> getKontakteOsobe(Long id) throws SQLException {
        List<Osoba> lista = new ArrayList<>();
        Connection veza = openConnection();
        Statement stmt = veza.createStatement();
        String upit = "SELECT * FROM KONTAKTIRANE_OSOBE  WHERE OSOBA_ID = " + id;
        ResultSet rs = stmt.executeQuery(upit);
        Set<Long> odabraneOsobe = new TreeSet<>();

        while (rs.next()){
            Long idSimp = rs.getLong("KONTAKTIRANA_OSOBA_ID");
            odabraneOsobe.add(idSimp);
        }

        for(Long idKontakta : odabraneOsobe){
            lista.add(getOneOsoba(idKontakta));
        }

        closeConnection(veza);
        return lista;
    }

    /**
     * Postavlja kontakte osobe u pomocnu tablicu KONTAKTIRANE_OSOBE
     * @param idKontakata Lista id-eva kontakata
     * @param idOsobe id osobe ciji se kontakti dodaju
     * @throws SQLException
     * @throws IOException
     */
    public void setKontakteOsobe(List<Long> idKontakata, Long idOsobe) throws SQLException, IOException {
        Connection veza = openConnection();

        for(Long i : idKontakata){
            PreparedStatement unesiSimptom = veza.prepareStatement(
                    " INSERT INTO KONTAKTIRANE_OSOBE(OSOBA_ID, KONTAKTIRANA_OSOBA_ID) VALUES (?,?)");
            unesiSimptom.setLong(1, idOsobe);
            unesiSimptom.setLong(2,i);
            unesiSimptom.executeUpdate();
        }
    }

    /**
     * Dohvaca jednu osobu iz baze podataka
     * @param id id osobe koja se dohvaca iz baze
     * @return objekt klase Osoba
     * @throws SQLException
     */
    public Osoba getOneOsoba(Long id) throws SQLException {
        Osoba osoba = null;
        Connection veza = openConnection();
        String upit = "SELECT * FROM OSOBA WHERE ID = " + id;
        Statement stmt = veza.createStatement();
        ResultSet rs = stmt.executeQuery(upit);

        while (rs.next()) {
            String ime = rs.getString("IME");
            String prezime = rs.getString("PREZIME");
            Date datumRod = rs.getDate("DATUM_RODJENJA");
            Long idZup = rs.getLong("ZUPANIJA_ID");
            Long idBol = rs.getLong("BOLEST_ID");
            Osoba nova = new Osoba.Builder(id,ime,prezime)
                    .withDatumRod(datumRod)
                    .withZupanija(getOneZupanija(idZup))
                    .withBolest(getOneBolest(idBol))
                    .withOsoba(getKontakteOsobe(id))
                    .build();
            osoba = nova;
        }

        closeConnection(veza);

        return osoba;
    }

    /**
     * Dodaje novu osobu u bazu podataka
     * @param osoba Objekt klase Osoba koji se dodaje u bazu podataka
     * @throws SQLException
     */
    public void addNewOsoba(Osoba osoba) throws SQLException {
        Connection veza = openConnection();
        PreparedStatement unesiOsobu = veza.prepareStatement(
                "INSERT INTO OSOBA(IME, PREZIME, DATUM_RODJENJA, ZUPANIJA_ID,BOLEST_ID) VALUES(?,?,?,?,?)");
        unesiOsobu.setString(1, osoba.getIme());
        unesiOsobu.setString(2,osoba.getPrezime());
        unesiOsobu.setDate(3,osoba.getDatumRod());
        unesiOsobu.setLong(4,osoba.getZupanija().getId());
        unesiOsobu.setLong(5,osoba.getZarazenBolescu().getId());
        unesiOsobu.executeUpdate();

        closeConnection(veza);
    }

    //1. zadatak
    public void deleteOsoba(Osoba osoba) throws SQLException{
        Connection veza = openConnection();

        PreparedStatement obrisiOsobu = veza.prepareStatement(
        "DELETE FROM KONTAKTIRANE_OSOBE WHERE OSOBA_ID = ? OR KONTAKTIRANA_OSOBA_ID = ?;");

        PreparedStatement obrisi = veza.prepareStatement("DELETE FROM OSOBA WHERE id = ?;");

        obrisiOsobu.setLong(1,osoba.getId());
        obrisiOsobu.setLong(2,osoba.getId());
        obrisi.setLong(1,osoba.getId());
        obrisiOsobu.executeUpdate();
        obrisi.executeUpdate();

        closeConnection(veza);
    }

}

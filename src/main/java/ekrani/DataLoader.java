package main.java.ekrani;
import hr.java.covidportal.genericsi.KlinikaZaInfektivneBolesti;
import hr.java.covidportal.model.*;
import hr.java.covidportal.Enum.*;
import hr.java.covidportal.sort.CovidSorter;


import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


import main.BazaPodataka;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Klasa koja ucitava podatke u liste koje se koriste u programu
 */
public class DataLoader {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);


    private List<Zupanija> zupanije = new ArrayList<>();
    private List<Simptom> simptomi = new ArrayList<>();
    private List<Bolest> bolesti = new ArrayList<>();
    private List<hr.java.covidportal.model.Osoba> osobe = new ArrayList<>();
    private Map<Bolest, List<Osoba>> oboljeli = new HashMap<>();

    /**
     * Metoda koja ucitava sve podatke pri pozivu, poziva se u konstruktoru kako bi svaki objekt klase
     * sadrzavao sve potrebne podatke
     */
    private void setAll() {

        try {
            BazaPodataka baza = new BazaPodataka();
            zupanije = baza.getAllZupanije();

            simptomi = baza.getAllSimps();
            logger.info("Simptomi uspjesno unijeti!");

            bolesti = baza.getAllBolesti();
            logger.info("Bolesti uspjesno unijete!");

            osobe = baza.getAllOsobe();

            logger.info("Osobe uspjesno unijete!");

            //---Unosenje virusa i osoba u kliniku---//

            List<Virus> tempV = bolesti.stream()
                    .filter(bolest -> bolest instanceof Virus)
                    .map(bolest -> (Virus) bolest)
                    .collect(Collectors.toList());

            List<Osoba> tempO = osobe.stream()
                    .filter(osoba -> osoba.getZarazenBolescu() instanceof Virus)
                    .collect(Collectors.toList());

            KlinikaZaInfektivneBolesti klinika = new KlinikaZaInfektivneBolesti(tempV, tempO);

            //---Ispis sortiranih virusa + mjerenje sortiranja---//

            Collections.sort(klinika.getUneseniVirusi(),Virus.ReverseVirusSorter);

            //ISPIS ZUPANIJE S NAJVECIM POSTOTKOM ZARAZENIH//

            Zupanija najviseZarazenih = zupanije.stream()
                    .max(new CovidSorter()).get();

        }catch (SQLException e){
            logger.info("Exception caught!");
        }


    }

    public DataLoader() {
        setAll();
    }

    public List<Zupanija> getZupanije() {
        return zupanije;
    }

    public List<Simptom> getSimptomi() {
        return simptomi;
    }

    public List<Bolest> getBolesti() {
        return bolesti;
    }

    public List<Osoba> getOsobe() {
        return osobe;
    }

    public Map<Bolest, List<Osoba>> getOboljeli() {
        return oboljeli;
    }

    private static Osoba unosOsobe(BufferedReader reader, List<Zupanija> zupanije, List<Bolest> bolesti, List<Osoba> osobe) {
        Long id = null;
        String ime = null;
        String prezime = null;
        String datumRod = null;
        Integer odabranaZupanija = 0;
        Integer odabranaBolest = 0;
        String sviKontakti = null;
        List<Long> odabraniKontakti = new ArrayList<>();

        try {

            String line = reader.readLine();
            if(line == null) return null;
            id = Long.valueOf(line);
            line = reader.readLine();
            ime = line;
            line = reader.readLine();
            prezime = line;
            //line = reader.readLine();
            //datumRod = line;
            line = reader.readLine();
            odabranaZupanija = Integer.valueOf(line);
            line = reader.readLine();
            odabranaBolest = Integer.valueOf(line);
            line = reader.readLine();
            sviKontakti = line;

            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(sviKontakti);
            while (m.find()) {
                Long n = Long.parseLong(m.group());
                odabraniKontakti.add(Long.valueOf(n));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


        Zupanija zupanijaOsobe = null;
        for(Zupanija z : zupanije){
            if(z.getId() == Long.valueOf(odabranaZupanija)){
                zupanijaOsobe = z;
            }
        }


        Bolest bolestOsobe = null;
        for (Bolest b : bolesti){
            if(b.getId() == Long.valueOf(odabranaBolest)){
                bolestOsobe = b;
            }
        }

        List<Osoba> kontaktiOsobe = new ArrayList<>();

        if(osobe.size() > 0){
            for(Osoba o: osobe){
                for(Long k : odabraniKontakti){
                    if(o.getId() == k){
                        kontaktiOsobe.add(o);
                    }
                }
            }

        }

        Osoba osoba = new Osoba.Builder(id,ime,prezime)
                //.withDatumRod(datumRod)
                .withZupanija(zupanijaOsobe)
                .withBolest(bolestOsobe)
                .withOsoba(kontaktiOsobe)
                .build();

        return osoba;
    }

    /**
     * Unos bolesti koje osoba moze imati
     * @return Objekt klase Bolest
     */
    private static Bolest unosBolesti(BufferedReader reader, List<Simptom> simptomi) {
        String naziv = null;
        Long id = null;
        String uneseniSimptomi = null;
        List<Simptom> simptomiBolesti = new ArrayList<>();
        List<Long> sviSimptomi = new ArrayList<>();
        Integer zaraznost = null;

        try {

            String line = reader.readLine();
            if(line == null) return null;
            id = Long.valueOf(line);
            line = reader.readLine();
            naziv = line;
            line = reader.readLine();
            uneseniSimptomi = line;
            line = reader.readLine();
            zaraznost = Integer.valueOf(line);

            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(uneseniSimptomi);
            while (m.find()) {
                Long n = Long.parseLong(m.group());
                sviSimptomi.add(Long.valueOf(n));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        for(Simptom s: simptomi){
            for(Long l : sviSimptomi){
                if(s.getId() == l){
                    simptomiBolesti.add(s);
                }
            }
        }

        Bolest bolest;
        if (zaraznost == 1){
            bolest = new Virus(id,naziv,simptomiBolesti);
        }else{
            bolest = new Bolest(id,naziv,simptomiBolesti);
        }

        return bolest;
    }

    /**
     * Unos zupanija u kojima osoba moze zivjeti
     * @return Objekt klase Zupanija
     */
    private static Zupanija unosZupanije(BufferedReader reader) {
        String naziv = null;
        Long id = null;
        Integer brojStanovnika = 0;
        Integer brojZarazenih = 0;

        try {

            String line = reader.readLine();
            if(line == null) return null;
            id = Long.valueOf(line);
            line = reader.readLine();
            naziv = line;
            line = reader.readLine();
            brojStanovnika = Integer.valueOf(line);
            line = reader.readLine();
            brojZarazenih = Integer.valueOf(line);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Zupanija zupanija = new Zupanija(id,naziv,brojStanovnika, brojZarazenih);
        return zupanija;
    }

    /**
     * Unos simptoma bolesti
     * @return Objekt klase Simptom
     */
    private static Simptom unosSimptoma(BufferedReader reader){
        Long id = null;
        String naziv = null;
        String vrijednost = null;
        String opis = null;

        try {

            String line = reader.readLine();
            if(line == null) return null;
            id = Long.valueOf(line);
            line = reader.readLine();
            naziv = line;
            line = reader.readLine();
            vrijednost = line;

        } catch (IOException e) {
            e.printStackTrace();
        }

        UcestalostSimptoma.valueOf(vrijednost);
        if(vrijednost.compareToIgnoreCase(UcestalostSimptoma.RIJETKO.getUcestalost()) == 0){
            vrijednost = UcestalostSimptoma.RIJETKO.getUcestalost();
        }else if (vrijednost.compareToIgnoreCase(UcestalostSimptoma.SREDNJE.getUcestalost()) == 0){
            vrijednost = UcestalostSimptoma.SREDNJE.getUcestalost();
        }else if (vrijednost.compareToIgnoreCase(UcestalostSimptoma.CESTO.getUcestalost()) == 0){
            vrijednost = UcestalostSimptoma.CESTO.getUcestalost();
        }

        Simptom simptom = new Simptom(id,naziv,vrijednost);
        return simptom;
    }

}

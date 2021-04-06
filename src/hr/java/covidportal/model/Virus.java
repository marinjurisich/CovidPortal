package hr.java.covidportal.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

/**
 * Podklasa Bolesti, implementira sucelje "Zarazno"
 */
public class Virus extends hr.java.covidportal.model.Bolest implements Zarazno, Serializable {

    public Virus(Long id, String naziv, List<Simptom> simptomi) {
        super(id, naziv, simptomi);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Prima objekt klase Osoba i postavlja Virus u "preneseneBolesti" svih osoba u polju "kontaktiraneOsobe"
     * @param osoba
     */
    @Override
    public void prelazakZarazeNaOsobu(Osoba osoba){
        osoba.setPreneseneBolesti(this);
    }

    public static Comparator<Virus> ReverseVirusSorter = new Comparator<Virus>() {

        @Override
        public int compare(Virus v1, Virus v2){
            return v2.getNaziv().compareTo(v1.getNaziv());
        }
    };
}

package hr.java.covidportal.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

/**
 * Klasa Osoba s implementiranim Builder Pattern-om
 */
public class Osoba implements Serializable {


    private Long id;
    private String ime;
    private String prezime;
    private Date datumRod;
    private Zupanija zupanija;
    private Bolest zarazenBolescu;
    private List<Osoba> kontaktiraneOsobe;
    private Bolest preneseneBolesti;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Osoba osoba = (Osoba) o;
        return Objects.equals(id, osoba.id) &&
                Objects.equals(ime, osoba.ime) &&
                Objects.equals(prezime, osoba.prezime) &&
                Objects.equals(datumRod, osoba.datumRod) &&
                Objects.equals(zupanija, osoba.zupanija) &&
                Objects.equals(zarazenBolescu, osoba.zarazenBolescu) &&
                Objects.equals(kontaktiraneOsobe, osoba.kontaktiraneOsobe) &&
                Objects.equals(preneseneBolesti, osoba.preneseneBolesti);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ime, prezime, datumRod, zupanija, zarazenBolescu,
                kontaktiraneOsobe, preneseneBolesti);
    }

    @Override
    public String toString(){
        return this.getIme() + " " + this.getPrezime();
    }


    /**
     * Implementacija Builder Pattern-a za konstrukciju klase, mora imati barem ime i prezime i id osobe
     */
    public static class Builder{
        private Long id;
        private String ime;
        private String prezime;
        private Date datumRod;
        private Zupanija zupanija;
        private Bolest zarazenBolescu;
        private List<Osoba> kontaktiraneOsobe;
        private Bolest preneseneBolesti;

        public Builder(Long id, String ime, String prezime){
            this.id = id;
            this.ime = ime;
            this.prezime = prezime;
        }

        public Builder withDatumRod(Date datumRod){
            this.datumRod = datumRod;
            return this;
        }

        public Builder withZupanija(Zupanija zupanija){
            this.zupanija = zupanija;
            return this;
        }

        public Builder withBolest(Bolest zarazenBolescu){
            this.zarazenBolescu = zarazenBolescu;
            return this;
        }

        public Builder withOsoba(List<Osoba> kontaktiraneOsobe){
            this.kontaktiraneOsobe = kontaktiraneOsobe;
            return this;
        }

        public Builder withPreneseneBolesti(Bolest preneseneBolesti){
            this.preneseneBolesti = preneseneBolesti;
            return this;
        }

        /**
         * Poziva se za konstruiranje objekta klase Osoba
         * @return Osoba o
         */
        public Osoba build(){
            Osoba osoba = new Osoba();
            osoba.id = this.id;
            osoba.ime = this.ime;
            osoba.prezime = this.prezime;
            osoba.datumRod = this.datumRod;
            osoba.zupanija = this.zupanija;
            osoba.zarazenBolescu = this.zarazenBolescu;
            osoba.kontaktiraneOsobe = this.kontaktiraneOsobe;
            osoba.preneseneBolesti = this.preneseneBolesti;

            //Pattern Matching
            if(zarazenBolescu instanceof Virus virus){
                for(Osoba o: kontaktiraneOsobe){
                    virus.prelazakZarazeNaOsobu(o);
                }
            }

            return osoba;
        }

    }




    private Osoba() {}

    public Bolest getPreneseneBolesti() {
        return preneseneBolesti;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPreneseneBolesti(Bolest preneseneBolesti) {
        this.preneseneBolesti = preneseneBolesti;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Date getDatumRod() {
        return datumRod;
    }

    public void setDatumRod(Date datumRod) {
        this.datumRod = datumRod;
    }

    public Zupanija getZupanija() {
        return zupanija;
    }

    public void setZupanija(Zupanija zupanija) {
        this.zupanija = zupanija;
    }

    public Bolest getZarazenBolescu() {
        return zarazenBolescu;
    }

    public void setZarazenBolescu(Bolest zarazenBolescu) {
        this.zarazenBolescu = zarazenBolescu;
    }

    public List<Osoba> getKontaktiraneOsobe() {
        return kontaktiraneOsobe;
    }

    public void setKontaktiraneOsobe(List<Osoba> kontaktiraneOsobe) {
        this.kontaktiraneOsobe = kontaktiraneOsobe;
    }
}

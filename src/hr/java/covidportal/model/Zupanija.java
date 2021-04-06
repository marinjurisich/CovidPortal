package hr.java.covidportal.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Sadrzi naziv i broj stanovnika zupanije
 */
public class Zupanija extends ImenovaniEntitet implements Serializable {
    private Integer brojStanovnika;
    private Integer brojZarazenih;

    public Double izracunajPostotak(){
        return (double)this.brojZarazenih/this.brojStanovnika;
    }

    public Integer getBrojZarazenih() {
        return brojZarazenih;
    }

    public void setBrojZarazenih(Integer brojZarazenih) {
        this.brojZarazenih = brojZarazenih;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zupanija zupanija = (Zupanija) o;
        return Objects.equals(brojStanovnika, zupanija.brojStanovnika);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brojStanovnika);
    }

    @Override
    public String toString(){
        return this.getNaziv();
    }

    public Zupanija(Long id, String naziv, Integer brojStanovnika, Integer brojZarazenih){
        super(id, naziv);
        this.brojStanovnika = brojStanovnika;
        this.brojZarazenih = brojZarazenih;
    }

    public Integer getBrojStanovnika(){
        return brojStanovnika;
    }



    public void setBrojStanovnika(Integer brojStanovnika){
        this.brojStanovnika = brojStanovnika;
    }

}

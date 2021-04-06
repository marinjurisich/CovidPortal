package hr.java.covidportal.model;


import java.io.Serializable;
import java.util.Objects;

/**
 * Sadrzi naziv i ucestalost simptoma
 */
public class Simptom extends ImenovaniEntitet implements Serializable {
    private String vrijednost;


    public Simptom(Long id, String naziv, String vrijednost) {
        super(id, naziv);
        this.vrijednost = vrijednost;

    }

    @Override
    public String toString(){
        return this.getNaziv();
    }

    public String getVrijednost() {
        return vrijednost;
    }

    public void setVrijednost(String vrijednost) {
        this.vrijednost = vrijednost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Simptom simptom = (Simptom) o;
        return Objects.equals(vrijednost, simptom.vrijednost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vrijednost);
    }
}

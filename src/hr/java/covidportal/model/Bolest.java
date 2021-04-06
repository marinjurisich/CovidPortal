package hr.java.covidportal.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Sadrzi naziv i simptome bolesti
 */
public class Bolest extends ImenovaniEntitet implements Serializable {
    private List<Simptom> simptomi;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bolest bolest = (Bolest) o;
        return Objects.equals(simptomi, bolest.simptomi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(simptomi);
    }

    @Override
    public String toString(){
        return this.getNaziv();
    }

    /**
     * Konstruktor koji prima naziv i polje simptoma
     * @param naziv
     * @param simptomi
     */
    public Bolest(Long id, String naziv, List<Simptom> simptomi) {
        super(id,naziv);
        this.simptomi = simptomi;
    }

    public List<Simptom> getSimptomi() {
        return simptomi;
    }

    public void setSimptomi(List<Simptom> simptomi) {
        this.simptomi = simptomi;
    }
}

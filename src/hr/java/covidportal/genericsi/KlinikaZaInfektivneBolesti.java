package hr.java.covidportal.genericsi;

import hr.java.covidportal.model.Osoba;
import hr.java.covidportal.model.Virus;

import java.util.*;

public class KlinikaZaInfektivneBolesti <T extends Virus, S extends Osoba>{

    public KlinikaZaInfektivneBolesti(List<T> uneseniVirusi, List<S> zarazeneOsobe) {
        this.uneseniVirusi = uneseniVirusi;
        this.zarazeneOsobe = zarazeneOsobe;
    }

    List<T> uneseniVirusi = new ArrayList<T>();
    List<S> zarazeneOsobe = new ArrayList<S>();

    public List<T> getUneseniVirusi() {
        return uneseniVirusi;
    }

    public List<S> getZarazeneOsobe() {
        return zarazeneOsobe;
    }

    public void setUneseniVirusi(List<T> uneseniVirusi) {
        this.uneseniVirusi = uneseniVirusi;
    }

    public void setZarazeneOsobe(List<S> zarazeneOsobe) {
        this.zarazeneOsobe = zarazeneOsobe;
    }

    public void dodajVirus(T virus){
        this.uneseniVirusi.add(virus);
    }

    public void dodajOsobu(S osoba){
        this.zarazeneOsobe.add(osoba);
    }
}

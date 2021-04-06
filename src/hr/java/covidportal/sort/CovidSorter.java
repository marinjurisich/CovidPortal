package hr.java.covidportal.sort;

import hr.java.covidportal.model.Zupanija;

import java.util.Comparator;
/**
 * Klasa za sortiranje zupanija prema postotku zarazenih
 * **/
public class CovidSorter implements Comparator<Zupanija> {

    @Override
    public int compare(Zupanija z1, Zupanija z2) {
        return z1.izracunajPostotak().compareTo(z2.izracunajPostotak());
    }

}

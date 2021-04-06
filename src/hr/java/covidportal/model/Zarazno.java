package hr.java.covidportal.model;

/**
 * Koristi se za implementaciju zaraznosti u klasi Virus
 */
public interface Zarazno {
    void prelazakZarazeNaOsobu(hr.java.covidportal.model.Osoba osoba);
}

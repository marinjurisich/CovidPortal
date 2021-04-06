package hr.java.covidportal.iznimke;

/**
 * Oznacena iznimka koja se baca ako se Osobi ponovo unese isti kontakt
 */
public class DuplikatKontaktiraneOsobe extends Exception {

    public DuplikatKontaktiraneOsobe(String message){
        super(message);
    }

    public DuplikatKontaktiraneOsobe(Throwable cause){
        super(cause);
    }

    public  DuplikatKontaktiraneOsobe(String message, Throwable cause){
        super(message,cause);
    }

}

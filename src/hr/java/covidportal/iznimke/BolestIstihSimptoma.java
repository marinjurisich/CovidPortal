package hr.java.covidportal.iznimke;

/**
 * Neoznacena iznimka koja se baca ako se unesu dvije identicne bolesti
 */
public class BolestIstihSimptoma extends RuntimeException {

    public BolestIstihSimptoma(String message){
        super(message);
    }

    public BolestIstihSimptoma(Throwable cause){
        super(cause);
    }

    public  BolestIstihSimptoma(String message, Throwable cause){
        super(message,cause);
    }

}

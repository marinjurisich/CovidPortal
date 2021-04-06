package hr.java.covidportal.model;

import java.io.Serializable;

/**
 * Apstraktna klasa koja sadrzi String naziv, nasljeduju je sve klase koje imaju naziv
 */
public abstract class ImenovaniEntitet implements Serializable {
    private Long id;
    private String naziv;

    public ImenovaniEntitet(Long id, String naziv) {
        this.id = id;
        this.naziv = naziv;
    }

    public String getNaziv() {
        return naziv;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}

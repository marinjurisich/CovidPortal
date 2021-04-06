package hr.java.covidportal.Enum;

public enum UcestalostSimptoma {
    DEFAULT("Vrijednost nije postavljena"),
    RIJETKO("RIJETKO"),
    SREDNJE("SREDNJE"),
    CESTO("CESTO"),
    PRODUKTIVNI("Produktivni"),
    VISOKA("Produktivni"),
    JAKA("Jaka"),
    UVIJEK("Uvijek"),
    INTENZIVNO("Intenzivno");

    public String toString(){
        switch(this){
            case RIJETKO :
                return "Rijetko";
            case SREDNJE :
                return "Srednje";
            case CESTO :
                return "Cesto";
            case PRODUKTIVNI :
                return "Produktivni";
            case VISOKA :
                return "Visoka";
            case JAKA :
                return "Jaka";
            case UVIJEK :
                return "Uvijek";
            case INTENZIVNO :
                return  "Intenzivno";
        }
        return null;
    }

    public String getUcestalost() {
        return ucestalost;
    }

    private String ucestalost;

    private UcestalostSimptoma(String ucestalost){
        this.ucestalost = ucestalost;
    }


}

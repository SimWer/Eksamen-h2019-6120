package com.eksamen.eksamen_h2019_6120;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Comparator;

public class Tilsyn implements Serializable {
    private String tilsynsobjektid, orgnummer, navn, adrlinje1,
            adrlinje2, postnr, poststed, tilsynid, sakref, status,
            dato, total_karakter, tilsynbesoektype, tema1_no, karakter1,
            tema2_no, karakter2, tema3_no, karakter3, tema4_no, karater4;

    private static final String KOL_TILSYNOBJEKTID      = "tilsynsobjektid";
    private static final String KOL_ORGNUMMER           = "orgnummer";
    private static final String KOL_NAVN                = "navn";
    private static final String KOL_ADRLINJE1           = "adrlinje1";
    private static final String KOL_ADRLINJE2           = "adrlinje2";
    private static final String KOL_POSTNR              = "postnr";
    private static final String KOL_POSTSTED            = "poststed";
    private static final String KOL_TILSYNID            = "tilsynid";
    private static final String KOL_SAKREF              = "sakref";
    private static final String KOL_STATUS              = "status";
    private static final String KOL_DATO                = "dato";
    private static final String KOL_TOTKARAKTER         = "total_karakter";
    private static final String KOL_TILSYNBESOEKTYPE    = "tilsynbesoektype";
    private static final String KOL_TEMA1_NO            = "tema1_no";
    private static final String KOL_KARAKTER1           = "karakter1";
    private static final String KOL_TEMA2_NO            = "tema2_no";
    private static final String KOL_KARAKTER2           = "karakter2";
    private static final String KOL_TEMA3_NO            = "tema3_no";
    private static final String KOL_KARATKER3           = "karakter3";
    private static final String KOL_TEMA4_NO            = "tema4_no";
    private static final String KOL_KARAKTER4           = "karater4";


    public Tilsyn(JSONObject tilsynJSON) {

        this.tilsynsobjektid    = tilsynJSON.optString(KOL_TILSYNOBJEKTID);
        this.orgnummer          = tilsynJSON.optString(KOL_ORGNUMMER);
        this.navn               = tilsynJSON.optString(KOL_NAVN);
        this.adrlinje1          = tilsynJSON.optString(KOL_ADRLINJE1);
        this.adrlinje2          = tilsynJSON.optString(KOL_ADRLINJE2);
        this.postnr             = tilsynJSON.optString(KOL_POSTNR);
        this.poststed           = tilsynJSON.optString(KOL_POSTSTED);
        this.tilsynid           = tilsynJSON.optString(KOL_TILSYNID);
        this.sakref             = tilsynJSON.optString(KOL_SAKREF);
        this.status             = tilsynJSON.optString(KOL_STATUS);
        this.dato               = tilsynJSON.optString(KOL_DATO);
        this.total_karakter     = tilsynJSON.optString(KOL_TOTKARAKTER);
        this.tilsynbesoektype   = tilsynJSON.optString(KOL_TILSYNBESOEKTYPE);
        this.tema1_no           = tilsynJSON.optString(KOL_TEMA1_NO);
        this.karakter1          = tilsynJSON.optString(KOL_KARAKTER1);
        this.tema2_no           = tilsynJSON.optString(KOL_TEMA2_NO);
        this.karakter2          = tilsynJSON.optString(KOL_KARAKTER2);
        this.tema3_no           = tilsynJSON.optString(KOL_TEMA3_NO);
        this.karakter3          = tilsynJSON.optString(KOL_KARATKER3);
        this.tema4_no           = tilsynJSON.optString(KOL_TEMA4_NO);
        this.karater4           = tilsynJSON.optString(KOL_KARAKTER4);

    }

    // Metoder for å sortere basert på ulike ønsker fra bruker
    // Kilde: https://beginnersbook.com/2013/12/java-arraylist-of-object-sort-example-comparable-and-comparator/
    public static Comparator<Tilsyn> tilsynNavnComparatorStigende = new Comparator<Tilsyn>() {
        @Override
        public int compare(Tilsyn t1, Tilsyn t2) {
            String tilsynNavn1 = t1.getNavn();
            String tilsynNavn2 = t2.getNavn();

            return tilsynNavn1.compareTo(tilsynNavn2);
        }
    };

    public static Comparator<Tilsyn> tilsynNavnComparatorSynkende = new Comparator<Tilsyn>() {
        @Override
        public int compare(Tilsyn t1, Tilsyn t2) {
            String tilsynNavn1 = t1.getNavn();
            String tilsynNavn2 = t2.getNavn();

            return tilsynNavn2.compareTo(tilsynNavn1);
        }
    };

    public static Comparator<Tilsyn> tilsynKarakterStigende = new Comparator<Tilsyn>() {
        @Override
        public int compare(Tilsyn t1, Tilsyn t2) {
            String tilsynNavn1 = t1.getTotal_karakter();
            String tilsynNavn2 = t2.getTotal_karakter();

            return tilsynNavn1.compareTo(tilsynNavn2);
        }
    };

    protected static Comparator<Tilsyn> tilsynKarakterSynkende = new Comparator<Tilsyn>() {
        @Override
        public int compare(Tilsyn t1, Tilsyn t2) {
            String tilsynNavn1 = t1.getTotal_karakter();
            String tilsynNavn2 = t2.getTotal_karakter();

            return tilsynNavn2.compareTo(tilsynNavn1);
        }
    };

    @Override
    public String toString() {
        return "Navn: " + this.navn + "  " + "Karakter: " + total_karakter;
    }

    public String getTilsynsobjektid() {
        return tilsynsobjektid;
    }

    public String getOrgnummer() {
        return orgnummer;
    }

    public String getNavn() {
        return navn;
    }

    public String getAdrlinje1() {
        return adrlinje1;
    }

    public String getAdrlinje2() {
        return adrlinje2;
    }

    public String getPostnr() {
        return postnr;
    }

    public String getPoststed() {
        return poststed;
    }

    public String getTilsynid() {
        return tilsynid;
    }

    public String getSakref() {
        return sakref;
    }

    public String getStatus() {
        return status;
    }

    public String getDato() {
        return dato;
    }

    public String getTotal_karakter() {
        return total_karakter;
    }

    public String getTilsynbesoektype() {
        return tilsynbesoektype;
    }

    public String getTema1_no() {
        return tema1_no;
    }

    public String getKarakter1() {
        return karakter1;
    }

    public String getTema2_no() {
        return tema2_no;
    }

    public String getKarakter2() {
        return karakter2;
    }

    public String getTema3_no() {
        return tema3_no;
    }

    public String getKarakter3() {
        return karakter3;
    }

    public String getTema4_no() {
        return tema4_no;
    }

    public String getKarater4() {
        return karater4;
    }
}

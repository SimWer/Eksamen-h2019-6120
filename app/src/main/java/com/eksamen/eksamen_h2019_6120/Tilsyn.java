package com.eksamen.eksamen_h2019_6120;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Comparator;

public class Tilsyn implements Serializable, Parcelable {
    private String tilsynsobjektid, orgnummer, navn, adrlinje1,
            adrlinje2, postnr, poststed, tilsynid, sakref, status,
            dato, total_karakter, tilsynbesoektype, tema1_no, karakter1,
            tema2_no, karakter2, tema3_no, karakter3, tema4_no, karater4;
    // Id som bestemmes ved lagingen av tilsyn, på denne måten slipper jeg å legge til mange switch'er
    private int bilde_id;

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

    /**
     *
     * Comparator for å sortere på Stigende navn-verdi
     *
     * */
    public static Comparator<Tilsyn> tilsynNavnComparatorStigende = new Comparator<Tilsyn>() {
        @Override
        public int compare(Tilsyn t1, Tilsyn t2) {
            String tilsynNavn1 = t1.getNavn();
            String tilsynNavn2 = t2.getNavn();

            return tilsynNavn1.compareTo(tilsynNavn2);
        }
    };

    /**
     *
     * Comparator for å sortere på Synkende navn-verdi
     *
     * */

    public static Comparator<Tilsyn> tilsynNavnComparatorSynkende = new Comparator<Tilsyn>() {
        @Override
        public int compare(Tilsyn t1, Tilsyn t2) {
            String tilsynNavn1 = t1.getNavn();
            String tilsynNavn2 = t2.getNavn();

            return tilsynNavn2.compareTo(tilsynNavn1);
        }
    };

    /**
     *
     * Comparator for å sortere på Stigende karakter-verdi
     *
     * */

    public static Comparator<Tilsyn> tilsynKarakterStigende = new Comparator<Tilsyn>() {
        @Override
        public int compare(Tilsyn t1, Tilsyn t2) {
            String tilsynNavn1 = t1.getTotal_karakter();
            String tilsynNavn2 = t2.getTotal_karakter();

            return tilsynNavn1.compareTo(tilsynNavn2);
        }
    };

    /**
     *
     * Comparator for å sortere på Synkende karakter-verdi
     *
     * */

    protected static Comparator<Tilsyn> tilsynKarakterSynkende = new Comparator<Tilsyn>() {
        @Override
        public int compare(Tilsyn t1, Tilsyn t2) {
            String tilsynNavn1 = t1.getTotal_karakter();
            String tilsynNavn2 = t2.getTotal_karakter();

            return tilsynNavn2.compareTo(tilsynNavn1);
        }
    };

    protected Tilsyn(Parcel in) {
        tilsynsobjektid = in.readString();
        orgnummer = in.readString();
        navn = in.readString();
        adrlinje1 = in.readString();
        adrlinje2 = in.readString();
        postnr = in.readString();
        poststed = in.readString();
        tilsynid = in.readString();
        sakref = in.readString();
        status = in.readString();
        dato = in.readString();
        total_karakter = in.readString();
        tilsynbesoektype = in.readString();
        tema1_no = in.readString();
        karakter1 = in.readString();
        tema2_no = in.readString();
        karakter2 = in.readString();
        tema3_no = in.readString();
        karakter3 = in.readString();
        tema4_no = in.readString();
        karater4 = in.readString();
        bilde_id = in.readInt();
    }

    public static final Creator<Tilsyn> CREATOR = new Creator<Tilsyn>() {
        @Override
        public Tilsyn createFromParcel(Parcel in) {
            return new Tilsyn(in);
        }

        @Override
        public Tilsyn[] newArray(int size) {
            return new Tilsyn[size];
        }
    };

    /***
     *
     * Metode for å hente ut dato som ser bra ut fra Tilsynet.
     * Det ser vanligvis ut som 01012019 uten skille.
     *
     */

    public String getDatoMedSkille() {

        String aarstall = getDato().substring(4);
        String maaned = getDato().substring(2, 4);
        String dag = getDato().substring(0, 2);

        String datoSkille = dag + "." + maaned + "." + aarstall;


        return datoSkille;
    }

    /**
     *
     * toString er implemtert her da jeg trengte den tidlig i prosjektet
     *
     * */
    @Override
    public String toString() {
        return "Navn: " + this.navn + "  " + "Karakter: " + this.total_karakter + " Dato: " + this.dato;
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

    public String getPostnr() {
        return postnr;
    }

    public String getPoststed() {
        return poststed;
    }

    public String getTilsynid() {
        return tilsynid;
    }

    public String getDato() {
        return dato;
    }

    public String getTotal_karakter() {
        return total_karakter;
    }

    public int getBilde_id() {
        return bilde_id;
    }

    public void setBilde_id(int bilde_id) {
        this.bilde_id = bilde_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tilsynsobjektid);
        dest.writeString(orgnummer);
        dest.writeString(navn);
        dest.writeString(adrlinje1);
        dest.writeString(adrlinje2);
        dest.writeString(postnr);
        dest.writeString(poststed);
        dest.writeString(tilsynid);
        dest.writeString(sakref);
        dest.writeString(status);
        dest.writeString(dato);
        dest.writeString(total_karakter);
        dest.writeString(tilsynbesoektype);
        dest.writeString(tema1_no);
        dest.writeString(karakter1);
        dest.writeString(tema2_no);
        dest.writeString(karakter2);
        dest.writeString(tema3_no);
        dest.writeString(karakter3);
        dest.writeString(tema4_no);
        dest.writeString(karater4);
        dest.writeInt(bilde_id);
    }
}

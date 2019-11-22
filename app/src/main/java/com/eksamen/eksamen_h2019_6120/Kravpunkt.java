package com.eksamen.eksamen_h2019_6120;

import org.json.JSONObject;

public class Kravpunkt {

    private String tilsynid, dato, ordningsverdi, kravpunktnavn_no, karakter, tekst_no;

    private static final String KOL_TILSYNID        = "tilsynid";
    private static final String KOL_DATO            = "dato";
    private static final String KOL_ORDNINGSVERDI   = "ordningsverdi";
    private static final String KOL_KRAVPUNKT       = "kravpunktnavn_no";
    private static final String KOL_KARAKTER        = "karakter";
    private static final String KOL_TEKST           = "tekst_no";

    public Kravpunkt(JSONObject jsonObject) {
        this.tilsynid = jsonObject.optString(KOL_TILSYNID);
        this.dato = jsonObject.optString(KOL_DATO);
        this.ordningsverdi = jsonObject.optString(KOL_ORDNINGSVERDI);
        this.kravpunktnavn_no = jsonObject.optString(KOL_KRAVPUNKT);
        this.karakter = jsonObject.optString(KOL_KARAKTER);
        this.tekst_no = jsonObject.optString(KOL_TEKST);
    }


    public String getTilsynid() {
        return tilsynid;
    }

    public String getDato() {
        return dato;
    }

    public String getOrdningsverdi() {
        return ordningsverdi;
    }

    public String getKravpunktnavn_no() {
        return kravpunktnavn_no;
    }

    public String getKarakter() {
        return karakter;
    }

    public String getTekst_no() {
        return tekst_no;
    }

    @Override
    public String toString() {
        return  kravpunktnavn_no + "     " +
                karakter + '\n' +
                tekst_no + '\n';
    }
}

package com.eksamen.eksamen_h2019_6120;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SmilefjesAdapter extends ArrayAdapter {

    private MainActivity context;

    private ArrayList<Kravpunkt> kravpunktListe;


    public SmilefjesAdapter(Context context, ArrayList<Kravpunkt> kravpunktListe) {
        super(context, R.layout.kravpunkt_liste, kravpunktListe);
        this.context = (MainActivity) context;
        this.kravpunktListe = kravpunktListe;


    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View radView = layoutInflater.inflate(R.layout.kravpunkt_liste, null, false);

        ImageView smilefjesImageView = radView.findViewById(R.id.smilefjesBilde);
        TextView kravNummerText = radView.findViewById(R.id.kravNummer);
        TextView kravTittelText = radView.findViewById(R.id.kravTittel);
        TextView kravForklaringText = radView.findViewById(R.id.kravForklaring);

        Kravpunkt valgtKravpunkt = kravpunktListe.get(position);


        String kravNummer = valgtKravpunkt.getOrdningsverdi();
        String kravTittel = valgtKravpunkt.getKravpunktnavn_no();
        String kravForklaring = valgtKravpunkt.getTekst_no();
        String kravKarakter = valgtKravpunkt.getKarakter();

        switch (kravKarakter) {

            case "0":   smilefjesImageView.setImageResource(R.drawable.tilsyn_ok);
                        break;
            case"1":    smilefjesImageView.setImageResource(R.drawable.tilsyn_ok);
                        break;
            case"2":    smilefjesImageView.setImageResource(R.drawable.tilsyn_trenger_oppfolgning);
                        break;
            case"3":    smilefjesImageView.setImageResource(R.drawable.tilsyn_ikke_ok);
                        break;
            case"4":    smilefjesImageView.setImageResource(R.drawable.ikke_vurdert);
                        break;
            case "5":   smilefjesImageView.setImageResource(R.drawable.ikke_vurdert);
                        break;
            default:    break;
        }

        kravNummerText.setText(kravNummer);
        kravTittelText.setText(kravTittel);
        kravForklaringText.setText(kravForklaring);

        return radView;
    }



}

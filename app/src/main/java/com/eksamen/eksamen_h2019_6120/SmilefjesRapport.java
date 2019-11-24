package com.eksamen.eksamen_h2019_6120;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


/**
 *
 * En fragment-klasse for å vise en mer detaljert versjon av Tilsynet
 * Det vises da også med kravpunktene som er gjennomført under tilsynet
 *
 */
public class SmilefjesRapport extends Fragment {

    private OnFragmentInteractionListener mListener;

    private TextView bedriftNavn, bedriftAdresse, dato;

    private ImageView totalkarakter;

    private ListView kravlisteView;

    private Tilsyn valgtTilsyn;

    private View rotView;

    private String tilsynNavn, tilsynAdresse, tilsynDato;

    private int bilde_id;

    public SmilefjesRapport() {
        // Required empty public constructor
    }

    public static SmilefjesRapport newInstance(){
        return new SmilefjesRapport();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            valgtTilsyn = (Tilsyn)getArguments().getSerializable(MainActivity.ID);
            tilsynNavn = valgtTilsyn.getNavn();
            tilsynAdresse = valgtTilsyn.getAdrlinje1();
            tilsynDato = valgtTilsyn.getDatoMedSkille();
            bilde_id = valgtTilsyn.getBilde_id();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rotView = inflater.inflate(R.layout.fragment_smilefjes_fragment, container, false);

        bedriftNavn = rotView.findViewById(R.id.bedrift_plassholder);
        bedriftAdresse = rotView.findViewById(R.id.adresse_plasshodler);
        dato = rotView.findViewById(R.id.dato_felt);

        totalkarakter = rotView.findViewById(R.id.total_karakter_bilde);

        kravlisteView = rotView.findViewById(R.id.kravpunktListe);



        bedriftNavn.setText(tilsynNavn);
        bedriftAdresse.setText(tilsynAdresse);
        dato.setText(tilsynDato);
        totalkarakter.setImageResource(bilde_id);

        //Søk etter kravpunkt-listen fra Tilsynet og vis den i form av en listview
        if(valgtTilsyn != null) {
            RestController.kravpunkterRequest(valgtTilsyn.getTilsynid(), this.getContext(), kravlisteView );

        }

        // Inflate the layout for this fragment
        return rotView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

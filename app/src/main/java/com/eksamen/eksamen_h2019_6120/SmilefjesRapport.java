package com.eksamen.eksamen_h2019_6120;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SmilefjesRapport.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SmilefjesRapport extends Fragment {

    private OnFragmentInteractionListener mListener;

    private TextView bedriftNavn, bedriftAdresse;

    private ListView kravlisteView;

    private Tilsyn valgtTilsyn;

    private View rotView;

    private Kravpunkt valgtKravpunkt;

    private String tilsynid, tilsynNavn, tilsynAdresse;

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
            tilsynid = valgtTilsyn.getTilsynid();
            tilsynNavn = valgtTilsyn.getNavn();
            tilsynAdresse = valgtTilsyn.getAdrlinje1();



        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rotView = inflater.inflate(R.layout.fragment_smilefjes_fragment, container, false);

        bedriftNavn = rotView.findViewById(R.id.bedrift_plassholder);
        bedriftAdresse = rotView.findViewById(R.id.adresse_plasshodler);
        kravlisteView = rotView.findViewById(R.id.kravpunktListe);

        bedriftNavn.setText(tilsynNavn);
        bedriftAdresse.setText(tilsynAdresse);
        //Søk etter kravpunkt-listen fra Tilsynet og vis den i form av en listview
        RestController.kravpunkterRequest(valgtTilsyn.getTilsynid(), this.getContext(), kravlisteView );

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

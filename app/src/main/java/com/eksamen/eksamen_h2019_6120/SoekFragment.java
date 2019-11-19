package com.eksamen.eksamen_h2019_6120;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SoekFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SoekFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private String url;
    private EditText sokFelt, datoFelt;
    private Button sokKnapp;
    private Context context;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    public SoekFragment() {
        // Required empty public constructor
    }

    public static SoekFragment newInstance() {
        return new SoekFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rotView = inflater.inflate(R.layout.fragment_soek_fragment, container, false);

        sokFelt = rotView.findViewById(R.id.sokFelt);
        datoFelt = rotView.findViewById(R.id.datoFelt);
        sokKnapp = rotView.findViewById(R.id.sokKnapp);
        recyclerView = rotView.findViewById(R.id.recyclerTilsyn);

        context = rotView.getContext();

        datoFelt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar kalender = Calendar.getInstance();
                int aar = kalender.get(Calendar.YEAR);
                int maamed = kalender.get(Calendar.MONTH);
                int dag = kalender.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(context, android.R.style.Theme_Material_Light_Dialog, dateSetListener, aar, maamed, dag);
                dialog.show();

            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                String dato;
                if(month < 10) {
                    dato = "" + "0" + dayOfMonth + "0" + month +  year;
                } else {
                    dato = "" + "0" + dayOfMonth + month +  year;

                }
                datoFelt.setText(dato);
            }
        };

        sokKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = sokFelt.getText().toString() + "&dato=" + datoFelt.getText();
                RestController.tilsynRequest(url, context, recyclerView);
            }
        });

        mListener.onSoek(recyclerView);
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

        void onTilsynValgt(Tilsyn tilsyn);
        void onSoek(RecyclerView recyclerView);
    }
}

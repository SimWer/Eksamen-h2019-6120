package com.eksamen.eksamen_h2019_6120;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;


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

    private EditText sokFelt;

    private Button sokKnapp;

    private Context context;


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
        sokKnapp = rotView.findViewById(R.id.sokKnapp);
        recyclerView = rotView.findViewById(R.id.recyclerTilsyn);

        context = rotView.getContext();



        sokKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = sokFelt.getText().toString();
                RestController.tilsynRequest(url, context, recyclerView);
            }
        });

        //Kaller metoden ved onCreateView av fragmentet for å sjekke brukerinstillinger
        mListener.sjekkInstillinger(recyclerView);

        //------------------------------------------------------------------------------------------//
        //------------------------------------------------------------------------------------------//
        // ItemTouchHelper bruker for å fjerne objekter fra ArrayListen og fra RecyclerView
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP ,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                // Kodeeksempler hentet fra stackoverflow: https://stackoverflow.com/questions/50137310/confirm-dialog-before-swipe-delete-using-itemtouchhelper
                // Dialog for å bekrefte eller avkrefte sletting - Krav i oppgaveteksten
                new MaterialAlertDialogBuilder(viewHolder.itemView.getContext())
                        .setMessage(R.string.alert_beskjed)
                        .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RestController.tilsynArrayList.remove(viewHolder.getAdapterPosition());
                                RestController.tilsynAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                            }
                        }).setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Viktig slik at det som ble "swipet" settes tilbake til posisjonen sin - ellers blir området tomt!
                        RestController.tilsynAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    }
                })
                        .create()
                        .show();

            }
        });

        helper.attachToRecyclerView(recyclerView);

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
        void sjekkInstillinger(RecyclerView recyclerView);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}

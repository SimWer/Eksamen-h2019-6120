package com.eksamen.eksamen_h2019_6120;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;



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

    private TextInputEditText sokInput;

    private Button filter_knapp, sorter_knapp;

    private Context context;

    private Chip chip_aarstal;

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

        sokInput = rotView.findViewById(R.id.soekFelt);
        recyclerView = rotView.findViewById(R.id.recyclerTilsyn);
        chip_aarstal = rotView.findViewById(R.id.chip_aarstall);
        filter_knapp = rotView.findViewById(R.id.filter_knapp);
        sorter_knapp = rotView.findViewById(R.id.sort_knapp);

        context = rotView.getContext();


        // For å søke på "søke"-knappen på tastaturet bruker jeg denne metoden:
        // Kilde: https://stackoverflow.com/questions/3205339/android-how-to-make-keyboard-enter-button-say-search-and-handle-its-click

        sokInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                            String aarstall_filter = chip_aarstal.getText().toString();
                            url = sokInput.getText().toString() + " " + aarstall_filter;
                            RestController.tilsynRequest(url, context, recyclerView);

                    return true;
                }else {
                    return false;

                }
            }
        });


        filter_knapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtrerSoek(rotView);
            }
        });
        sorter_knapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sorterListe(rotView);
            }
        });



        //Kaller metoden ved onCreateView av fragmentet for å sjekke brukerinstillinger

        if(savedInstanceState != null) {
            TilsynListeAdapter tilsynListeAdapter = new TilsynListeAdapter(this.getContext());
            recyclerView.setAdapter(tilsynListeAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        } else {
            mListener.sjekkInstillinger(recyclerView);
        }

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
                new MaterialAlertDialogBuilder(viewHolder.itemView.getContext(), R.style.ThemeOverlay_MaterialComponents_Light)
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MainActivity.ARRAY_ID_STATE, RestController.tilsynArrayList);
    }

    /**
     *
     * Metode for å sortere RecyclerView basert på brukervalg.
     *
     * @param v View
     *
     * */
    private void sorterListe(View v) {

        final CharSequence[] sorteringsListe = v.getContext().getResources().getStringArray(R.array.sortering);
        final String[] valgtSorteringsMetode = {(String) sorteringsListe[0]};
        final Chip chipSortering = v.findViewById(R.id.chip_sorter);

        new MaterialAlertDialogBuilder(v.getContext(), R.style.ThemeOverlay_MaterialComponents_Dialog)
                .setSingleChoiceItems(sorteringsListe, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        valgtSorteringsMetode[0] = (String) sorteringsListe[which];
                    }
                }).setPositiveButton("Ferdig", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!valgtSorteringsMetode[0].isEmpty()) {
                    if(chipSortering.isCloseIconVisible()) {
                        chipSortering.setVisibility(View.GONE);
                    }
                    chipSortering.setText(valgtSorteringsMetode[0]);
                    chipSortering.setVisibility(View.VISIBLE);
                    RestController.tilsynAdapter.sorterAdapter(valgtSorteringsMetode[0]);
                }
            }
        }).setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();

        if(chipSortering.isCloseIconVisible()) {
            chipSortering.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chipSortering.setText("");
                    chipSortering.setVisibility(View.GONE);
                }
            });
        }


    }


    /**
     *
     * Metode for å filtrere søket basert på brukervalg.
     *
     * @param v View
     *
     * */

    //Inspirasjon : https://www.androidhive.info/2017/11/android-recyclerview-with-search-filter-functionality/

    private void filtrerSoek(View v) {

        final CharSequence[] aarstallListe = v.getContext().getResources().getStringArray(R.array.aarstall);
        final String[] valgAarstall = {(String)aarstallListe[0]};
        final Chip chipAarstall = v.findViewById(R.id.chip_aarstall);

        new MaterialAlertDialogBuilder(v.getContext(), R.style.ThemeOverlay_MaterialComponents_Dialog)
        .setSingleChoiceItems(aarstallListe, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                valgAarstall[0] = (String) aarstallListe[which];
            }
        }).setPositiveButton("Ferdig", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!valgAarstall[0].isEmpty()) {
                    if(chipAarstall.isCloseIconVisible()) {
                        chipAarstall.setVisibility(View.GONE);
                    }
                    chipAarstall.setText(valgAarstall[0]);
                    chipAarstall.setVisibility(View.VISIBLE);
                    String filtrering = valgAarstall[0];
                    RestController.tilsynAdapter.getFilter().filter(filtrering);
                }
            }
        }).setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();

        if(chipAarstall.isCloseIconVisible()) {
            chipAarstall.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RestController.tilsynAdapter.getFilter().filter("");
                    chipAarstall.setText("");
                    chipAarstall.setVisibility(View.GONE);
                }
            });
        }
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

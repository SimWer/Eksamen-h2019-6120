package com.eksamen.eksamen_h2019_6120;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;


public class TilsynListeAdapter extends RecyclerView.Adapter<TilsynListeAdapter.TilsynViewHolder> implements Filterable {

    private LayoutInflater inflater;
    private MainActivity aktiviteten;
    private ArrayList<Tilsyn> tilsynListeFiltrert;


    public TilsynListeAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        tilsynListeFiltrert = RestController.tilsynArrayList;
        this.aktiviteten = (MainActivity) context;
    }

    @NonNull
    @Override
    public TilsynListeAdapter.TilsynViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.tilsyn_liste_item, parent, false);

        return new TilsynViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull TilsynListeAdapter.TilsynViewHolder holder, int position) {
        Tilsyn tilsyn = tilsynListeFiltrert.get(position);
        holder.navn.setText(tilsyn.getNavn());
        holder.karakter.setImageResource(tilsyn.getBilde_id());
        holder.orgNr.setText(tilsyn.getOrgnummer());
        holder.postnr.setText(tilsyn.getPostnr());
        holder.poststed.setText(tilsyn.getPoststed());
        holder.dato.setText(tilsyn.getDato());
    }

    @Override
    public int getItemCount() {
        return tilsynListeFiltrert.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filtreringString = constraint.toString();
                if(filtreringString.isEmpty()) {
                    tilsynListeFiltrert = RestController.tilsynArrayList;
                } else {
                    if(tilsynListeFiltrert.isEmpty()) {
                        tilsynListeFiltrert = RestController.tilsynArrayList;
                    }
                    ArrayList<Tilsyn> filtrertListe = new ArrayList<>();

                    for(int i = 0; i < tilsynListeFiltrert.size(); i++) {
                        Tilsyn tilsyn = tilsynListeFiltrert.get(i);
                        if(tilsyn.getDato().toLowerCase().contains(filtreringString.toLowerCase())) {
                            filtrertListe.add(tilsyn);
                        }

                    }

                    tilsynListeFiltrert = filtrertListe;
                }
                FilterResults filterResultat = new FilterResults();
                filterResultat.values = tilsynListeFiltrert;

                return filterResultat;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                tilsynListeFiltrert = (ArrayList<Tilsyn>) results.values;

                // Legger til en if her, for om den søker på en liste som er oppdatert vil den returne en tom array
                // Den gjennomfører da søket på nytt, men den første listen og stringen
                if(tilsynListeFiltrert.isEmpty()) {
                    getFilter().filter(constraint);
                }
                notifyDataSetChanged();

            }
        };
    }

    public void sorterAdapter(String sValg) {

        switch (sValg) {
            case "Karakter stigende":
                Collections.sort(tilsynListeFiltrert, Tilsyn.tilsynKarakterStigende);
                notifyDataSetChanged();
                break;
            case "Karakter synkende":
                Collections.sort(tilsynListeFiltrert, Tilsyn.tilsynKarakterSynkende);
                notifyDataSetChanged();
                break;
            case "Navn stigende":
                Collections.sort(tilsynListeFiltrert, Tilsyn.tilsynNavnComparatorStigende);
                notifyDataSetChanged();
                break;
            case "Navn synkende":
                Collections.sort(tilsynListeFiltrert, Tilsyn.tilsynNavnComparatorSynkende);
                notifyDataSetChanged();
                break;
        }

    }

    class TilsynViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView navn, orgNr, postnr, poststed, dato;
        public final ImageView karakter;
        final TilsynListeAdapter adapter;

        public TilsynViewHolder(@NonNull View itemView, TilsynListeAdapter adapter) {
            super(itemView);
            this.navn = itemView.findViewById(R.id.navnFelt);
            this.karakter = itemView.findViewById(R.id.karakterFelt);
            this.orgNr = itemView.findViewById(R.id.orgNrFelt);
            this.postnr = itemView.findViewById(R.id.postNrFelt);
            this.poststed = itemView.findViewById(R.id.poststedFelt);
            this.dato = itemView.findViewById(R.id.datoFelt);
            this.adapter = adapter;

            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            Tilsyn tilsyn = tilsynListeFiltrert.get(getLayoutPosition());
            aktiviteten.onTilsynValgt(tilsyn);
        }// Slutt på onClick
    }// Slutt på holder-klassen
}// Slutt på adapter-klassen

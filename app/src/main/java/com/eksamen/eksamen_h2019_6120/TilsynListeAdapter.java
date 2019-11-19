package com.eksamen.eksamen_h2019_6120;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;


public class TilsynListeAdapter extends RecyclerView.Adapter<TilsynListeAdapter.TilsynViewHolder> {

    private LayoutInflater inflater;
    private MainActivity aktiviteten;

    public TilsynListeAdapter(Context context) {
        inflater = LayoutInflater.from(context);
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
        Tilsyn tilsyn = RestController.tilsynArrayList.get(position);
        holder.navn.setText(tilsyn.getNavn());
        holder.karakter.setText(tilsyn.getTotal_karakter());
        holder.orgNr.setText(tilsyn.getOrgnummer());
        holder.postnr.setText(tilsyn.getPostnr());
        holder.poststed.setText(tilsyn.getPoststed());
    }

    @Override
    public int getItemCount() {
        return RestController.tilsynArrayList.size();
    }

    class TilsynViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView navn, karakter, orgNr, postnr, poststed;
        final TilsynListeAdapter adapter;

        public TilsynViewHolder(@NonNull View itemView, TilsynListeAdapter adapter) {
            super(itemView);
            this.navn = itemView.findViewById(R.id.navnFelt);
            this.karakter = itemView.findViewById(R.id.karakterFelt);
            this.orgNr = itemView.findViewById(R.id.orgNrFelt);
            this.postnr = itemView.findViewById(R.id.postNrFelt);
            this.poststed = itemView.findViewById(R.id.poststedFelt);
            this.adapter = adapter;
            Collections.sort(RestController.tilsynArrayList, Tilsyn.tilsynKarakterSynkende);
            itemView.setOnClickListener(this);

        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onClick(View v) {
            Toast.makeText(aktiviteten, "Du klikket på: " + navn.getText().toString(), Toast.LENGTH_SHORT).show();
        }// Slutt på onClick
    }// Slutt på holder-klassen
}// Slutt på adapter-klassen

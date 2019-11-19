package com.eksamen.eksamen_h2019_6120;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private String url;
    private EditText sokFelt;
    private Button sokKnapp;
    private RecyclerView recyclerView;
    private TilsynListeAdapter tilsynAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sokFelt = findViewById(R.id.sokFelt);
        sokKnapp = findViewById(R.id.sokKnapp);
        recyclerView = findViewById(R.id.recyclerTilsyn);


        sokKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                url = sokFelt.getText().toString();

                RestController.tilsynRequest(url, MainActivity.this);

                opprettListe();

            }
        });


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
                new AlertDialog.Builder(viewHolder.itemView.getContext())
                        .setMessage("Er du sikker på at du vil fjerne fra listen?")
                        .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RestController.tilsynArrayList.remove(viewHolder.getAdapterPosition());
                                tilsynAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                            }
                        }).setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Viktig slik at det som ble "swipet" settes tilbake til posisjonen sin - ellers blir området tomt!
                        tilsynAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    }
                })
                    .create()
                    .show();

            }
        });

        helper.attachToRecyclerView(recyclerView);
    }

    private void opprettListe() {

        if(RestController.tilsynArrayList!=null) {

            tilsynAdapter = new TilsynListeAdapter(MainActivity.this);
            recyclerView.setAdapter(tilsynAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        }

    }
}

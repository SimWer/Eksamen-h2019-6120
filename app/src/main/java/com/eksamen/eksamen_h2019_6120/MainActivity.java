package com.eksamen.eksamen_h2019_6120;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SoekFragment.OnFragmentInteractionListener, SmilefjesRapport.OnFragmentInteractionListener{
    //ID for å hente ut data sendt fra fragment til fragment

    protected static final String ID = "Eksamen-h2019-6120";
    private SoekFragment soekFragment;
    private SmilefjesRapport smileFragment;

    private Toolbar toppMeny;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toppMeny = findViewById(R.id.meny);

        setSupportActionBar(toppMeny);

        if(savedInstanceState != null) {

        } else {
            soekFragment = SoekFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_holder, soekFragment).addToBackStack(null).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.top_menu, menu);

        final MenuItem soekItem = menu.findItem(R.id.soek_menu);
        final SearchView soekView = (SearchView) soekItem.getActionView();
        soekView.setIconified(false);
        soekView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.instillinger_menu) {
            Intent intent = new Intent(this, Innstillinger.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onTilsynValgt(Tilsyn tilsyn) {
        smileFragment = SmilefjesRapport.newInstance();
        Bundle args = new Bundle();
        args.putSerializable(ID, tilsyn);
        smileFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, smileFragment).addToBackStack(null).commit();

        // For å vise tilbakeknapp bare etter søk og ikke på første fragmentet
        if(smileFragment != null && soekFragment.isVisible()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public void onSoek(RecyclerView recyclerView) {
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
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

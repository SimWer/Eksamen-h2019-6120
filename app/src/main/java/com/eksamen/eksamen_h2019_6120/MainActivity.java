package com.eksamen.eksamen_h2019_6120;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SoekFragment.OnFragmentInteractionListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SoekFragment soekFragment = SoekFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_holder, soekFragment).commit();
        }


    @Override
    public void onTilsynValgt(Tilsyn tilsyn) {

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
                        .setMessage("Er du sikker på at du vil fjerne fra listen?")
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
}

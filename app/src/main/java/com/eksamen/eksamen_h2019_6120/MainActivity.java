package com.eksamen.eksamen_h2019_6120;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
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

    protected static final String ID                = "Eksamen-h2019-6120";


    private static final String KEY_FAVORITTSTED    = "poststed";
    private static final String KEY_FAVORITTNR      = "postnr";
    private static final String KEY_LASTAUTO        = "lastned";

    private static final int MY_REQUEST_LOCATION = 1;

    // Felt for GPS lokalisering
    private LocationManager locationManager;
    private String lokasjonsLeverandoer = LocationManager.GPS_PROVIDER;
    private Location minLokasjon;

    private SoekFragment soekFragment;
    private SmilefjesRapport smileFragment;

    private Toolbar toppMeny;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sett menyen til å være toolbar
        toppMeny = findViewById(R.id.meny);
        setSupportActionBar(toppMeny);


        if(savedInstanceState != null) {

        } else {
            soekFragment = SoekFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_holder, soekFragment).addToBackStack(null).commit();
        }
        // Metode som kalles ved oppstart av aktiviten for å sjekke instillinger

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

        switch (id) {
            case R.id.instillinger_menu:
                Intent intent = new Intent(this, Innstillinger.class);
                startActivity(intent);
                break;
            case R.id.posisjon_menu:
                soekOppPosisjon();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private void soekOppPosisjon() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if(!locationManager.isProviderEnabled(lokasjonsLeverandoer)) {
            Toast.makeText(this, "Aktiver " + lokasjonsLeverandoer + " under Locations i Settings", Toast.LENGTH_SHORT).show();
        } else {

            int tillatelse = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);

            if(tillatelse != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_REQUEST_LOCATION);

            } else {

                minLokasjon = locationManager.getLastKnownLocation(lokasjonsLeverandoer);
                String lengdeGrad = Location.convert(minLokasjon.getLongitude(), Location.FORMAT_DEGREES);
                String breddeGrad = Location.convert(minLokasjon.getLatitude(), Location.FORMAT_DEGREES);
                RestController.adresseRequest(lengdeGrad, breddeGrad, soekFragment.getRecyclerView(), this);
            }
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == MY_REQUEST_LOCATION) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                soekOppPosisjon();
            } else {

            }
        }
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
    public void sjekkInstillinger(RecyclerView recyclerView) {
        //Få instillinger fra appen, og sjekk om brukeren har valgt at det skal oppdataeres automatisk ved oppstart.
        PreferenceManager preferenceManager = new PreferenceManager(this);
        // https://stackoverflow.com/questions/18414267/switchpreference-and-checkboxpreference-in-code
        if(preferenceManager.getSharedPreferences().getBoolean(KEY_LASTAUTO, true)) {

            String poststed = preferenceManager.getSharedPreferences().getString(KEY_FAVORITTSTED, null);
            RestController.tilsynRequest(poststed, this, recyclerView);
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

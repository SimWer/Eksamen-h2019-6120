package com.eksamen.eksamen_h2019_6120;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class MainActivity extends AppCompatActivity implements SoekFragment.OnFragmentInteractionListener, SmilefjesRapport.OnFragmentInteractionListener{
    //ID for å hente ut data sendt fra fragment til fragment
    protected static final String ID                = "Eksamen-h2019-6120";
    protected static final String ARRAY_ID_STATE    = "Eksamen-h2019-6120-Array_State";

    // Stringer som brukes
    private static final String KEY_FAVORITTSTED    = "poststed";
    private static final String KEY_LASTAUTO        = "lastned";

    private static final int MY_REQUEST_LOCATION = 1;

    // Felt for GPS lokalisering
    private LocationManager locationManager;
    private String lokasjonsLeverandoer = LocationManager.GPS_PROVIDER;
    private Location minLokasjon;

    private SoekFragment soekFragment;
    private SmilefjesRapport smileFragment;

    private long tilbakeTrykkTid;

    private Toolbar toppMeny;

    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sett menyen til å være toolbar
        toppMeny = findViewById(R.id.meny);
        setSupportActionBar(toppMeny);

        if(savedInstanceState != null) {
            // Må sjekke om den er i liggende eller stående
            // Dette for at om den er i stående så skal den vise riktig fragment ingenting skjer om den er i liggende
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            } else {
                soekFragment = SoekFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, soekFragment).commit();
            }

        } else {
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            } else {
                soekFragment = SoekFragment.newInstance();
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_holder, soekFragment).commit();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.top_menu, menu);

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
            case android.R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, soekFragment).commit();
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        // Jeg klikket meg ut uheldigvis veldig mange ganger
        // La derfor til en måte å sjekke om brukeren faktisk vil gå ut av appen
        if(soekFragment.isVisible()) {
            if(tilbakeTrykkTid + 2000 > System.currentTimeMillis()) {
                toast.cancel();
                super.onBackPressed();
                return;
            } else {
                toast = Toast.makeText(this, "Trykk tilbake igjen for å gå ut", Toast.LENGTH_SHORT);
                toast.show();
            }
            tilbakeTrykkTid = System.currentTimeMillis();
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            super.onBackPressed();

        }
    }

    /**
     *
     * Søker opp posisjonen til brukeren og setter opp datasettet i RecyclerView'et
     *
     * */
    // Dette er fra leksjonene vi har hatt på skolen, og fra undervisningsmateriell
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

                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    View v = findViewById(R.id.soek_fragmen);
                    RecyclerView view = v.findViewById(R.id.recyclerTilsyn);
                    RestController.adresseRequest(lengdeGrad, breddeGrad, view, this);
                } else {
                    RestController.adresseRequest(lengdeGrad, breddeGrad, soekFragment.getRecyclerView(), this);
                }

            }
        }



    }

    // Dette er fra leksjonene vi har hatt på skolen, og fra undervisningsmateriell
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
        outState.putParcelableArrayList(ARRAY_ID_STATE, RestController.tilsynArrayList);
    }


    /***
     *
     * Metode for å håndtere valg av tilsyn, og legger inn riktig informasjon basert på liggende eller stående format.
     *
     *
     * @param tilsyn Valgt tilsyn fra brukeren
     *
     * */

    @Override
    public void onTilsynValgt(Tilsyn tilsyn) {

        // Sjekker om det er liggende format for å legeg inn data på en annen måte
        // Det er ikke nødvendig å legge til eller replace fragmentene i liggende format
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            View smilView = findViewById(R.id.smil_fragment);

            TextView bedriftNavn = smilView.findViewById(R.id.bedrift_plassholder);
            TextView bedriftAdresse = smilView.findViewById(R.id.adresse_plasshodler);
            TextView dato = smilView.findViewById(R.id.dato_felt);
            ImageView totalkarakter = smilView.findViewById(R.id.total_karakter_bilde);
            ListView kravlisteView = smilView.findViewById(R.id.kravpunktListe);

            bedriftNavn.setText(tilsyn.getNavn());
            bedriftAdresse.setText(tilsyn.getAdrlinje1());
            dato.setText(tilsyn.getDatoMedSkille());
            totalkarakter.setImageResource(tilsyn.getBilde_id());

            RestController.kravpunkterRequest(tilsyn.getTilsynid(), smilView.getContext(), kravlisteView );

        } else {

            smileFragment = SmilefjesRapport.newInstance();
            Bundle args = new Bundle();
            args.putSerializable(ID, tilsyn);
            smileFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, smileFragment).addToBackStack(null).commit();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

    }


    /**
     *
     * Sjekker innstilinger fra brukeren ved oppstart av applikasjonen
     *
     * @param recyclerView RecyclerViewet som skal ha datasettet
     *
     * */
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

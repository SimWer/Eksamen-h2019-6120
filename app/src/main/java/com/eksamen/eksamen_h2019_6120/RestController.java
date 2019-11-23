package com.eksamen.eksamen_h2019_6120;

import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RestController {


    protected static ArrayList<Tilsyn> tilsynArrayList;
    protected static ArrayList<Kravpunkt> kravpunkterArrayList;
    protected static TilsynListeAdapter tilsynAdapter;
    protected static SmilefjesAdapter smilefjesAdapter;
    protected static String postNr;

    private final static String ARRAY_NAVN = "entries";
    private final static String ARRAY_ADRESSER = "adresser";
    private final static String KOL_POSTNR = "postnummer";
    private final static String KOL_POSTSTED = "poststed";




    private static final String ENDPOINT_TILSYN = "http://hotell.difi.no/api/json/mattilsynet/smilefjes/tilsyn?query=";
    private static final String ENDPOINT_KRAVPUNKT = "http://hotell.difi.no/api/json/mattilsynet/smilefjes/kravpunkter?tilsynid=";

    public static void tilsynRequest(String spoerring, final Context context, final RecyclerView view) {

        String url = ENDPOINT_TILSYN + spoerring;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(tilsynArrayList != null) {
                    tilsynArrayList.clear();
                }
                tilsynArrayList = new ArrayList<>();
                JSONArray jsonArray = response.optJSONArray(ARRAY_NAVN);
                for(int i = 0; i < jsonArray.length(); i++)  {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Tilsyn tilsyn = new Tilsyn(jsonObject);
                        tilsynArrayList.add(tilsyn);
                    }catch (JSONException e) {

                    }
                }

                tilsynAdapter = new TilsynListeAdapter(context);
                view.setAdapter(tilsynAdapter);
                view.setLayoutManager(new LinearLayoutManager(context));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void kravpunkterRequest(String tilsynID, final Context context, final ListView listView) {

        String url = ENDPOINT_KRAVPUNKT + tilsynID;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(kravpunkterArrayList != null) {
                    kravpunkterArrayList.clear();
                }
                kravpunkterArrayList = new ArrayList<>();
                JSONArray jsonArray = response.optJSONArray(ARRAY_NAVN);
                for(int i = 0; i < jsonArray.length(); i++)  {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Kravpunkt kravpunkt = new Kravpunkt(jsonObject);
                        kravpunkterArrayList.add(kravpunkt);
                    }catch (JSONException e) {

                    }
                    smilefjesAdapter = new SmilefjesAdapter(context, kravpunkterArrayList);
                    listView.setAdapter(smilefjesAdapter);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }





    public static void adresseRequest(String lat, String lon, final RecyclerView recyclerView, final Context context) {
        String url = "https://ws.geonorge.no/adresser/v1/punktsok?radius=300&lat=" + lon + "&lon=" + lat + "&treffPerSide=10&side=0&asciiKompatibel=true";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray jsonArray = response.optJSONArray(ARRAY_ADRESSER);
                    if(response == null) {

                        Toast.makeText(context, "Det er ingen adresser registrert på din posisjon!", Toast.LENGTH_SHORT).show();

                    }
                    try {
                        JSONObject forsteObject = jsonArray.getJSONObject(0);
                        postNr = forsteObject.optString(KOL_POSTNR);
                        String poststed = forsteObject.optString(KOL_POSTSTED);
                        Toast.makeText(context, "Du er her: " + poststed, Toast.LENGTH_SHORT).show();
                        tilsynRequest(postNr, context, recyclerView);
                    } catch (JSONException e) {

                    }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }
}

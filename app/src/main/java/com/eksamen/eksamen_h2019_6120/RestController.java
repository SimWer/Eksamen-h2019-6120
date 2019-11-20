package com.eksamen.eksamen_h2019_6120;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RestController {
    protected static ArrayList<Tilsyn> tilsynArrayList;
    protected static ArrayList<Tilsyn> kravpunkterArrayList;
    protected static TilsynListeAdapter tilsynAdapter;



    private static final String ENDPOINT_TILSYN = "http://hotell.difi.no/api/json/mattilsynet/smilefjes/tilsyn?name=";
    private static final String ENDPOINT_KRAVPUNKT = "http://hotell.difi.no/api/json/mattilsynet/smilefjes/kravpunkter?query=";

    public static void tilsynRequest(String spoerring, final Context context, final RecyclerView view) {

        String url = ENDPOINT_TILSYN + spoerring;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(tilsynArrayList != null) {
                    tilsynArrayList.clear();
                }
                tilsynArrayList = new ArrayList<>();
                JSONArray jsonArray = response.optJSONArray("entries");
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

    public static void kravpunkterRequest(String spoerring, Context context) {

        String url = ENDPOINT_TILSYN + spoerring;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(tilsynArrayList != null) {
                    tilsynArrayList.clear();
                }
                tilsynArrayList = new ArrayList<>();
                JSONArray jsonArray = response.optJSONArray("entries");
                for(int i = 0; i < jsonArray.length(); i++)  {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Tilsyn tilsyn = new Tilsyn(jsonObject);
                        tilsynArrayList.add(tilsyn);
                    }catch (JSONException e) {

                    }
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

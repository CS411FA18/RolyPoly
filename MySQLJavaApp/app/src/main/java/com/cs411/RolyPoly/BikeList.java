package com.cs411.RolyPoly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BikeList extends AppCompatActivity {
    @BindView(R.id.toolbar_bike_list)
    public Toolbar toolbar;

    private RequestQueue requestQueue;
    private static final String getAllBikeURL = "https://cs411fa18.web.illinois.edu/phpScripts/ReadAll_Bike.php";

    ArrayList<Bike> bikeList;
    ArrayList<JSONObject> jsonResults;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_list);

        final Bundle bundle = this.getIntent().getExtras();
        user = (User) bundle.getSerializable("user");

        ButterKnife.bind(this);
        toolbar.setTitle(getResources().getString(R.string.account));
        setSupportActionBar(toolbar);
        DrawerUtil.getDrawer(this, toolbar, user);

        bikeList = new ArrayList<>();
        jsonResults = new ArrayList<>();

        getAllBikes();

        Button addBike = findViewById(R.id.add_bike_button);
        addBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), addBikeActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    /*
    This function is used to populate the ListView
    Only called inside the onResponse function of the requestQueue, that is so that the listview can be populated when the information from HTTP Reuqest is recieved.
     */
    private void showListView(){
       BikeAdapter bikeAdapter = new BikeAdapter(this, bikeList);

        ListView listView = findViewById(R.id.bike_list);
        listView.setAdapter(bikeAdapter);

    }

    private void getAllBikes() {
        requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getAllBikeURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObj;
                        try {
                            jsonObj = new JSONObject(response);
                            if ("0" == jsonObj.get("success")) {
                                System.out.println("NO SUCCESS");
                            } else {
                                JSONArray jsonArray = jsonObj.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Gson gson = new Gson();

                                    Bike bike = gson.fromJson(jsonObject.toString(), Bike.class);
                                    bikeList.add(bike);
                                }

                                // Populate the ListView when information comes in
                                showListView();
                            }
                        } catch (JSONException e) {
                            System.out.println("JSON EXCEPTION: " + e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("VOLLEY ERROR String: " + error.toString());
                        System.out.println("VOLLEY ERROR Message: " + error.getCause());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("UIN", user.UIN.toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}


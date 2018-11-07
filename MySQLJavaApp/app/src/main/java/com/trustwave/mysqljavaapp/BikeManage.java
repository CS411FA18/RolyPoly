package com.trustwave.mysqljavaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BikeManage extends AppCompatActivity {

    private Button getAll;
    private Button addBike;
    private Button deleteButton;
    private Button updateBikeButton;
    private static final String getAllBikeURL = "https://cs411fa18.web.illinois.edu/phpScripts/ReadAll_Bike.php";

    private Button searchButton;

    private RequestQueue requestQueue;

    ArrayList<Bike> bikeList;

    ArrayList<JSONObject> jsonResults;

    private int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bike_manage);
//        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_manage);

        getAll = findViewById(R.id.getAll);

        updateBikeButton = findViewById(R.id.updateBikeButton);
        addBike = findViewById(R.id.addBike);
        deleteButton = findViewById(R.id.deleteButton);
        searchButton = findViewById(R.id.searchButtonMain);

        jsonResults = new ArrayList<JSONObject>();

        bikeList = new ArrayList<Bike>();

        updateBikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UpdateBikeActivity.class);
                startActivity(intent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchForBikeActivity.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RemoveBikeActivity.class);
                startActivity(intent);
            }
        });

        addBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), addBikeActivity.class);
                startActivity(intent);
            }
        });

        getAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                getAllBikes(getAllBikeURL);
//                printJsonArray();
//                printBikeList();

                if (count == 3) {
                    Intent intent = new Intent(getApplicationContext(), BikeList.class);
                    intent.putParcelableArrayListExtra("BikeList", bikeList);
                    startActivity(intent);
                }
            }
        });
    }


    private void getAllBikes(String URL) {
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonResults.add(jsonArray.getJSONObject(i));
                            }

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Gson gson = new Gson();

                                Bike bike = gson.fromJson(jsonObject.toString(), Bike.class);
                                bikeList.add(bike);
                            }

//                            for(int i = 0; i < jsonArray.length(); i++){
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                System.out.println(jsonObject.toString());
//                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    private void printJsonArray() {
//        int spacesToIndentEachLevel = 2;

        for (int i = 0; i < jsonResults.size(); i++) {
            JSONObject object = jsonResults.get(i);
            System.out.println(object.toString());
        }
    }

    private void printBikeList() {
        for (int i = 0; i < bikeList.size(); i++) {
            System.out.println("Bike Serial Number: " + bikeList.get(i).serialNumber);
        }
    }

}


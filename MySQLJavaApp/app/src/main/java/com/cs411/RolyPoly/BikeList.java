package com.cs411.RolyPoly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

public class BikeList extends AppCompatActivity {

    ArrayList<Bike> bikeList;
    private RequestQueue requestQueue;
    ArrayList<JSONObject> jsonResults;

    Integer onResponse = 0;

    private static final String getAllBikeURL = "https://cs411fa18.web.illinois.edu/phpScripts/ReadAll_Bike.php";

    private Button returnMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_list);

        bikeList = new ArrayList<Bike>();
        jsonResults = new ArrayList<JSONObject>();

        returnMain = findViewById(R.id.return_Main);

        getAllBikes(getAllBikeURL);

        if(onResponse == 0){
            Toast.makeText(this,"Retrieving Information. Please wait...", Toast.LENGTH_SHORT).show();
        }

        returnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
    This function is used to populate the ListView
    Only called inside the onResponse function of the requestQueue, that is so that the listview can be populated when the information from HTTP Reuqest is recieved.
     */
    private void showListView(){
        ArrayAdapter<Bike> arrayAdapter = new ArrayAdapter<Bike>(this, R.layout.activity_bike_list_element, R.id.bikeText, bikeList);

        ListView listView = (ListView) findViewById(R.id.Bike_List);

        listView.setAdapter(arrayAdapter);

        System.out.println("Printing BikeList");
        printBikeList();
        System.out.println("After Printing BikeList");

        return;
    }

    private void printBikeList(){
        for (int i = 0; i < bikeList.size(); i++){
            System.out.println("Bike Serial Number: " + bikeList.get(i).serialNumber);
        }
    }

    /*
    This function sends the HTTP Get request to the database
    Using Volley, it will send the request and  when it recieves a response it will populate the bikeList array and proceed to populate the ListView accordingly
     */
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
//                            System.out.println("Printing JSON Array from inside getAllBikesFunction: ");
//                            printJsonArray();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Gson gson = new Gson();

                                Bike bike = gson.fromJson(jsonObject.toString(), Bike.class);
//                                System.out.println(bike.toString());
                                bikeList.add(bike);
                            }

                            //Populate the ListView when information comes in
                            if(onResponse == 0){
                                onResponse = 1;
                                showListView();
                            }


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

        for (int i = 0; i < jsonResults.size(); i++) {
            JSONObject object = jsonResults.get(i);
            System.out.println(object.toString());
        }
    }
}


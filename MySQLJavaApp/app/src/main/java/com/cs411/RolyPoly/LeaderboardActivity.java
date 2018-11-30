package com.cs411.RolyPoly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LeaderboardActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_leaderboard)
    public Toolbar toolbar;

    private RequestQueue requestQueue;
    private static final String getRankingsURL = "https://cs411fa18.web.illinois.edu/phpScripts/getRankings.php";

    ArrayList<JSONObject> jsonResults;
    ArrayList<RankStanding> rankingList;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String userEmail;

    RankStanding personalRank;

    Integer onResponse = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        ButterKnife.bind(this);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        User user = (User)bundle.getSerializable("user");

        toolbar.setTitle(getResources().getString(R.string.leaderboard));
        setSupportActionBar(toolbar);
        DrawerUtil.getDrawer(this, toolbar, user);

        jsonResults = new ArrayList<>();
        rankingList = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        userEmail = mUser.getEmail();

        personalRank = new RankStanding();

        if(onResponse == 0){
            Toast.makeText(this,"Retrieving Information. Please wait...", Toast.LENGTH_SHORT).show();
        }

        getPersonalRanking();
        getRankings();

    }

    public void getRankings(){
        requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getRankingsURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onResponse = 1;
                        try {
                            JSONObject jsonResponseObject = new JSONObject(response);
                            JSONArray jsonArray = jsonResponseObject.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length() && i < 10; i++){
//                                System.out.println("Rank Number " + String.valueOf(i) + ": " + jsonArray.getJSONObject(i));

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Gson gson = new Gson();

                                RankStanding rankStanding = gson.fromJson(jsonObject.toString(), RankStanding.class);
                                rankingList.add(rankStanding);
                            }

                            showListView();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error: " +  error.getMessage());
                        Log.e("Volley", error.toString());
                        NetworkResponse response = error.networkResponse;
                        if(response != null && response.data != null){
                            String errorString = new String(response.data);
                            Log.i("error:", errorString);
                        }
                    }
                });
        requestQueue.add(stringRequest);
    }
    public void getPersonalRanking(){
        requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getRankingsURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onResponse = 1;
                        try {
                            JSONObject jsonResponseObject = new JSONObject(response);
                            JSONArray jsonArray = jsonResponseObject.getJSONArray("results");

                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            Gson gson = new Gson();

                            RankStanding yourRank = gson.fromJson(jsonObject.toString(), RankStanding.class);
                            personalRank = yourRank;

                            showListView();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error: " +  error.getMessage());
                        Log.e("Volley", error.toString());
                        NetworkResponse response = error.networkResponse;
                        if(response != null && response.data != null){
                            String errorString = new String(response.data);
                            Log.i("error:", errorString);
                        }
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("Email", userEmail);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }



    /*
    This function is used to populate the ListView
    Only called inside the onResponse function of the requestQueue, that is so that the listview can be populated when the information from HTTP Reuqest is recieved.
     */
    private void showListView(){
        RankAdapter rankAdapter = new RankAdapter(this, rankingList);

        ListView listView = findViewById(R.id.leaderboard_list);
        listView.setAdapter(rankAdapter);

//        TextView yourRankTextView = findViewById(R.id.yourRankText);
        //TODO: SET VIEW WITH USER INFORMATION
//        View yourRankView = findViewById(R.id.yourRankView);
//        yourRankTextView.setText(personalRank.toString());
    }

}

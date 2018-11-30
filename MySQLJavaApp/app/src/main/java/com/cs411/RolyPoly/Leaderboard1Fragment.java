package com.cs411.RolyPoly;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Leaderboard1Fragment extends Fragment {

    User user;
    View RootView;

    ArrayList<RankStanding> rankingList;

    RankStanding personalRank;

    private RequestQueue requestQueue;
    private static final String getRankingsURL = "https://cs411fa18.web.illinois.edu/phpScripts/getRankingsWeekly.php";
    private static final String getRankingsByEmailURL = "https://cs411fa18.web.illinois.edu/phpScripts/getRankingByEmailWeekly.php";

    boolean gotPersonalRanking;


    public Leaderboard1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RootView = inflater.inflate(R.layout.fragment_leaderboard1, container, false);

        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        user = (User)bundle.getSerializable("user");

        rankingList = new ArrayList<>();

        gotPersonalRanking = false;

        getPersonalRanking();
        getRankings();

        return RootView;
    }


    public void getRankings(){
        requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getRankingsURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponseObject = new JSONObject(response);
                            JSONArray jsonArray = jsonResponseObject.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length() && i < 10; i++){

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Gson gson = new Gson();

                                RankStanding rankStanding = gson.fromJson(jsonObject.toString(), RankStanding.class);
                                rankingList.add(rankStanding);
                            }

                            if (gotPersonalRanking == true){
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
        requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getRankingsByEmailURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            gotPersonalRanking = true;
                            System.out.println(response);
//                            System.out.println(user.email);
                            JSONObject jsonResponseObject = new JSONObject(response);
                            jsonResponseObject = jsonResponseObject.getJSONObject("data");

                            Gson gson = new Gson();

                            personalRank = gson.fromJson(jsonResponseObject.toString(), RankStanding.class);

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
                params.put("Email", user.email);

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
        RankAdapter rankAdapter = new RankAdapter(getContext(), rankingList);

        ListView listView = RootView.findViewById(R.id.leaderboard_list);
        listView.setAdapter(rankAdapter);

        TextView rankTextView = RootView.findViewById(R.id.leaderboard_rankNumb);
        TextView usernameTextView = RootView.findViewById(R.id.leaderboard_username);
        TextView deptTextView = RootView.findViewById(R.id.leaderboard_dept);
        TextView pingCountTextView = RootView.findViewById(R.id.leaderboard_pings);

        rankTextView.setText(String.valueOf(personalRank.getRankPosition()));
        usernameTextView.setText(user.firstName + " " + user.lastName);
        deptTextView.setText(user.deptName);
        pingCountTextView.setText(String.valueOf(personalRank.getNumPings()));

    }

}

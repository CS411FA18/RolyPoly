package com.cs411.RolyPoly;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shinelw.library.ColorArcProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1Fragment extends Fragment {

    Integer weeklyGoal;
    Float AvgPingPerDay;
    Float AvgPingPerWeek;
    Integer curNumPings;

    TextView goalTextView;
    TextView avgNumPingsDayTextView;
    TextView avgNumPingsWeekTextView;

    User user;

    private RequestQueue requestQueue;
    private static final String getWeeklyGoalURL = "https://cs411fa18.web.illinois.edu/phpScripts/getPingNumber.php";
    private static final String getAvgPingsDayURL = "https://cs411fa18.web.illinois.edu/phpScripts/getAvgPingsPerDay.php";
    private static final String getAvgPingsWeekURL = "https://cs411fa18.web.illinois.edu/phpScripts/getAvgPingsPerWeek.php";

    public ColorArcProgressBar progressBar;


    public Tab1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View RootView = inflater.inflate(R.layout.fragment_tab1, container, false);

        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();

        user = (User)bundle.getSerializable("user");

        goalTextView = RootView.findViewById(R.id.WeeklyGoalTextView);
        goalTextView.setVisibility(View.INVISIBLE);

        avgNumPingsDayTextView = RootView.findViewById(R.id.avg_num_ping_day_text);
        avgNumPingsWeekTextView = RootView.findViewById(R.id.avg_num_ping_week_text);

        progressBar = (ColorArcProgressBar) RootView.findViewById(R.id.tabbed_progress_bar);

        progressBar.setCurrentValues(0);

        getWeeklyGoal();
        getAvgPingsPerDay();
        getAvgPingsPerWeek();

        return RootView;


    }

    public void getWeeklyGoal() {
        requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getWeeklyGoalURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("response: " + response);


                        try {
                            JSONObject jsonObject = new JSONObject(response);
//                            System.out.println("JSONObject" + jsonObject);
                            jsonObject = jsonObject.getJSONObject("data");
//                            System.out.println("WeeklyGoal: " + jsonObject.get("WeeklyGoal"));
//                            System.out.println("NumPings: " + jsonObject.get("NumPings"));

                            //get values from json object
                            weeklyGoal = (Integer) jsonObject.get("WeeklyGoal");
                            curNumPings = Integer.parseInt((String) jsonObject.get("NumPings"));

                            //set values to progress bar
                            progressBar.setCurrentValues(curNumPings);
                            progressBar.setMaxValues(weeklyGoal);

                            //show Weekly Goal text view
                            goalTextView.setText(String.valueOf(weeklyGoal));
                            goalTextView.setVisibility(View.VISIBLE);
//                            System.out.println("WeeklyGoal: " + weeklyGoal);
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
                })
        {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("Email", user.email);
//                params.put("Email", "ot01@illinois.edu");


                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void getAvgPingsPerDay(){
        requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getAvgPingsDayURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("response: " + response);


                        try {
                            JSONObject jsonObject = new JSONObject(response);
//                            System.out.println("JSONObject" + jsonObject);
//                            jsonObject = jsonObject.getJSONObject("data");
//                            System.out.println("WeeklyGoal: " + jsonObject.get("WeeklyGoal"));
//                            System.out.println("NumPings: " + jsonObject.get("NumPings"));

                            //get values from json object
                            AvgPingPerDay = (Float) Float.valueOf((String)jsonObject.get("data"));

                            //show Weekly Goal text view
                            avgNumPingsDayTextView.setText(String.valueOf(AvgPingPerDay));

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
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("Email", user.email);
//                params.put("Email", "ot01@illinois.edu");


                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void getAvgPingsPerWeek(){
            requestQueue = Volley.newRequestQueue(getContext());

            StringRequest stringRequest = new StringRequest(Request.Method.POST, getAvgPingsWeekURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            System.out.println("response: " + response);


                            try {
                                JSONObject jsonObject = new JSONObject(response);
//                            System.out.println("JSONObject" + jsonObject);
//                                jsonObject = jsonObject.getJSONObject("data");
//                            System.out.println("WeeklyGoal: " + jsonObject.get("WeeklyGoal"));
//                            System.out.println("NumPings: " + jsonObject.get("NumPings"));

                                //get values from json object
                                AvgPingPerWeek = (Float) Float.valueOf((String) jsonObject.get("data"));

                                //show Weekly Goal text view
                                avgNumPingsWeekTextView.setText(String.valueOf(AvgPingPerWeek));
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
                    }) {
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<>();
                    params.put("Email", user.email);
//                params.put("Email", "ot01@illinois.edu");


                    return params;
                }
            };
        requestQueue.add(stringRequest);
    }

}

package com.cs411.RolyPoly;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoadDashboard {
    private static final String getUserURL = "https://cs411fa18.web.illinois.edu/phpScripts/getUser.php";

    public static void getUserInfo(final Context appContext, final Activity activity, final String email) {
        System.out.println("GETTING USER INFO");
        RequestQueue requestQueue = Volley.newRequestQueue(appContext);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getUserURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObj;
                        try {
                            jsonObj = new JSONObject(response);
                            if ("0" == jsonObj.get("success")){
                                System.out.println("Invalid email, Try Again!");
                            } else {
                                System.out.println("RESPONSE:");
                                System.out.println(response);
                                JSONObject jsonUser = jsonObj.getJSONObject("data");
                                Gson gson = new Gson();

                                User user = gson.fromJson(jsonUser.toString(), User.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("user", user);

//                                Intent intent = new Intent(appContext, DashboardActivity.class);
                                Intent intent = new Intent(appContext, TabbedDashboardActivity.class);
                                intent.putExtras(bundle);

                                activity.startActivity(intent);
                            }
                        } catch (JSONException e) {
                            System.out.println("Invalid email, Try Again!");
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}

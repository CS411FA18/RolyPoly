package com.trustwave.mysqljavaapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SearchForBikeActivity extends AppCompatActivity {

    private Button searchButton;

    private EditText searchEdit;

    private String searchIDText;

    private TextView searchResultTV;

    private static final String searchBikeURL = "https://cs411fa18.web.illinois.edu/phpScripts/Search_Bike.php";

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_bike);
        searchButton = findViewById(R.id.searchButton);

        searchEdit = findViewById(R.id.searchEdit);

        searchResultTV = findViewById(R.id.searchResultTV);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchIDText = searchEdit.getText().toString();
                searchBike();
            }
        });

    }

    private void searchBike(){
        requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, searchBikeURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObj = new JSONObject();
                        try {
                            jsonObj = new JSONObject(response);
                            if ("0" == jsonObj.get("success")){
                                searchResultTV.setText("Invalid TagID, Try Again!");
                            } else {
                                searchResultTV.setText(Bike.jsonToString(jsonObj));
                            }
                        } catch (JSONException e) {
                            searchResultTV.setText("Invalid TagID, Try Again!");
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
//                System.out.println(searchIDText);
                params.put("TagID", searchIDText);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}

package com.trustwave.mysqljavaapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UpdateBikeActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private Button updateBikeButton;
    private static final String addBikeURL = "https://cs411fa18.web.illinois.edu/phpScripts/Update_Bike.php";

    private EditText editTagId;
    private EditText editDescription;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bike);

        editTagId = findViewById(R.id.TagID);
        editDescription = findViewById(R.id.DescriptionText);

        updateBikeButton = findViewById(R.id.updateButton);
        updateBikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBike();

                Toast toast = Toast.makeText(getApplicationContext(), "Updated Bike!", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    private void updateBike() {
        requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, addBikeURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("response:" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        NetworkResponse response = error.networkResponse;
                        if(response != null && response.data != null){
                            String errorString = new String(response.data);
                            Log.i("error:", errorString);
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                final String tagIdStr = editTagId.getText().toString();
                final String descriptionStr = editDescription.getText().toString();

                Map<String, String> params = new HashMap<>();

                System.out.println(tagIdStr + " " + descriptionStr);
                params.put("TagId", tagIdStr);
                params.put("Description", descriptionStr);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}

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

public class addBikeActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private Button addBikeButton;
    private static final String addBikeURL = "https://cs411fa18.web.illinois.edu/phpScripts/Create_Bike.php";

    private EditText editUIN;
    private EditText editSerialNum;
    private EditText editMake;
    private EditText editModel;
    private EditText editColor;
    private EditText editDescription;
    private EditText editUniqueCharacteristics;

    String UINText;
    String SerialNumberText;
    String MakeText;
    String ModelText;
    String ColorText;
    String DescriptionText;
    String UniqueCharacteristicsText;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bike);

        editUIN = findViewById(R.id.UINText);
        editSerialNum = findViewById(R.id.SerialNumberText);
        editMake = findViewById(R.id.MakeText);
        editModel = findViewById(R.id.ModelText);
        editColor = findViewById(R.id.ColorText);
        editDescription = findViewById(R.id.DescriptionText);
        editUniqueCharacteristics = findViewById(R.id.UniqueCharacteristicsText);

        addBikeButton = findViewById(R.id.addBikeButton);
        addBikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String UINStr = editUIN.getText().toString();
                final String serialNumberStr = editSerialNum.getText().toString();
                final String makeStr = editMake.getText().toString();
                final String modelStr = editModel.getText().toString();
                final String colorStr = editColor.getText().toString();
                final String descriptionStr = editDescription.getText().toString();
                final String uniqueCharacteristicsStr = editUniqueCharacteristics.getText().toString();

                addNewBike(UINStr, serialNumberStr, makeStr, modelStr, colorStr, descriptionStr, uniqueCharacteristicsStr);

                Toast toast = Toast.makeText(getApplicationContext(), "Added New Bike!", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    private void addNewBike(final String UINStr,  final String serialNumberStr, final String makeStr,
                            final String modelStr, final String colorStr, final String descriptionStr,
                            final String uniqueCharacteristicsStr) {
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
                Map<String, String> params = new HashMap<>();
                params.put("UIN", UINStr);
                params.put("SerialNumber", serialNumberStr);
                params.put("Make", makeStr);
                params.put("Model", modelStr);
                params.put("Color", colorStr);
                params.put("Description", descriptionStr);
                params.put("UniqueCharacteristics", uniqueCharacteristicsStr);

//                params.put("UIN", "673500000");
//                params.put("SerialNumber", "12345");
//                params.put("Make", "makeStr");
//                params.put("Model", "modelStr");
//                params.put("Color", "colorStr");
//                params.put("Description", "descriptionStr");
//                params.put("UniqueCharacteristics", "uniqueCharacteristicsStr");

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
package com.trustwave.mysqljavaapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

    private Button addBikeButton;

    private RequestQueue requestQueue;

    private EditText editUIN;
    private EditText editSerialNum;
    private EditText editMake;
    private EditText editModel;
    private EditText editColor;
    private EditText editDescription;
    private EditText editUniqueCharacteristics;

    String UINText;
    String SerialNumText;
    String MakeText;
    String ModelText;
    String ColorText;
    String DescriptionText;
    String UniqueCharacteristicsText;


    private static final String addBikeURL = "https://cs411fa18.web.illinois.edu/phpScripts/Create_Bike.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bike);

        addBikeButton = findViewById(R.id.addBikeButton);

        editUIN = findViewById(R.id.UINText);
        editSerialNum = findViewById(R.id.SerialNumberText);
        editMake = findViewById(R.id.MakeText);
        editModel = findViewById(R.id.ModelText);
        editColor = findViewById(R.id.ColorText);
        editDescription = findViewById(R.id.DescriptionText);
        editUniqueCharacteristics = findViewById(R.id.UniqueCharacteristicsText);

        addBikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UINText = editUIN.getText().toString();
                SerialNumText = editSerialNum.getText().toString();
                MakeText = editMake.getText().toString();
                ModelText = editModel.getText().toString();
                ColorText = editColor.getText().toString();
                DescriptionText = editDescription.getText().toString();
                UniqueCharacteristicsText = editUniqueCharacteristics.getText().toString();

                System.out.println(UINText);
                System.out.println(SerialNumText);
                System.out.println(MakeText);
                System.out.println(ModelText);
                System.out.println(ColorText);
                System.out.println(DescriptionText);
                System.out.println(UniqueCharacteristicsText);


                addNewBike(addBikeURL);
                Toast toast = Toast.makeText(getApplicationContext(), "Added New Bike!", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    private void addNewBike(String URL) {
        requestQueue = Volley.newRequestQueue(this);

        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, URL,
            new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//              JSONObject jsonObject = new JSONObject(response);
//              Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Response: " + response, Toast.LENGTH_SHORT).show();
                Log.i("My success", "" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.i("Error: ", error.getMessage());
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("My error", "" + error);
                NetworkResponse response = error.networkResponse;
                String errorMsg = "";
                if(response != null && response.data != null){
                    String errorString = new String(response.data);
                    Log.i("log error", errorString);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();

//                map.put("Content-Type", "application/json; charset=utf-8");

//                map.put("UIN", UINText);
//                map.put("SerialNumber", SerialNumText);
//                map.put("Make", MakeText);
//                map.put("Model", ModelText);
//                map.put("Color", ColorText);
//                map.put("Description", DescriptionText);
//                map.put("UniqueCharacteristics", UniqueCharacteristicsText);

                map.put("UIN", "673500000");
                map.put("SerialNumber", "SerialNumText");
                map.put("Make", "MakeText");
                map.put("Model", "ModelText");
                map.put("Color", "ColorText");
                map.put("Description", "DescriptionText");
                map.put("UniqueCharacteristics", "UniqueCharacteristicsText");

                Log.i("sending ", map.toString());

                return map;
            }
        };

//        requestQueue.s(new DefaultRetryPolicy(10000, 1, 1.0f));
        requestQueue.add(jsonObjectRequest);
    }
}

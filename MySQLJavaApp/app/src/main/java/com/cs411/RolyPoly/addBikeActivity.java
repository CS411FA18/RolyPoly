package com.cs411.RolyPoly;

import android.content.Intent;
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

    private static final String addBikeURL = "https://cs411fa18.web.illinois.edu/phpScripts/Create_Bike.php";

    RequestQueue requestQueue;
    User user;
    Bundle bundle;

    private Button addBikeButton;
    private EditText editSerialNum;
    private EditText editMake;
    private EditText editModel;
    private EditText editColor;
    private EditText editDescription;
    private EditText editUniqueCharacteristics;

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

        bundle = this.getIntent().getExtras();
        user = (User) bundle.getSerializable("user");

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
                SerialNumberText = editSerialNum.getText().toString();
                MakeText = editMake.getText().toString();
                ModelText = editModel.getText().toString();
                ColorText = editColor.getText().toString();
                DescriptionText = editDescription.getText().toString();
                UniqueCharacteristicsText = editUniqueCharacteristics.getText().toString();

                addNewBike();
                Toast toast = Toast.makeText(getApplicationContext(), "Added New Bike!", Toast.LENGTH_LONG);
                toast.show();
                finish();
                Intent intent = new Intent(getApplicationContext(), BikeList.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.putExtras(bundle);
        setResult(RESULT_OK, data);
        super.onBackPressed();
    }

    private void addNewBike() {
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
                params.put("UIN", user.UIN.toString());
                params.put("SerialNumber", SerialNumberText);
                params.put("Make", MakeText);
                params.put("Model", ModelText);
                params.put("Color", ColorText);
                params.put("Description", DescriptionText);
                params.put("UniqueCharacteristics", UniqueCharacteristicsText);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
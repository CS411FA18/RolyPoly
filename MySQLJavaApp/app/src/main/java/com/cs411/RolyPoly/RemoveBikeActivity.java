package com.cs411.RolyPoly;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RemoveBikeActivity extends AppCompatActivity {

    private Button deleteButton;

    private EditText EditTagID;
    private String TagIDText;
    private RequestQueue requestQueue;

    private static final String deleteBikeURL = "https://cs411fa18.web.illinois.edu/phpScripts/Delete_Bike.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_bike);

        EditTagID = findViewById(R.id.TagID);

        deleteButton = findViewById(R.id.deleteButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TagIDText = EditTagID.getText().toString();
                deleteBike();

            }
        });
    }

    private void deleteBike() {
        requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, deleteBikeURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("response:" + response);

                        Toast toast2 = Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG);
                        toast2.show();

                        if(response == "success" + ":0," + "message" + ":" + "Bike not found") {
                            Toast toast = Toast.makeText(getApplicationContext(), "Please Try Again with a Valid TagID", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        else if(response == "success" + ":1," + "message" + ":" + "Bike deleted successfully"){
                            Toast toast = Toast.makeText(getApplicationContext(), "Bike Deleted Successfully!", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                System.out.println(TagIDText);
                params.put("TagID", TagIDText);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RemoveBikeActivity extends AppCompatActivity {
    private static final String deleteBikeURL = "https://cs411fa18.web.illinois.edu/phpScripts/Delete_Bike.php";

    private Button deleteButton;
    private EditText EditTagID;

    private String TagIDText;

    private RequestQueue requestQueue;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_bike);

        Bundle bundle = this.getIntent().getExtras();
        user = (User) bundle.getSerializable("user");

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
                        JSONObject jsonObj;
                        try {
                            jsonObj = new JSONObject(response);

                            if(jsonObj.get("success").equals("0")){
                                Toast.makeText(getApplicationContext(), "Invalid TagID. Try Again!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Bike Deleted Successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }  catch (JSONException e) {
                            e.printStackTrace();
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
                params.put("TagID", TagIDText);
                params.put("UIN", user.UIN.toString());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}

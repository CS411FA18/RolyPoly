package com.trustwave.mysqljavaapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class addBikeActivity extends AppCompatActivity {

    private Button addBikeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bike);

        addBikeButton = findViewById(R.id.addBikeButton);

        addBikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Added New Bike!", Toast.LENGTH_LONG);
                toast.show();
            }
        });


    }
}

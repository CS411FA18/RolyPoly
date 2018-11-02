package com.trustwave.mysqljavaapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RemoveBikeActivity extends AppCompatActivity {

    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_bike);

        deleteButton = findViewById(R.id.deleteButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Deleted the Bike!", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}

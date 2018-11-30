package com.cs411.RolyPoly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if(mUser == null){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        else {
            LoadDashboard.getUserInfo(getApplicationContext(), MainActivity.this, mUser.getEmail());
        }
    }
}

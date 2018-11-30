package com.cs411.RolyPoly;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static int RC_SIGN_IN = 123;
    static Context mContext;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if(mFirebaseUser == null){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        else {
            String mEmailAddress = mFirebaseUser.getEmail();
//            startActivity(new Intent(this, DashboardActivity.class));
            startActivity(new Intent(this, TabbedDashboardActivity.class));
        }

    }

    private void instantiateUser(){
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
    }

    private void refreshUser(){
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
    }

    private void loginUser(){
        if(!isUserSignedIn()) {
            Toast.makeText(this, "Login menu selected", Toast.LENGTH_LONG).show();
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                    .setAvailableProviders(Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build()))
                    .setTheme(R.style.AuthUITheme)
                    .setLogo(R.drawable.ic_account_circle_black_24dp)
                    .setIsSmartLockEnabled(true)
                    .build(), RC_SIGN_IN);
        }
        else{
            Toast.makeText(this, "User is already Signed In!", Toast.LENGTH_SHORT).show();
        }
    }

    private void logOutUser(){
        mFirebaseAuth.signOut();

        Intent s = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(s);
    }

    private boolean isUserSignedIn(){
        if(mFirebaseUser == null)
            return false;
        else
            return true;
    }

    public static void launch(String message) {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra("message", message);
        mContext.startActivity(intent);
    }
}

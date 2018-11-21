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

    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        try {
//            Bundle bundle = getIntent().getExtras();
//            if(bundle != null){
//                Integer text = bundle.getInt("signOut");
//                if(text == 1){
//                    Toast.makeText(getApplicationContext(), "Trying to Sign Out!", Toast.LENGTH_LONG).show();
//                    logOutUser();
//                }
//            }
//            else{
//                instantiateUser();
//                loginUser();
//                refreshUser();
//
//                if(isUserSignedIn()){
//                    Intent s = new Intent(getApplicationContext(), DashboardActivity.class);
//                    startActivity(s);
//                }
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//            Toast.makeText(getApplicationContext(), "Exception", Toast.LENGTH_LONG).show();
//        }

        instantiateUser();

//        logOutUser();
//
//        if(isUserSignedIn()){
//            logOutUser();
//        }
        loginUser();
//        refreshUser();
//        if(!isUserSignedIn()) {
//            loginUser();
//        }
//
//        while (!isUserSignedIn());
//
//        if(isUserSignedIn()){
            Intent s = new Intent(getApplicationContext(), DashboardActivity.class);
            startActivity(s);
//        }

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

        Intent s = new Intent(getApplicationContext(), LogInSignUpButtonAcitivty.class);
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

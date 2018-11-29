package com.cs411.RolyPoly;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private View mProgressView;
    private View mSignUpFormView;
    private EditText password;
    private EditText email;
    private EditText UIN;
    private EditText WeeklyGoal;
    private EditText FirstName;
    private EditText LastName;
    private Button button_register;
    private RadioGroup radioGroup;
    private Spinner dropdownList;

    private String emailText;
    private String passwordText;
    private String UINText;
    private String WeeklyGoalText;
    private String FirstNameText;
    private String LastNameText;
    private String PersonType;
    private String CollegeText;

    private Integer addedNewUser = 0;

    private RequestQueue requestQueue;
    private static final String addUserURL = "https://cs411fa18.web.illinois.edu/phpScripts/Create_User.php";

    String[] dropdownListItems = new String[] {
            "College of Agricultural, Consumer and Environmental Sciences",
            "College of Applied Health Sciences",
            "College of Education",
            "College of Engineering",
            "College of Fine and Applied Arts",
            "Division of General Studies",
            "Gies College of Business",
            "Graduate College",
            "School of Labor and Employment Relations",
            "College of Law",
            "College of Liberal Arts and Sciences",
            "School of Information Sciences",
            "College of Media",
            "Carle Illinois College of Medicine",
            "School of Social Work",
            "College of Veterinary Medicine"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        email = (EditText) findViewById(R.id.emailSignUp);
        password = (EditText) findViewById(R.id.passwordSignUp);
        UIN = (EditText) findViewById(R.id.UINSignUp);
        WeeklyGoal = (EditText) findViewById(R.id.WeeklyGoalSignUp);
        FirstName = (EditText) findViewById(R.id.FirstNameSignUp);
        LastName = (EditText) findViewById(R.id.LastNameSignUp);
        button_register = (Button) findViewById(R.id.email_register_button);

        radioGroup = (RadioGroup) findViewById(R.id.RadioGroupSignUp);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.StudentOption) {
                    Toast.makeText(SignUpActivity.this, "Student Option Selected", Toast.LENGTH_SHORT).show();
                    PersonType = "Student";
                } else if (checkedId == R.id.FacultyOption) {
                    Toast.makeText(SignUpActivity.this, "Faculty Option Selected", Toast.LENGTH_SHORT).show();
                    PersonType = "Faculty";
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mProgressView = findViewById(R.id.sign_up_progress);
        mSignUpFormView = findViewById(R.id.sign_up_form);

        addedNewUser = 0;

        dropdownList = (Spinner) findViewById(R.id.CollegeSpinnerList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, dropdownListItems);
        dropdownList.setAdapter(adapter);
        dropdownList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CollegeText = dropdownListItems[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(SignUpActivity.this, "Please Select a College", Toast.LENGTH_SHORT).show();
            }
        });

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == button_register) {
                    RegisterUser();
                }
            }
        });
    }

    public void RegisterUser() {
        emailText = email.getText().toString().trim();
        passwordText = password.getText().toString().trim();
        UINText = UIN.getText().toString().trim();
        WeeklyGoalText = WeeklyGoal.getText().toString().trim();
        FirstNameText = FirstName.getText().toString().trim();
        LastNameText = LastName.getText().toString().trim();

        if (TextUtils.isEmpty(emailText)) {
            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            email.setError(getString(R.string.error_field_required));
            return;
        }
        if (TextUtils.isEmpty(passwordText)) {
            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            email.setError(getString(R.string.error_field_required));
            return;
        }
        if (TextUtils.isEmpty(UINText)) {
            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            UIN.setError(getString(R.string.error_field_required));
            return;
        }
        if (TextUtils.isEmpty(FirstNameText)) {
            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            FirstName.setError(getString(R.string.error_field_required));
            return;
        }
        if (TextUtils.isEmpty(LastNameText)) {
            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            LastName.setError(getString(R.string.error_field_required));
            return;
        }
        if (TextUtils.isEmpty(WeeklyGoalText)) {
            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            WeeklyGoal.setError(getString(R.string.error_field_required));
            return;
        }
        if (UINText != null && UINText.length() != 9) {
            Toast.makeText(this, "UIN is Invalid", Toast.LENGTH_SHORT).show();
            UIN.setError("UIN is Invalid");
            return;
        }

        mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            if (task.isSuccessful()) {
                                //Create a User in the database
                                //For now we move onto the dashboard
                                User user = new User(Integer.parseInt(UINText), FirstNameText, LastNameText, PersonType, CollegeText, emailText, Integer.parseInt(WeeklyGoalText));
                                addNewUser(user);

//                                while (addedNewUser == 0){
////                                    showProgress(true);
//                                }

//                                if(addedNewUser == -1){
//                                    Toast.makeText(SignUpActivity.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
//                                    return;
//                                }

                                System.out.println(user);
                            } else {
                                Toast.makeText(SignUpActivity.this, "Couldn't Register, Try Again", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void addNewUser(final User user) {
        requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, addUserURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("response: " + response);
                        addedNewUser = 1;

                        Toast.makeText(SignUpActivity.this, "Registration is Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        addedNewUser = -1;
                        Log.e("Volley", error.toString());
//                        NetworkResponse response = error.networkResponse;
//                        if (response != null && response.data != null) {
//                            String errorString = new String(response.data);
//                            Log.i("error:", errorString);
//                        }
                        error.printStackTrace();
                        //Delete the user
                        AuthUI.getInstance()
                                .delete(getApplicationContext())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
//                                        Toast.makeText(SignUpActivity.this, "Adding New User Failed", Toast.LENGTH_SHORT).show();
//                                        finish();
//                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                    }
                                });
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("UIN", String.valueOf(user.UIN));
                params.put("FirstName", user.firstName);
                params.put("LastName", user.lastName);
                params.put("UserType", user.userType);
                params.put("DeptName", user.deptName);
                params.put("Address1", "");
                params.put("City", "");
                params.put("State", "");
                params.put("Zip", "");
                params.put("Email", user.email);
                params.put("PhoneNumber", "");
                params.put("WeeklyGoal", String.valueOf(user.weeklyGoal));

                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                if (response.headers == null) {
                    // cant just set a new empty map because the member is final.
                    response = new NetworkResponse(
                            response.statusCode,
                            response.data,
                            Collections.<String, String>emptyMap(), // this is the important line, set an empty but non-null map.
                            response.notModified,
                            response.networkTimeMs);
                }
                return super.parseNetworkResponse(response);
            }

        };
        requestQueue.add(stringRequest);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mSignUpFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}

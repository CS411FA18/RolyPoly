package com.cs411.RolyPoly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EditAccount extends AppCompatActivity {
    private RequestQueue requestQueue;
    private static final String updateUserURL = "https://cs411fa18.web.illinois.edu/phpScripts/updateUser.php";

    private EditText firstName;
    private EditText lastName;
    private RadioGroup userType;
    private Spinner deptName;
    private EditText address1;
    private EditText city;
    private EditText state;
    private EditText zip;
    private EditText phoneNumber;
    private EditText weeklyGoal;
    private Button updateButton;

    User user;
    String firstNameText;
    String lastNameText;
    String userTypeText;
    String deptNameText;
    String address1Text;
    String cityText;
    String stateText;
    String zipText;
    String phoneNumberText;
    String weeklyGoalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_account);

        Bundle bundle = this.getIntent().getExtras();
        user = (User) bundle.getSerializable("user");

        firstName = findViewById(R.id.firstname_et);
        firstName.setText(user.firstName);

        lastName = findViewById(R.id.lastname_et);
        lastName.setText(user.lastName);

        userType = findViewById(R.id.usertype_et);
        if (user.userType.equals("Student")) {
            userType.check(R.id.StudentButton);
            userTypeText = "Student";
        } else {
            userType.check(R.id.FacultyButton);
            userTypeText = "Faculty";
        }
        userType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.StudentButton) {
                    userTypeText = "Student";
                } else {
                    userTypeText = "Faculty";
                }
            }
        });

        deptName = findViewById(R.id.deptname_et);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, Common.dropdownListItems);
        deptName.setAdapter(adapter);
        deptName.setSelection(Common.dropdownListItems.indexOf(user.deptName));
        deptName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                deptNameText = Common.dropdownListItems.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(EditAccount.this, "Please Select a College", Toast.LENGTH_SHORT).show();
            }
        });

        address1 = findViewById(R.id.address1_et);
        address1.setText(user.address);

        city = findViewById(R.id.city_et);
        city.setText(user.city);

        state = findViewById(R.id.state_et);
        state.setText(user.state);

        zip = findViewById(R.id.zip_et);
        zip.setText(user.zip.toString());

        phoneNumber = findViewById(R.id.phone_number_et);
        phoneNumber.setText(user.phoneNumber);

        weeklyGoal = findViewById(R.id.weekly_goal_et);
        weeklyGoal.setText(user.weeklyGoal.toString());

        updateButton = findViewById(R.id.register_account_update);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstNameText = firstName.getText().toString().trim();
                lastNameText = lastName.getText().toString().trim();
                address1Text = address1.getText().toString().trim();
                cityText = city.getText().toString().trim();
                stateText = state.getText().toString().trim();
                zipText = zip.getText().toString().trim();
                phoneNumberText = phoneNumber.getText().toString().trim();
                weeklyGoalText = weeklyGoal.getText().toString().trim();

                if (v == updateButton) {
                    updateUser();
                }
            }
        });
    }

    public void updateUser() {
        if (TextUtils.isEmpty(firstNameText)) {
            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            firstName.setError(getString(R.string.error_field_required));
            return;
        }
        if (TextUtils.isEmpty(lastNameText)) {
            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            lastName.setError(getString(R.string.error_field_required));
            return;
        }
        if (TextUtils.isEmpty(weeklyGoalText)) {
            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            weeklyGoal.setError(getString(R.string.error_field_required));
            return;
        }

        if (user != null) {
            user.firstName = firstNameText;
            user.lastName = lastNameText;
            user.userType = userTypeText;
            user.deptName = deptNameText;
            user.address = address1Text;
            user.city = cityText;
            user.state = stateText;
            user.zip = Integer.parseInt(zipText);
            user.weeklyGoal = Integer.parseInt(weeklyGoalText);
            System.out.println("NEW USER:" + user);

            updateUserOnDB();

        } else {
            System.out.println("NO USER FOUND");
        }

    }

    public void updateUserOnDB() {
        requestQueue = Volley.newRequestQueue(this);
//        System.out.println("VOLLEY");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, updateUserURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("response: " + response);

                        Toast.makeText(EditAccount.this, "Update Successful", Toast.LENGTH_SHORT).show();

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user", user);

                        Intent intent = new Intent(getApplicationContext(), TabbedDashboardActivity.class);
                        intent.putExtras(bundle);

                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        error.printStackTrace();
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
                params.put("Address1", user.address);
                params.put("City", user.city);
                params.put("State", user.state);
                params.put("Zip", user.zip.toString());
                params.put("PhoneNumber", user.phoneNumber);
                params.put("WeeklyGoal", user.weeklyGoal.toString());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}

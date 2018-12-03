package com.cs411.RolyPoly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.shinelw.library.ColorArcProgressBar;

import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_dashboard)
    public Toolbar toolbar;

    Integer weeklyGoal;
    Integer curNumPings;

    TextView goalTextView;

    private RequestQueue requestQueue;
    private static final String getWeeklyGoalURL = "https://cs411fa18.web.illinois.edu/phpScripts/getPingNumber.php";

    //ProgressBar imported from open source library
    public ColorArcProgressBar progressBar;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("DASHBOARD ACTIVITY");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        user = (User)bundle.getSerializable("user");

        ButterKnife.bind(this);

        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);

        goalTextView = findViewById(R.id.WeeklyGoalTextView);
        goalTextView.setVisibility(View.INVISIBLE);

        DrawerUtil.getDrawer(this, toolbar, user);

        getWeeklyGoal();

        // SET THIS TO THE USERS CURRENT NUMBER OF PINGS
        // SET THE MAX VALUE TO USERS WEEKLY GOAL
        progressBar = (ColorArcProgressBar) findViewById(R.id.arcProgressbar1);

        progressBar.setCurrentValues(0);

        MaterialCalendarView materialCalendarView = findViewById(R.id.calendarView);

        final LocalDate localDate = LocalDate.now();
        materialCalendarView.setCurrentDate(localDate);

        Collection<CalendarDay> collection= new HashSet<>();
        collection.add(CalendarDay.from(LocalDate.of(2018, 11, 25)));

        EventDecorator eventDecorator = new EventDecorator(this, getResources().getColor(R.color.primary), collection);
        materialCalendarView.addDecorator(eventDecorator);
    }

    public void getWeeklyGoal() {
        requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getWeeklyGoalURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            System.out.println("response: " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            jsonObject = jsonObject.getJSONObject("data");

                            //get values from json object
                            weeklyGoal = user.weeklyGoal;
                            curNumPings = Integer.parseInt((String) jsonObject.get("NumPings"));

                            //set values to progress bar
                            progressBar.setCurrentValues(curNumPings);
                            progressBar.setMaxValues(weeklyGoal);

                            //show Weekly Goal text view
                            goalTextView.setText("Your Weekly Goal: " + String.valueOf(weeklyGoal));
                            goalTextView.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error: " +  error.getMessage());
                        Log.e("Volley", error.toString());
                        NetworkResponse response = error.networkResponse;
                        if(response != null && response.data != null){
                            String errorString = new String(response.data);
                            Log.i("error:", errorString);
                        }
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("Email", user.email);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}

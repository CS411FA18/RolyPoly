package com.cs411.RolyPoly;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab2Fragment extends Fragment {

    User user;

    HashSet<CalendarDay> calendarDayHashSet;
    MaterialCalendarView materialCalendarView;
    Collection<CalendarDay> collection;

    EventDecorator eventDecorator;

    private RequestQueue requestQueue;
    private static final String getMonthlyPingInfoURL = "https://cs411fa18.web.illinois.edu/phpScripts/getMonthlyPingInfo.php";


    public Tab2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View RootView = inflater.inflate(R.layout.fragment_tab2, container, false);

        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();

        user = (User)bundle.getSerializable("user");

        calendarDayHashSet = new HashSet<>();

        materialCalendarView = RootView.findViewById(R.id.calendarView);
//        materialCalendarView.setCurrentDate(CalendarDay.from(LocalDate.of(2018, 3, 1)));
        getMonthlyPingInfo(materialCalendarView.getCurrentDate());

        materialCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView materialCalendarView, CalendarDay calendarDay) {
                materialCalendarView.setCurrentDate(calendarDay);
                if (!calendarDayHashSet.contains(calendarDay)){
                    calendarDayHashSet.add(calendarDay);
//                    System.out.println("Month: " + calendarDay.getMonth());
//                    System.out.println("Year: " + calendarDay.getYear());
                    getMonthlyPingInfo(calendarDay);
                }
                CalendarDay adjMonth;
                //get the previous month
                if (calendarDay.getMonth() == 1){
                    adjMonth = CalendarDay.from(calendarDay.getYear() - 1, 12, calendarDay.getDay());
                }
                else {
                    adjMonth = CalendarDay.from(calendarDay.getYear(), calendarDay.getMonth() - 1, calendarDay.getDay());
                }
                getMonthlyPingInfo(adjMonth);

                //get the next month
                if(calendarDay.getMonth() == 12){
                    adjMonth = CalendarDay.from(calendarDay.getYear() + 1, 1, calendarDay.getDay());
                }
                else {
                    adjMonth = CalendarDay.from(calendarDay.getYear(), calendarDay.getMonth() + 1, calendarDay.getDay());
                }
                getMonthlyPingInfo(adjMonth);

            }
        });

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
                materialCalendarView.setCurrentDate(calendarDay);
            }
        });

//        final LocalDate localDate = LocalDate.now();
//        materialCalendarView.setCurrentDate(localDate);
        materialCalendarView.setCurrentDate(CalendarDay.from(LocalDate.of(2018, 3, 1)));

        collection = new HashSet<>();
//        collection.add(CalendarDay.from(LocalDate.of(2018, 11, 25)));
//        collection.add(CalendarDay.from(LocalDate.of(2018, 11, 26)));
//        collection.add(CalendarDay.from(LocalDate.of(2018, 11, 27)));

        eventDecorator = new EventDecorator(getActivity(), getResources().getColor(R.color.primary), collection);
        materialCalendarView.addDecorator(eventDecorator);
//        System.out.println("Current Date: " + materialCalendarView.getCurrentDate());

        return RootView;
    }

    public void getMonthlyPingInfo(final CalendarDay calendarDay) {
        final int month = calendarDay.getMonth();
        final int year = calendarDay.getYear();

        requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getMonthlyPingInfoURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        System.out.println("response: " + response);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++){
//                                System.out.println("Date: " + jsonArray.getJSONObject(i).get("Date").toString());
//                                System.out.println("PingCount: " + jsonArray.getJSONObject(i).get("PingCount"));
//                                System.out.println("Day with Ping: " + getCalendarDay(jsonArray.getJSONObject(i).get("Date").toString()));
                                CalendarDay calendarDay1 = getCalendarDay(jsonArray.getJSONObject(i).get("Date").toString());
                                collection.add(calendarDay1);
                            }

//                            Iterator<CalendarDay> i = collection.iterator();
//                            while (i.hasNext()){
//                                System.out.println("Day in Collection: " + i.next());
//                            }
                            eventDecorator.updateCollection(collection);
                            materialCalendarView.addDecorator(eventDecorator);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                        showMonthlyPingInfo(calendarDay);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<>();
                    params.put("Email", user.email);
                    params.put("Month", String.valueOf(month));
                    params.put("Year", String.valueOf(year));
//                    params.put("Email", "sw00@illinois.edu");
//                    params.put("Month", String.valueOf(3));
//                    params.put("Year", String.valueOf(2018));


                    return params;
                }
            };
        requestQueue.add(stringRequest);

    }

    public CalendarDay getCalendarDay(String date){
        Integer year = Integer.parseInt(date.substring(0, 4));
        Integer month = Integer.parseInt(date.substring(5, 7));
        Integer day = Integer.parseInt(date.substring(8, 10));

        return CalendarDay.from(LocalDate.of(year, month, day));

    }

}

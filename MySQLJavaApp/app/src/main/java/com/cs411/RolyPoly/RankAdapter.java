package com.cs411.RolyPoly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RankAdapter extends ArrayAdapter<RankStanding> {
    public RankAdapter(Context context, ArrayList<RankStanding> ranks){
        super(context, 0, ranks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        RankStanding rankUser = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_rank_list_element, parent, false);
        }
        // Lookup view for data population
        TextView userRank = (TextView) convertView.findViewById(R.id.leaderboard_rankNumb);
        TextView userName = (TextView) convertView.findViewById(R.id.leaderboard_username);
        TextView userDept = (TextView) convertView.findViewById(R.id.leaderboard_dept);
        TextView userPingCount = (TextView) convertView.findViewById(R.id.leaderboard_pings);

        userRank.setText(String.valueOf(rankUser.getRankPosition()));
        userName.setText(rankUser.getFirstName() + " " + rankUser.getLastName());
        userDept.setText(rankUser.getDeptName());
        userPingCount.setText(String.valueOf(rankUser.getNumPings()));


        return convertView;
    }
}

package com.cs411.RolyPoly;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class RankStanding {

    @SerializedName("Rank")
    private Integer rankPosition;
    @SerializedName("FirstName")
    private String firstName;
    @SerializedName("LastName")
    private String lastName;
    @SerializedName("DeptName")
    private String deptName;
    @SerializedName("This Week's Ping Count")
    private Integer numPings;

    public RankStanding(Integer rankPosition, String firstName, String lastName, String deptName, Integer numPings) {
        this.rankPosition = rankPosition;
        this.firstName = firstName;
        this.lastName = lastName;
        this.deptName = deptName;
        this.numPings = numPings;
    }

    public RankStanding() {
    }

    public Integer getRankPosition() {
        return rankPosition;
    }

    public void setRankPosition(Integer rankPosition) {
        this.rankPosition = rankPosition;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getNumPings() {
        return numPings;
    }

    public void setNumPings(Integer numPings) {
        this.numPings = numPings;
    }

    @Override
    public String toString() {
        return "RankStanding{" +
                "rankPosition=" + rankPosition +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", deptName='" + deptName + '\'' +
                ", numPings=" + numPings +
                '}';
    }

    public static String jsonToString(JSONObject jsonObject) throws JSONException {
        jsonObject = jsonObject.getJSONObject("data");
        return
                "Rank = " + jsonObject.get("Rank") + '\n' +
                        "FirstName = " + jsonObject.get("FirstName") + '\n' +
                        "LastName = " + jsonObject.get("LastName") + '\n' +
                        "DeptName = " + jsonObject.get("DeptName") + '\n' +
                        "NumPings = " + jsonObject.get("NumPings") + '\n';
    }
}

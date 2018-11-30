package com.cs411.RolyPoly;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("UIN")
    Integer UIN;
    @SerializedName("FirstName")
    String firstName;
    @SerializedName("LastName")
    String lastName;
    @SerializedName("UserType")
    String userType;
    @SerializedName("DeptName")
    String deptName;
    @SerializedName("Address1")
    String address;
    @SerializedName("City")
    String city;
    @SerializedName("State")
    String state;
    @SerializedName("Zip")
    Integer zip;
    @SerializedName("Email")
    String email;
    @SerializedName("PhoneNumber")
    String phoneNumber;
    @SerializedName("WeeklyGoal")
    Integer weeklyGoal;

    public User(Integer UIN, String firstName, String lastName, String userType, String deptName, String email, Integer weeklyGoal) {
        this.UIN = UIN;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        this.deptName = deptName;
        this.email = email;
        this.weeklyGoal = weeklyGoal;
    }

    public User(Integer UIN, String firstName, String lastName, String userType, String deptName, String address, String city, String state, Integer zip, String email, String phoneNumber, Integer weeklyGoal) {
        this.UIN = UIN;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        this.deptName = deptName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.weeklyGoal = weeklyGoal;
    }

    @Override
    public String toString() {
        return "User{" +
                "UIN=" + UIN +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userType='" + userType + '\'' +
                ", deptName='" + deptName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", weeklyGoal=" + weeklyGoal +
                '}';
    }
}

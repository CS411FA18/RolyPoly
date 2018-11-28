package com.cs411.RolyPoly;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class Node {

    @SerializedName("NodeID")
    Integer NodeID;
    @SerializedName("XCoord")
    double XCoord;
    @SerializedName("YCoord")
    double YCoord;
    @SerializedName("Name")
    String Name;

    public Node() {
    }

    public Node(Integer nodeID, double XCoord, double YCoord, String name) {
        NodeID = nodeID;
        this.XCoord = XCoord;
        this.YCoord = YCoord;
        Name = name;
    }

    public Integer getNodeID() {
        return NodeID;
    }

    public void setNodeID(Integer nodeID) {
        NodeID = nodeID;
    }

    public double getXCoord() {
        return XCoord;
    }

    public void setXCoord(double XCoord) {
        this.XCoord = XCoord;
    }

    public double getYCoord() {
        return YCoord;
    }

    public void setYCoord(double YCoord) {
        this.YCoord = YCoord;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return
                "NodeID = " + NodeID + '\n' +
                "XCoord = " + XCoord + '\n' +
                "YCoord = " + YCoord + '\n' +
                "Name = '" + Name + '\n'
                ;
    }

    public static String jsonToString(JSONObject jsonObject) throws JSONException {
        jsonObject = jsonObject.getJSONObject("data");
        return
                "NodeID = " + jsonObject.get("NodeID") + '\n' +
                        "Xcoord = " + jsonObject.get("XCoord") + '\n' +
                        "YCoord = " + jsonObject.get("YCoord") + '\n' +
                        "Name = " + jsonObject.get("Name") + '\n';
    }
}

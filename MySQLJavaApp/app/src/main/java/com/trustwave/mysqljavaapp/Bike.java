package com.trustwave.mysqljavaapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class Bike implements Parcelable {

    @SerializedName("TagID")
    Integer tagID;
    @SerializedName("UIN")
    Integer UIN;
    @SerializedName("SerialNumber")
    String serialNumber;
    @SerializedName("Make")
    String bikeMake;
    @SerializedName("Model")
    String model;
    @SerializedName("Color")
    String bikeColor;
    @SerializedName("Description")
    String bikeDescription;
    @SerializedName("UniqueCharacteristics")
    String uniqueCharacteristics;

    public Bike() { }

    public Bike(Integer tagID, Integer UIN, String serialNumber, String bikeMake, String model, String bikeColor, String bikeDescription, String uniqueCharacteristics) {
        this.tagID = tagID;
        this.UIN = UIN;
        this.serialNumber = serialNumber;
        this.bikeMake = bikeMake;
        this.model = model;
        this.bikeColor = bikeColor;
        this.bikeDescription = bikeDescription;
        this.uniqueCharacteristics = uniqueCharacteristics;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tagID);
        dest.writeInt(UIN);
        dest.writeString(serialNumber);
        dest.writeString(bikeMake);
        dest.writeString(model);
        dest.writeString(bikeColor);
        dest.writeString(bikeDescription);
        dest.writeString(uniqueCharacteristics);
    }

    public static final Parcelable.Creator<Bike> CREATOR = new Parcelable.Creator<Bike>() {
        public Bike createFromParcel(Parcel pc) {
            return new Bike(pc);
        }
        public Bike[] newArray(int size) {
            return new Bike[size];
        }
    };

    public Bike(Parcel pc) {
        tagID = pc.readInt();
        UIN = pc.readInt();
        serialNumber = pc.readString();
        bikeMake = pc.readString();
        model = pc.readString();
        bikeColor = pc.readString();
        bikeDescription = pc.readString();
        uniqueCharacteristics = pc.readString();
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getBikeMake() {
        return bikeMake;
    }

    public void setBikeMake(String bikeMake) {
        this.bikeMake = bikeMake;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBikeColor() {
        return bikeColor;
    }

    public void setBikeColor(String bikeColor) {
        this.bikeColor = bikeColor;
    }

    public String getBikeDescription() {
        return bikeDescription;
    }

    public void setBikeDescription(String bikeDescription) {
        this.bikeDescription = bikeDescription;
    }

    public Integer getTagID() {
        return tagID;
    }

    public void setTagID(Integer tagID) {
        this.tagID = tagID;
    }

    public Integer getUIN() {
        return UIN;
    }

    public void setUIN(Integer UIN) {
        this.UIN = UIN;
    }

    public String getUniqueCharacteristics() {
        return uniqueCharacteristics;
    }

    public void setUniqueCharacteristics(String uniqueCharacteristics) {
        this.uniqueCharacteristics = uniqueCharacteristics;
    }

    @Override
    public String toString() {
        return
                "TagID = " + tagID.toString() + '\n' +
                        "UIN = " + UIN.toString() + '\n' +
                        "SerialNumber = " + serialNumber + '\n' +
                        "BikeMake = " + bikeMake + '\n' +
                        "Model = " + model + '\n' +
                        "BikeColor = " + bikeColor + '\n' +
                        "BikeDescription = " + bikeDescription + '\n' +
                        "UniqueCharacteristics = " + uniqueCharacteristics + '\n';
    }

    public static String jsonToString(JSONObject jsonObject) throws JSONException {
        jsonObject = jsonObject.getJSONObject("data");
        return
                "TagID = " + jsonObject.get("TagID") + '\n' +
                        "UIN = " + jsonObject.get("UIN") + '\n' +
                        "SerialNumber = " +jsonObject.get("SerialNumber")+ '\n' +
                        "BikeMake = " + jsonObject.get("Make") + '\n' +
                        "Model = " + jsonObject.get("Model") + '\n' +
                        "BikeColor = " + jsonObject.get("Color") + '\n' +
                        "BikeDescription = " + jsonObject.get("Description") + '\n' +
                        "UniqueCharacteristics = " + jsonObject.get("UniqueCharacteristics") + '\n';
    }
}

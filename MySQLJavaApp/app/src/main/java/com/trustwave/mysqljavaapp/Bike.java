package com.trustwave.mysqljavaapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Bike implements Parcelable {

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
    @SerializedName("Owner")
    String bikeOwner;

    public Bike() { }

    public Bike(String serialNumber, String bikeMake, String model, String bikeColor, String bikeDescription, String bikeOwner) {
        this.serialNumber = serialNumber;
        this.bikeMake = bikeMake;
        this.model = model;
        this.bikeColor = bikeColor;
        this.bikeDescription = bikeDescription;
        this.bikeOwner = bikeOwner;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(serialNumber);
        dest.writeString(bikeMake);
        dest.writeString(model);
        dest.writeString(bikeColor);
        dest.writeString(bikeDescription);
        dest.writeString(bikeOwner);
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
        serialNumber = pc.readString();
        bikeMake = pc.readString();
        model = pc.readString();
        bikeColor = pc.readString();
        bikeDescription = pc.readString();
        bikeOwner = pc.readString();
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

    public String getBikeOwner() {
        return bikeOwner;
    }

    public void setBikeOwner(String bikeOwner) {
        this.bikeOwner = bikeOwner;
    }

    @Override
    public String toString() {
        return
                "SerialNumber= " + serialNumber + '\n' +
                "BikeMake=" + bikeMake + '\n' +
                "Model=" + model + '\n' +
                "BikeColor=" + bikeColor + '\n' +
                "BikeDescription=" + bikeDescription + '\n' +
                "BikeOwner=" + bikeOwner + '\n';
    }
}

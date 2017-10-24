package com.ttuicube.dibzitapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zeejfps on 10/11/2017.
 */

public class DibsRoom implements Parcelable {

    @SerializedName("RoomID")
    public final int roomID;

    @SerializedName("Name")
    public final String name;

    @SerializedName("Description")
    public final String description;

    @SerializedName("Map")
    public final String mapURL;

    @SerializedName("Picture")
    public final String pictureURL;

    public DibsRoom(int roomID, String name, String description, String mapURL, String pictureURL) {
        this.roomID = roomID;
        this.name = name;
        this.description = description;
        this.mapURL = mapURL;
        this.pictureURL = pictureURL;
    }

    protected DibsRoom(Parcel in) {
        roomID = in.readInt();
        name = in.readString();
        description = in.readString();
        mapURL = in.readString();
        pictureURL = in.readString();
    }

    public static final Creator<DibsRoom> CREATOR = new Creator<DibsRoom>() {
        @Override
        public DibsRoom createFromParcel(Parcel in) {
            return new DibsRoom(in);
        }

        @Override
        public DibsRoom[] newArray(int size) {
            return new DibsRoom[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(roomID);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(mapURL);
        dest.writeString(pictureURL);
    }

    @Override
    public String toString() {
        return name;
    }
}

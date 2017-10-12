package com.ttuicube.dibzitapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zeejfps on 10/11/2017.
 */

public class DibsRoom {

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

}

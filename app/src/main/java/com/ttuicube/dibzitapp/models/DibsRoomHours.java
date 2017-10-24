package com.ttuicube.dibzitapp.models;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.joda.time.DateTime;

import java.lang.reflect.Type;

/**
 * Created by zeejfps on 10/11/2017.
 */

public class DibsRoomHours {

    public final int roomID;
    public final DateTime startTime;
    public final DateTime endTime;

    public DibsRoomHours(int roomID, DateTime startTime, DateTime endTime) {
        this.roomID = roomID;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static class Serializer implements JsonDeserializer<DibsRoomHours> {

        @Override
        public DibsRoomHours deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject obj = json.getAsJsonObject();
            DateTime startTime = DateTime.parse(obj.get("StartTime").getAsString());
            DateTime endTime = DateTime.parse(obj.get("EndTime").getAsString());
            int roomID = obj.get("RoomID").getAsInt();
            return new DibsRoomHours(roomID, startTime, endTime);
        }

    }

}

package com.ttuicube.dibzitapp.database;

import android.provider.BaseColumns;

/**
 * Created by Zeejfps on 10/26/17.
 */

public final class DibzitContract {

    private DibzitContract() {}

    public static class WorkingHoursEntry implements BaseColumns {
        public static final String TABLE_NAME = "workingHours";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_ROOM_ID = "roomID";
        public static final String COLUMN_START_TIME = "startTime";
        public static final String COLUMN_END_TIME = "endTime";
    }

}

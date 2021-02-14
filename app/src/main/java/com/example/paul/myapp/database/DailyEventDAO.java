package com.example.paul.myapp.database;


import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.paul.myapp.model.DailyEvent;
import com.example.paul.myapp.utils.ApplicationPreferences;

import java.util.ArrayList;
import java.util.UUID;

public class DailyEventDAO {


    private DbHelper help;
    private Activity activity;

    public static final String QUERY_GET_ALL_EVENT = "SELECT *" + " FROM "
            + CalendarSchema.DailyTable.NAME + " INNER JOIN "
            + CalendarSchema.EventTable.NAME + " ON "
            + CalendarSchema.DailyTable.NAME + "." + CalendarSchema.DailyTable.Cols.EVENT_ID
            + " = " + CalendarSchema.EventTable.NAME + "." + CalendarSchema.EventTable.Cols.ID
            + " WHERE " + CalendarSchema.DailyTable.NAME + "." + CalendarSchema.DailyTable.Cols.USER_ID + " = ?";

    public static final String QUERY_GET_ALL_REPETABLE_EVENT = "SELECT *" + " FROM "
            + CalendarSchema.DailyTable.NAME + " INNER JOIN "
            + CalendarSchema.EventTable.NAME + " ON "
            + CalendarSchema.DailyTable.NAME + "." + CalendarSchema.DailyTable.Cols.EVENT_ID
            + " = " + CalendarSchema.EventTable.NAME + "." + CalendarSchema.EventTable.Cols.ID
            + " WHERE " + CalendarSchema.DailyTable.NAME + "." + CalendarSchema.DailyTable.Cols.USER_ID + " = ? "
            + " AND " + CalendarSchema.DailyTable.NAME + "." + CalendarSchema.DailyTable.Cols.REPEATABLE + " != 0";

    public static final String QUERY_GET_ALL_NONREPETABLE_EVENT = "SELECT *" + " FROM "
            + CalendarSchema.DailyTable.NAME + " INNER JOIN "
            + CalendarSchema.EventTable.NAME + " ON "
            + CalendarSchema.DailyTable.NAME + "." + CalendarSchema.DailyTable.Cols.EVENT_ID
            + " = " + CalendarSchema.EventTable.NAME + "." + CalendarSchema.EventTable.Cols.ID
            + " WHERE " + CalendarSchema.DailyTable.NAME + "." + CalendarSchema.DailyTable.Cols.USER_ID + " = ? "
            + " AND " + CalendarSchema.DailyTable.NAME + "." + CalendarSchema.DailyTable.Cols.REPEATABLE + " = 0";

    public DailyEventDAO(DbHelper help, Activity activity) {
        this.help = help;
        this.activity = activity;


    }

    private static ContentValues getContentValues(DailyEvent dailyEvent) {

        ContentValues values = new ContentValues();

        values.put(CalendarSchema.DailyTable.Cols.UUID, dailyEvent.getId().toString());
        values.put(CalendarSchema.DailyTable.Cols.EVENT_ID, dailyEvent.getEvent().getId().toString());
        values.put(CalendarSchema.DailyTable.Cols.START_EVENT, dailyEvent.getStartDate());
        values.put(CalendarSchema.DailyTable.Cols.COST, dailyEvent.getCost());
        values.put(CalendarSchema.DailyTable.Cols.DURATION, dailyEvent.getDuration());
        values.put(CalendarSchema.DailyTable.Cols.DESCRIPTION, dailyEvent.getDescription());
        values.put(CalendarSchema.DailyTable.Cols.USER_ID, dailyEvent.getUserId());
        values.put(CalendarSchema.DailyTable.Cols.REPEATABLE, dailyEvent.getRepeatable());
        values.put(CalendarSchema.DailyTable.Cols.UNTIL, dailyEvent.getUntil());

        return values;

    }


    public ArrayList<DailyEvent> getEvents(String query) {

        ApplicationPreferences applicationPreferences = ApplicationPreferences.getInstance(activity);
        Cursor cursor = help.getDatabase()
                .rawQuery(query.replace("?",  "'" + applicationPreferences.getUserId() + "'"), null);
        CalendarCursorWrapper calendarCursorWrapper = new CalendarCursorWrapper(cursor);


        ArrayList<DailyEvent> dailyEvents = new ArrayList<>();
        try {
            calendarCursorWrapper.moveToFirst();
            while (!calendarCursorWrapper.isAfterLast()) {
                dailyEvents.add(calendarCursorWrapper.getDailyEvent());
                calendarCursorWrapper.moveToNext();
            }
        } finally {
            calendarCursorWrapper.close();
        }
        return dailyEvents;
    }


    public DailyEvent getEvent(UUID id) {
        CalendarCursorWrapper cursor = (CalendarCursorWrapper) help.queryDaily(
                CalendarSchema.DailyTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getDailyEvent();
        } finally {
            cursor.close();
        }
    }

    public void addEvent(DailyEvent dailyEvent) {
        ContentValues values = getContentValues(dailyEvent);
        help.getDatabase().insert(CalendarSchema.DailyTable.NAME, null, values);
    }

    public void deleteEvent(DailyEvent dailyEvent) {

        String uuidString = dailyEvent.getId().toString();
        help.getDatabase().delete(CalendarSchema.DailyTable.NAME,
                CalendarSchema.DailyTable.Cols.UUID + " = ?", new String[]{uuidString});


    }

    public void updateEvent(DailyEvent dailyEvent) {
        String uuidString = dailyEvent.getId().toString();
        ContentValues values = getContentValues(dailyEvent);
        help.getDatabase().update(CalendarSchema.DailyTable.NAME, values,
                CalendarSchema.DailyTable.Cols.UUID + " = ?", new String[]{uuidString});

    }


}

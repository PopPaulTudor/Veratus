package com.example.paul.myapp.database;

import android.app.Activity;
import android.content.ContentValues;

import com.example.paul.myapp.model.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



public class EventDAO {

    private DbHelper help;
    private Activity activity;

    public EventDAO(DbHelper help,Activity activity) {
        this.help = help;
        this.activity= activity;
    }

    private static ContentValues getContentValues(Event event) {

        ContentValues values = new ContentValues();

        values.put(CalendarSchema.EventTable.Cols.ID, event.getId().toString());
        values.put(CalendarSchema.EventTable.Cols.NUME, event.getNume());
        values.put(CalendarSchema.EventTable.Cols.TYPE, event.getType());

        return values;
    }

    public List<Event> getEvents() {

        List<Event> events = new ArrayList<>();
        CalendarCursorWrapper cursor = (CalendarCursorWrapper) help.queryEvent(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                events.add(cursor.getEvent());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return events;
    }

    public Event getEvent(UUID id) {

        CalendarCursorWrapper cursor = (CalendarCursorWrapper) help.queryEvent(
                CalendarSchema.EventTable.Cols.ID + " = ?",
                new String[]{id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getEvent();
        } finally {
            cursor.close();
        }
    }

    public void addEvent(Event event) {
        ContentValues values = getContentValues(event);
        help.getDatabase().insert(CalendarSchema.EventTable.NAME, null, values);
    }

    public void deleteEvent(Event event) {

        String uuidString = event.getId().toString();
        ContentValues values = getContentValues(event);
        help.getDatabase().delete(CalendarSchema.DailyTable.NAME,
                CalendarSchema.EventTable.Cols.NUME + " = ?", new String[]{uuidString});


    }

    public void updateEvent(Event event) {
        String uuidString = event.getId().toString();
        ContentValues values = getContentValues(event);
        help.getDatabase().update(CalendarSchema.EventTable.NAME, values,
                CalendarSchema.EventTable.Cols.NUME + " = ?",
                new String[]{uuidString});

    }

}
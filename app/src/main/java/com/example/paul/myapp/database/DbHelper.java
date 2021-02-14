package com.example.paul.myapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.paul.myapp.database.CalendarSchema.DailyTable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "doggie.db";
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private static DbHelper instance;



    private DbHelper(Context context) {

        super(context, DATABASE_NAME, null, VERSION);
        mContext=context.getApplicationContext();
        openDataBase();
    }

    public static DbHelper getInstance(Context context){

        if(instance==null){
            instance= new DbHelper(context);

        }
        return  instance;
    }

    public void openDataBase(){
        try {
            if (mDatabase == null || !mDatabase.isOpen()) {
               mDatabase = getWritableDatabase();
            }
        }catch (SQLException e){
            mDatabase=getReadableDatabase();

        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.beginTransaction();

        db.execSQL("create table " + DailyTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                DailyTable.Cols.UUID + ", " +
                DailyTable.Cols.EVENT_ID + ", " +
                DailyTable.Cols.UNTIL+", "+
                DailyTable.Cols.REPEATABLE+", "+
                DailyTable.Cols.START_EVENT + ", " +
                DailyTable.Cols.DESCRIPTION + ", " +
                DailyTable.Cols.DURATION+ ", " +
                DailyTable.Cols.COST + ", "+
                DailyTable.Cols.USER_ID +  ")");

        db.execSQL("create table " + CalendarSchema.EventTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                CalendarSchema.EventTable.Cols.ID + ", " +
                CalendarSchema.EventTable.Cols.TYPE + ", " +
                CalendarSchema.EventTable.Cols.NUME +  ")");


        db.execSQL("create table " + CalendarSchema.UserTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                CalendarSchema.UserTable.Cols.USERNAME + ", " +
                CalendarSchema.UserTable.Cols.PASSWORD + ", " +
                CalendarSchema.UserTable.Cols.NAME + ", " +
                CalendarSchema.UserTable.Cols.PRENAME + ", " +
                CalendarSchema.UserTable.Cols.AGE + ", " +
                CalendarSchema.UserTable.Cols.YEAR + ", " +
                CalendarSchema.UserTable.Cols.POINT + ", " +
                CalendarSchema.UserTable.Cols.ID+")");



        db.setTransactionSuccessful();
        db.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public SQLiteDatabase getDatabase() {
        return mDatabase;
    }



    CursorWrapper queryDaily(String whereClause, String[] whereArgs) {

        Cursor cursor= mDatabase.rawQuery("SELECT * FROM "+DailyTable.NAME
                +" LEFT JOIN "+ CalendarSchema.EventTable.NAME+" ON "+DailyTable.Cols.EVENT_ID+" = "+ CalendarSchema.EventTable.Cols.ID
                +" WHERE "+DailyTable.Cols.UUID+" = "+"'"+whereArgs[0]+"'",null);
        return new CalendarCursorWrapper(cursor);
    }

    CursorWrapper queryEvent(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query( CalendarSchema.EventTable.NAME, null,
                whereClause, whereArgs,
                null, null, null);

        return new CalendarCursorWrapper(cursor);
    }


    CursorWrapper queryUser(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CalendarSchema.UserTable.NAME, null,
                whereClause, whereArgs,
                null, null, null);

        return new CalendarCursorWrapper(cursor);
    }



}





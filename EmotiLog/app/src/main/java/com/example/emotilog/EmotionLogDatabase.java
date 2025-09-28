package com.example.emotilog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * This class is the helper for the database that stores `EmotionLog`s.
 */
public class EmotionLogDatabase extends SQLiteOpenHelper {
    // database values
    private static final String DATABASE_NAME = "emotion_logs.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "emotion_logs";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMOTION = "emotion";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    /**
     * Constructs a new `EmotionLogDatabase`
     * @param context the current state/environment of the app
     */
    public EmotionLogDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates the database
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create the table
        String createTable = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EMOTION + " TEXT NOT NULL, "
                + COLUMN_TIMESTAMP + " INTEGER NOT NULL)";
        db.execSQL(createTable);
    }

    /**
     * Updates the database
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Deletes the database
     */
    public void deleteTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Adds an `EmotionLog` to the database
     * @param emotion emoticon to be added
     */
    public void addEmotionLog(String emotion) {
        SQLiteDatabase db = this.getWritableDatabase();
        // pack data into values to insert into the database
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMOTION, emotion);
        values.put(COLUMN_TIMESTAMP, new Date().getTime());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    /**
     * Gets all the `EmotionLog`s of a certain date
     * @param date date to get emotions from
     * @return `ArrayList` of the `EmotionLog`s specified by `date`
     */
    public ArrayList<EmotionLog> getEmotionLogsByDate(Date date) {
        ArrayList<EmotionLog> emotions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // day start
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long dayStart = calendar.getTimeInMillis();

        // day end
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        long dayEnd = calendar.getTimeInMillis();

        // selection and arguments to extract data from database
        String selection = COLUMN_TIMESTAMP + " >= ? AND " + COLUMN_TIMESTAMP + " < ?";
        String[] selectionArgs = {String.valueOf(dayStart), String.valueOf(dayEnd)};
        Cursor cursor = null;
        try {
            // get rows from table
            cursor = db.query(
                    TABLE_NAME,
                    null,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    COLUMN_TIMESTAMP + " DESC"
            );
            // iterate over rows and add them to emotions ArrayList
            if (cursor.moveToFirst()) {
                do {
                    int emotionIndex = cursor.getColumnIndex(COLUMN_EMOTION);
                    int timestampIndex = cursor.getColumnIndex(COLUMN_TIMESTAMP);

                    if (emotionIndex != -1 && timestampIndex != -1) {
                        String emotion = cursor.getString(emotionIndex);
                        long timestampMs = cursor.getLong(timestampIndex);
                        emotions.add(new EmotionLog(emotion, new Date(timestampMs)));
                    }
                } while (cursor.moveToNext());
            }
        } finally {
            // free cursor
            if (cursor != null) cursor.close();
        }

        return emotions;
    }
}

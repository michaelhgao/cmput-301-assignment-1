package com.example.emotilog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EmotionLogDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "emotion_logs.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "emotion_logs";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMOTION = "emotion";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    public EmotionLogDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EMOTION + " TEXT NOT NULL, "
                + COLUMN_TIMESTAMP + " INTEGER NOT NULL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addEmotionLog(String emotion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMOTION, emotion);
        values.put(COLUMN_TIMESTAMP, new Date().getTime());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

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

        String selection = COLUMN_TIMESTAMP + " >= ? AND " + COLUMN_TIMESTAMP + " < ?";
        String[] selectionArgs = {String.valueOf(dayStart), String.valueOf(dayEnd)};
        Cursor cursor = null;
        try {
            cursor = db.query(
                    TABLE_NAME,
                    null,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    COLUMN_TIMESTAMP + " DESC"
            );

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
            if (cursor != null) cursor.close();
        }

        return emotions;
    }
}

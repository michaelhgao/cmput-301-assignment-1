package com.example.emotilog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EmotionLogManager {
    ArrayList<EmotionLog> emotionLogs;

    public EmotionLogManager() {
        this.emotionLogs = new ArrayList<>();
    }

    public void addEmotionLog(String emotion) {
        EmotionLog emotionLog = new EmotionLog(emotion, new Date());
        this.emotionLogs.add(emotionLog);
    }

    public ArrayList<EmotionLog> getEmotionLogsByDate(Date date) {
        ArrayList<EmotionLog> emotionsThisDay = new ArrayList<>();
        Calendar thisDay = Calendar.getInstance();
        Calendar emotionLogDate = Calendar.getInstance();

        for (EmotionLog emotionLog: emotionLogs) {
            thisDay.setTime(date);
            emotionLogDate.setTime(emotionLog.getTimestamp());

            boolean sameDay = thisDay.get(Calendar.YEAR) == emotionLogDate.get(Calendar.YEAR) && thisDay.get(Calendar.DAY_OF_YEAR) == emotionLogDate.get(Calendar.DAY_OF_YEAR);
            if (sameDay) {
                emotionsThisDay.add(emotionLog);
            }
        }

        return emotionsThisDay;
    }
}

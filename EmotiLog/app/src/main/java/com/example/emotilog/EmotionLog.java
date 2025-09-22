package com.example.emotilog;

import java.util.Date;

public class EmotionLog {
    String emotion;
    Date timestamp;

    public EmotionLog(String emotion, Date timestamp) {
        this.emotion = emotion;
        this.timestamp = timestamp;
    }

    public String getEmotion() {
        return this.emotion;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }
}

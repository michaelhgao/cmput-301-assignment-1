package com.example.emotilog;

import java.io.Serializable;
import java.util.Date;

public class EmotionLog implements Serializable {
    String emotion;
    Date timestamp;

    public EmotionLog(String emotion, Date timestamp) {
        this.emotion = emotion;
        this.timestamp = timestamp;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getEmotion() {
        return this.emotion;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }
}

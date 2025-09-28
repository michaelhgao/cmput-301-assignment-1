package com.example.emotilog;

import java.util.Date;

/**
 * This class represents an `EmotionLog`, which is an `emotion` felt at a certain `timestamp`.
 */
public class EmotionLog {
    String emotion;
    Date timestamp;

    /**
     * Constructs a new `EmotionLog` with an `emotion` and `timestamp`
     * @param emotion the emoticon for this log
     * @param timestamp the time this log was created
     */
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

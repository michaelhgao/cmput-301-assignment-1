package com.example.emotilog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class EmotionLogArrayAdapter extends ArrayAdapter<EmotionLog> {
    SimpleDateFormat dateFormat;

    public EmotionLogArrayAdapter(Context context, ArrayList<EmotionLog> emotionLogs) {
        super(context, 0, emotionLogs);
        this.dateFormat = new SimpleDateFormat("yyyy-MM-ss, HH:mm", Locale.getDefault());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.content, parent, false);
        }
        else {
            view = convertView;
        }

        EmotionLog emotionLog = getItem(position);
        TextView emotionText = view.findViewById(R.id.emotion_text);
        TextView timestampText = view.findViewById(R.id.timestamp_text);

        emotionText.setText(emotionLog.getEmotion());
        timestampText.setText(this.dateFormat.format(emotionLog.getTimestamp()));

        return view;
    }
}

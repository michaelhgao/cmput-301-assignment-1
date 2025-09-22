package com.example.emotilog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class EmotionLogArrayAdapter extends ArrayAdapter<EmotionLog> {
    public EmotionLogArrayAdapter(Context context, ArrayList<EmotionLog> emotionLogs) {
        super(context, 0, emotionLogs);
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
        timestampText.setText(emotionLog.getTimestamp().toString());

        return view;
    }
}

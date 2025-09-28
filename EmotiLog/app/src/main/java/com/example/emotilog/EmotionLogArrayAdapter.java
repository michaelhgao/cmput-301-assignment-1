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

/**
 * This class is a custom adapter, based on `ArrayAdapter`, for `EmotionLog`. It displays an
 * `EmotionLog`'s `emotion` and `timestamp` side by side, in a `LinearLayout` found in
 * `emotion_timestamp.xml`.
 */
public class EmotionLogArrayAdapter extends ArrayAdapter<EmotionLog> {
    SimpleDateFormat dateFormat; // yyyy-MM-dd, HH:mm

    /**
     * Constructs a new `EmotionLogArrayAdapter` with the specified `context` and `emotionLogs`.
     * @param context the current state/environment of the app
     * @param emotionLogs an `ArrayList` of `EmotionLog`s
     */
    public EmotionLogArrayAdapter(Context context, ArrayList<EmotionLog> emotionLogs) {
        super(context, 0, emotionLogs);
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd, HH:mm", Locale.getDefault());
    }

    /**
     * Gets a view that diplays a `EmotionLog`'s `emotion` and `timestamp` side by side, in a
     * `LinearLayout`
     * @param position The position of the item within the adapter's data set of the item whose view
     *        we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *        is non-null and of an appropriate type before using. If it is not possible to convert
     *        this view to display the correct data, this method can create a new view.
     *        Heterogeneous lists can specify their number of view types, so that this View is
     *        always of the right type (see {@link #getViewTypeCount()} and
     *        {@link #getItemViewType(int)}).
     * @param parent The parent that this view will eventually be attached to
     * @return The view of the specified data
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        // view recycling
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.emotion_timestamp, parent, false);
        }
        else {
            view = convertView;
        }

        // binding data to views
        EmotionLog emotionLog = getItem(position);
        TextView emotionText = view.findViewById(R.id.emotion_text);
        TextView timestampText = view.findViewById(R.id.timestamp_text);

        // setting text for array item
        emotionText.setText(emotionLog.getEmotion());
        timestampText.setText(this.dateFormat.format(emotionLog.getTimestamp()));

        return view;
    }
}

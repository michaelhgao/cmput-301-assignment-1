package com.example.emotilog;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * This class is the activity where the user can see their `EmotionLog`s and a summary of their
 * `EmotionLog`s
 */
public class ViewEmotionLogsActivity extends AppCompatActivity {
    SimpleDateFormat dateFormat; // MMMM dd, yyyy
    Date currentDate;

    ArrayList<EmotionLog> emotions;
    EmotionLogArrayAdapter emotionsAdapter;
    ListView emotionsView;

    EmotionLogDatabase db;

    /**
     * Initializes all attributes and views
     */
    private void initActivity() {
        this.dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        this.currentDate = new Date();
        this.initButtons();
        this.initListViewEmotions();
    }

    /**
     * Initializes `Button`s
     */
    private void initButtons() {
        // gets buttons/textviews by views
        Button btnBackToMain = findViewById(R.id.btn_logs_back_to_main);
        Button btnViewSummary = findViewById(R.id.btn_logs_view_summary);
        Button btnPrevDate = findViewById(R.id.btn_logs_prev_date);
        Button btnNextDate = findViewById(R.id.btn_logs_next_date);
        TextView textViewCurrentDate = findViewById(R.id.textview_logs_current_date);

        textViewCurrentDate.setText(this.dateFormat.format(this.currentDate));

        // button to go to `MainActivity`
        btnBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(ViewEmotionLogsActivity.this, MainActivity.class);
            startActivity(intent);
        });
        // button to view a summary of today's emotions
        btnViewSummary.setOnClickListener(v -> {
            this.viewSummary();
        });

        // buttons to alter the date, and view emotions by the selected date
        btnPrevDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(this.currentDate);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            this.currentDate = calendar.getTime();
            this.updateListViewEmotionsAndDate();

        });
        btnNextDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(this.currentDate);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            this.currentDate = calendar.getTime();
            this.updateListViewEmotionsAndDate();
        });
        textViewCurrentDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(this.currentDate);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                ViewEmotionLogsActivity.this,
                (view, year1, month1, dayOfMonth) -> {
                    Calendar chosen = Calendar.getInstance();
                    chosen.set(year1, month1, dayOfMonth);
                    this.currentDate = chosen.getTime();
                    updateListViewEmotionsAndDate();
                },
                year,
                month,
                day);

            datePickerDialog.show();
        });
    }

    /**
     * Initializes the `ListView` that displays `EmotionLog`s
     */
    private void initListViewEmotions() {
        this.db = new EmotionLogDatabase(this);
        this.emotions = this.db.getEmotionLogsByDate(this.currentDate);

        this.emotionsView = findViewById(R.id.listview_logs_logs);

        this.emotionsAdapter = new EmotionLogArrayAdapter(this, emotions);
        this.emotionsView.setAdapter(this.emotionsAdapter);
    }

    /**
     * Updates the current date at the top, and notifies the adapter to show the new data
     */
    private void updateListViewEmotionsAndDate() {
        this.emotions.clear();
        this.emotions.addAll(db.getEmotionLogsByDate(this.currentDate));
        this.emotionsAdapter.notifyDataSetChanged();

        TextView textViewCurrentDate = findViewById(R.id.textview_logs_current_date);
        textViewCurrentDate.setText(this.dateFormat.format(this.currentDate));
    }

    /**
     * Views the current date's summary, including total logs, and frequencies of each emotion
     */
    private void viewSummary() {
        // no emotions to show
        if (this.emotions.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Summary for " + this.dateFormat.format(this.currentDate))
                    .setMessage("Nothing for " + this.dateFormat.format(this.currentDate))
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        // creates a map of emotion: frequency
        Map<String, Integer> counts = new HashMap<>();
        for (EmotionLog log : this.emotions) {
            counts.put(log.getEmotion(), counts.getOrDefault(log.getEmotion(), 0) + 1);
        }

        // total number of emotions logged
        int total = this.emotions.size();

        // creates the string to display the current date's emotions
        StringBuilder summary = new StringBuilder();
        summary.append("Total logs: ").append(total).append("\n\n");
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            summary.append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append("\n");
        }

        // creates an AlertDialog to show the current date's emotions
        new AlertDialog.Builder(this)
                .setTitle("Summary for " + this.dateFormat.format(this.currentDate))
                .setMessage(summary.toString())
                .setPositiveButton("OK", null)
                .show();
    }

    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.view_emotion_logs);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.view_logs), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initActivity();

    }
}

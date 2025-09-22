package com.example.emotilog;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ViewEmotionLogsActivity extends AppCompatActivity {
    SimpleDateFormat dateFormat;
    Date currentDate;
    ArrayList<EmotionLog> currentDateEmotions;

    EmotionLogArrayAdapter emotionsAdapter;
    ListView emotionsView;


    private void createButtons() {
        Button btnBackToMain = findViewById(R.id.btn_logs_back_to_main);
        Button btnPrevDate = findViewById(R.id.btn_logs_prev_date);
        Button btnNextDate = findViewById(R.id.btn_logs_next_date);
        TextView textViewCurrentDate = findViewById(R.id.textview_logs_current_date);

        dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        currentDate = new Date();
        textViewCurrentDate.setText(dateFormat.format(currentDate));

        btnBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(ViewEmotionLogsActivity.this, MainActivity.class);
            startActivity(intent);
        });
        btnPrevDate.setOnClickListener(v -> {});
        btnNextDate.setOnClickListener(v -> {});
        textViewCurrentDate.setOnClickListener(v -> {
            final Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                ViewEmotionLogsActivity.this,
                (view, year1, month1, dayOfMonth) -> textViewCurrentDate.setText(dayOfMonth + "-" + (month1 +1) + "-" + year1),
                year,
                month,
                day);
            
            datePickerDialog.show();
        });
    }
    
    private void createListViewEmotions() {
        currentDateEmotions = EmotionLogManager.getEmotionLogsByDate(currentDate);

        emotionsView = findViewById(R.id.listview_logs_logs);

        emotionsAdapter = new EmotionLogArrayAdapter(this, currentDateEmotions);
        emotionsView.setAdapter(emotionsAdapter);
    }

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

        createListViewEmotions();
        createButtons();



    }
}

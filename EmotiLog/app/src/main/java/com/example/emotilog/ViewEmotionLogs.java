package com.example.emotilog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;

public class ViewEmotionLogs extends AppCompatActivity {
    ListView emotionsView;
    ArrayAdapter<String> emotionsAdapter;
    SimpleDateFormat dateFormat;

    private void createButtons() {
        Button btnBackToMain = findViewById(R.id.btn_logs_back_to_main);
        Button btnPrevDate = findViewById(R.id.btn_logs_prev_date);
        Button btnNextDate = findViewById(R.id.btn_logs_next_date);
        TextView textViewCurrentDate = findViewById(R.id.textview_logs_current_date);

        textViewCurrentDate.setText("MMMM dd, yyyy");

        btnBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(ViewEmotionLogs.this, MainActivity.class);
            startActivity(intent);
        });
        btnPrevDate.setOnClickListener(v -> {});
        btnNextDate.setOnClickListener(v -> {});
        textViewCurrentDate.setOnClickListener(v -> {});
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

        emotionsView = findViewById(R.id.listview_logs_logs);

    }
}

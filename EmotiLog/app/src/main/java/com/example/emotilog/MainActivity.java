package com.example.emotilog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final List<String> emotions = Arrays.asList("\uD83D\uDE00", "\uD83D\uDE06", "\uD83D\uDE0D", "\uD83E\uDD2A", "\uD83D\uDE0E", "\uD83D\uDE41", "\uD83D\uDE21", "\uD83D\uDE28", "\uD83D\uDE10");
    EmotionLogDatabase db;

    private void createButtons() {
        Button btn0 = findViewById(R.id.btn_main_0);
        Button btn1 = findViewById(R.id.btn_main_1);
        Button btn2 = findViewById(R.id.btn_main_2);
        Button btn3 = findViewById(R.id.btn_main_3);
        Button btn4 = findViewById(R.id.btn_main_4);
        Button btn5 = findViewById(R.id.btn_main_5);
        Button btnViewEmotionLogs = findViewById(R.id.btn_main_view_logs);

        btn0.setText(this.emotions.get(0));
        btn1.setText(this.emotions.get(1));
        btn2.setText(this.emotions.get(2));
        btn3.setText(this.emotions.get(3));
        btn4.setText(this.emotions.get(4));
        btn5.setText(this.emotions.get(5));

        btn0.setOnClickListener(v -> this.db.addEmotionLog(this.emotions.get(0)));
        btn1.setOnClickListener(v -> this.db.addEmotionLog(this.emotions.get(1)));
        btn2.setOnClickListener(v -> this.db.addEmotionLog(this.emotions.get(2)));
        btn3.setOnClickListener(v -> this.db.addEmotionLog(this.emotions.get(3)));
        btn4.setOnClickListener(v -> this.db.addEmotionLog(this.emotions.get(4)));
        btn5.setOnClickListener(v -> this.db.addEmotionLog(this.emotions.get(5)));
        btnViewEmotionLogs.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewEmotionLogsActivity.class);
            startActivity(intent);
        });
    }

    private void changeEmotionButton() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.db = new EmotionLogDatabase(this);
        this.createButtons();

    }
}
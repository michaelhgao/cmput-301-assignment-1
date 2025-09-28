package com.example.emotilog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final List<String> emotions = Arrays.asList("\uD83D\uDE00", "\uD83D\uDE06", "\uD83D\uDE0D", "\uD83E\uDD2A", "\uD83D\uDE0E", "\uD83D\uDE41", "\uD83D\uDE21", "\uD83D\uDE28", "\uD83D\uDE10");
    EmotionLogDatabase db;

    private void initActivity() {
        this.db = new EmotionLogDatabase(this);
        this.initButtons();
    }

    private void initButtons() {
        Button btn0 = findViewById(R.id.btn_main_0);
        Button btn1 = findViewById(R.id.btn_main_1);
        Button btn2 = findViewById(R.id.btn_main_2);
        Button btn3 = findViewById(R.id.btn_main_3);
        Button btn4 = findViewById(R.id.btn_main_4);
        Button btn5 = findViewById(R.id.btn_main_5);
        Button btnViewEmotionLogs = findViewById(R.id.btn_main_view_logs);

        Button[] btns = { btn0, btn1, btn2, btn3, btn4, btn5 };

        for (int i = 0; i < btns.length; i++) {
            int j = i;
            String savedEmoticon = getSharedPreferences("emoticon_prefs", MODE_PRIVATE).getString("btn_" + i, this.emotions.get(i));
            btns[i].setText(savedEmoticon);
            btns[i].setOnClickListener(v -> this.addEmotionLog(btns[j]));
            btns[i].setOnLongClickListener(v -> {
                this.changeEmotionButton(btns[j], j);
                return true;
            });
        }

        btnViewEmotionLogs.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewEmotionLogsActivity.class);
            startActivity(intent);
        });
    }

    private void addEmotionLog(Button btn) {
        this.db.addEmotionLog(btn.getText().toString().trim());
    }

    private void changeEmotionButton(Button btn, int btnIndex) {
        CharSequence[] emoticons = this.emotions.toArray(new CharSequence[0]);

        new AlertDialog.Builder(this)
                .setTitle("Change Emoticon")
                .setItems(emoticons, (dialog, which) -> {
                    String selectedEmoticon = this.emotions.get(which);
                    btn.setText(selectedEmoticon);
                    getSharedPreferences("emoticon_prefs", MODE_PRIVATE)
                            .edit()
                            .putString("btn_" + btnIndex, selectedEmoticon)
                            .apply();
                })
                .setNegativeButton("Cancel", null)
                .show();
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


        this.initActivity();

    }
}
package com.example.emotilog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // list of all emotions available to users
    final List<String> selectableEmotions = Arrays.asList("\uD83D\uDE00", "\uD83D\uDE06", "\uD83D\uDE0D", "\uD83E\uDD2A", "\uD83D\uDE0E", "\uD83D\uDE41", "\uD83D\uDE21", "\uD83D\uDE28", "\uD83D\uDE10");
    EmotionLogDatabase db;

    /**
     * Initializes all attributes and views
     */
    private void initActivity() {
        this.db = new EmotionLogDatabase(this);
        this.initButtons();
    }

    /**
     * Initializes buttons
     */
    private void initButtons() {
        // get all buttons
        Button btn0 = findViewById(R.id.btn_main_0);
        Button btn1 = findViewById(R.id.btn_main_1);
        Button btn2 = findViewById(R.id.btn_main_2);
        Button btn3 = findViewById(R.id.btn_main_3);
        Button btn4 = findViewById(R.id.btn_main_4);
        Button btn5 = findViewById(R.id.btn_main_5);
        Button btnViewEmotionLogs = findViewById(R.id.btn_main_view_logs);

        // emoticon buttons
        Button[] btns = { btn0, btn1, btn2, btn3, btn4, btn5 };

        // iterates over btns and assigns text and listeners
        for (int i = 0; i < btns.length; i++) {
            int j = i;
            // get saved emoticon buttons from shared preferences, defaults to emotions[i]
            String savedEmoticon = getSharedPreferences("emoticon_prefs", MODE_PRIVATE).getString("btn_" + i, this.selectableEmotions.get(i));
            btns[i].setText(savedEmoticon);
            btns[i].setOnClickListener(v -> this.addEmotionLog(btns[j]));
            btns[i].setOnLongClickListener(v -> {
                this.changeEmotionButton(btns[j], j);
                return true;
            });
        }

        // button to go to `ViewEmotionLogsActivity`
        btnViewEmotionLogs.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewEmotionLogsActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Wrapper around `EmotionLogDatabase`'s `addEmotionLog`
     * @param btn button to get emotion from
     */
    private void addEmotionLog(Button btn) {
        this.db.addEmotionLog(btn.getText().toString().trim());
    }

    /**
     * Creates an `AlertDialog` that changes a button's emoticon based on the `emotions` list
     * @param btn button to change the emoticon of
     * @param btnIndex the button's index
     */
    private void changeEmotionButton(Button btn, int btnIndex) {
        CharSequence[] emoticons = this.selectableEmotions.toArray(new CharSequence[0]);

        new AlertDialog.Builder(this)
                .setTitle("Change Emoticon")
                .setItems(emoticons, (dialog, which) -> {
                    String selectedEmoticon = this.selectableEmotions.get(which);
                    btn.setText(selectedEmoticon);
                    // saves emoticon button text into shared preferences
                    getSharedPreferences("emoticon_prefs", MODE_PRIVATE)
                            .edit()
                            .putString("btn_" + btnIndex, selectedEmoticon)
                            .apply();
                })
                .setNegativeButton("Cancel", null)
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
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        this.initActivity();

    }
}

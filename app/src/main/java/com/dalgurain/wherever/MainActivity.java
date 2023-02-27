package com.dalgurain.wherever;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnVoiceReport, btnKeyboardReport;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnVoiceReport = findViewById(R.id.btn_voice_report);
        btnKeyboardReport = findViewById(R.id.btn_keyboard_report);

        btnVoiceReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecordReportActivity.class);
                startActivity(intent);
            }
        });

        btnKeyboardReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TextReportActivity.class);
                startActivity(intent);
            }
        });
    }
}

package com.dalgurain.wherever;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class TextReportActivity extends AppCompatActivity {
    Button btnTextComplete;
    EditText editReportLocation, editReportContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        init();
        setListener();
    }

    private void init(){
        btnTextComplete = findViewById(R.id.btn_text_complete);
        editReportLocation = findViewById(R.id.edit_report_location);
        editReportContext = findViewById(R.id.edit_report_context);
    }

    private void setListener(){
        btnTextComplete.setOnClickListener(view -> {
            String location = editReportLocation.getText().toString();
            String context = editReportContext.getText().toString();

            Intent intent = new Intent(TextReportActivity.this, PhotoActivity.class);
            intent.putExtra("location", location);
            intent.putExtra("context", context);

            startActivity(intent);
        });
    }
}

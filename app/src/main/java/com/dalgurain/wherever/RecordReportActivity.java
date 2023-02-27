package com.dalgurain.wherever;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.dalgurain.wherever.controller.RetrofitController;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordReportActivity extends AppCompatActivity {
    Button btnRecord;
    Button btnRecordPause;

    private String recordPermission = Manifest.permission.RECORD_AUDIO;
    private int PERMISSION_CODE = 21;

    private MediaRecorder mediaRecorder;
    private String audioFileName;
    private boolean isRecording = false;
    private Uri audioUri = null;

    private MediaPlayer mediaPlayer = null;
    private Boolean isPlaying = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        init();
    }

    private void init() {
        btnRecord = findViewById(R.id.recordBtn);
        btnRecordPause = findViewById(R.id.recordPauseBtn);

        btnRecord.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(isRecording) {
                    isRecording = false;
                    stopRecording();
                    Intent intent = new Intent(getApplicationContext(), PhotoActivity.class);
                    startActivity(intent);
                }
                else {
                    if(checkAudioPermission()) {
                        isRecording = true;
                        btnRecord.setText("확인");
                        startRecording();
                    }
                }
            }
        });

        btnRecordPause.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if(isRecording) {
                    isRecording = false;
                    btnRecordPause.setText("계속하기");
                    btnRecordPause.setBackgroundColor(Color.WHITE);
                    btnRecordPause.setTextColor(Color.BLACK);
                    mediaRecorder.pause();
                }
                else {
                    if(checkAudioPermission()) {
                        isRecording = true;
                        btnRecordPause.setText("중단하기");
                        btnRecordPause.setBackgroundColor(Color.parseColor("#37373a"));
                        btnRecordPause.setTextColor(Color.WHITE);
                        mediaRecorder.resume();
                    }
                }
            }
        });
    }

    private boolean checkAudioPermission() {
        boolean permissionCheck = (ActivityCompat.checkSelfPermission(getApplicationContext(), recordPermission) == PackageManager.PERMISSION_GRANTED);
        if(!permissionCheck) {
            ActivityCompat.requestPermissions(this, new String[] {recordPermission}, PERMISSION_CODE);
        }
        return permissionCheck;
    }

    private void stopRecording() {
        File audioFile = new File(audioFileName);
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;

        if (audioFile != null && audioFile.exists()) {
            mediaPlayer = new MediaPlayer();

            try {
                mediaPlayer.setDataSource(audioFileName);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            reportUpload(audioFile, "location from wherever");
        }
        else {
            Toast.makeText(this, "녹음을 먼저 진행해주세요", Toast.LENGTH_SHORT).show();
        }
        Intent intent = getIntent();
        intent.getStringExtra("longitude");

    }

    private void startRecording() {
        String recordPath = getExternalCacheDir().getAbsolutePath();
        String timeStamp = new SimpleDateFormat("_yyMMdd").format(new Date());
        audioFileName = recordPath + "/" + timeStamp + ".mp4";

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(audioFileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        Toast.makeText(this, audioFileName, Toast.LENGTH_LONG).show();
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaRecorder.start();
    }

    private void reportUpload(File recordFile, String location) {
        RequestBody fileRequestBody = RequestBody.create(MediaType.parse("video/mp4"), recordFile);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("record", recordFile.getName(), fileRequestBody);

        MultipartBody.Part textPart = MultipartBody.Part.createFormData("location", location);

        Call<ResponseBody> call = RetrofitController.getInstance().getService().ReportUpload(filePart, textPart);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.v("upload", "success");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("upload error", t.getMessage());
            }
        });
    }
}

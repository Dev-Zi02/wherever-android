package com.dalgurain.wherever;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.dalgurain.wherever.utils.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends AppCompatActivity {
    private Button btnShoot, btnAdd, btnPass;
    private List<String> listPermission = new ArrayList<String>();
    private String photoPermission = Manifest.permission.CAMERA;
    private PermissionUtil permissionUtil = new PermissionUtil();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        init();

        listPermission.add(photoPermission);
        btnShoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");
        String context = intent.getStringExtra("context");
    }

    private void init(){
        btnShoot = findViewById(R.id.btn_shoot_photo);
        btnAdd = findViewById(R.id.btn_add_photo);
        btnPass = findViewById(R.id.btn_pass_photo);
    }

    private void takePicture() {
        permissionUtil.checkAudioPermission(getApplicationContext(), this, listPermission);
        Intent photoTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(photoTakeIntent.resolveActivity(getPackageManager()) != null) {
            startActivityR.launch(photoTakeIntent);
        }
    }

    ActivityResultLauncher<Intent> startActivityR = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        Log.d("tag", "PhotoActivity로 돌아왔다");
                    }
                }
            }
    );



    private void accessGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setType("images/*");

    }
}

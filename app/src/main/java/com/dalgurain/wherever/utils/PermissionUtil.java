package com.dalgurain.wherever.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtil {
    private int PERMISSION_CODE = 27;

    public boolean checkAudioPermission(Context context, Activity activity, List<String> listPermission) {
        boolean permissionCheck = true;
        ArrayList<String> arrDeniedPermissions = new ArrayList<String>();
        for (String permission : listPermission) {
            boolean check = (ActivityCompat.checkSelfPermission(context, permission)) == PackageManager.PERMISSION_GRANTED;
            permissionCheck = permissionCheck && check;
            if(!permissionCheck)
                arrDeniedPermissions.add(permission);
        }
        ActivityCompat.requestPermissions(activity, arrDeniedPermissions.toArray(new String[arrDeniedPermissions.size()]), PERMISSION_CODE);
        return permissionCheck;
    }
}

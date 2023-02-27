package com.dalgurain.wherever;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class LocationTrackingActivity extends AppCompatActivity {
    private Button btnLocationTracking;
    private TextView txtLocationDetectResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_tracking);

        btnLocationTracking = findViewById(R.id.btnLocationTracking);
        txtLocationDetectResult = findViewById(R.id.locationResultTxt);

        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        btnLocationTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= 21 && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions( LocationTrackingActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 0 );
                }
                else {
                    Location locationInfo = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    Intent intent = new Intent(getApplicationContext(), RecordReportActivity.class);
                    if(locationInfo != null) {
                        String provider = locationInfo.getProvider();
                        double longitude = locationInfo.getLongitude();
                        double latitude = locationInfo.getLatitude();
                        double altitude = locationInfo.getAltitude();

                        intent.putExtra("longitude",longitude);
                        intent.putExtra("latitude",latitude);
                        intent.putExtra("altitude",altitude);

//                        txtLocationDetectResult.setText("위치정보 : " + provider + "\n" +
//                                "위도 : " + longitude + "\n" +
//                                "경도 : " + latitude + "\n" +
//                                "고도  : " + altitude);

                    }

//                    Toast.makeText(LocationTrackingActivity.this, "longitude", Toast.LENGTH_SHORT).show();
                    startActivity(intent);

                    // 위치정보를 원하는 시간, 거리마다 갱신해준다.
//                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
//                            1000,
//                            1,
//                            gpsLocationListener);
//                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//                            1000,
//                            1,
//                            gpsLocationListener);
                }
            }
        });
    }

    final LocationListener gpsLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            String provider = location.getProvider();  // 위치정보
            double longitude = location.getLongitude(); // 위도
            double latitude = location.getLatitude(); // 경도
            double altitude = location.getAltitude(); // 고도
           // txtLocationDetectResult.setText("위치정보 : " + provider + "\n" + "위도 : " + longitude + "\n" + "경도 : " + latitude + "\n" + "고도 : " + altitude);
        } public void onStatusChanged(String provider, int status, Bundle extras) {

        } public void onProviderEnabled(String provider) {

        } public void onProviderDisabled(String provider) {

        }
    };
}

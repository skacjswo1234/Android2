package com.koreait.examplelocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocationService();
            }
        });
        public void onRequestPermissionsResult(int requestCode, String permissions, int[] grantResults){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            AutoPermissions.Companion.loadAllPermissions(this, 101);
        }
        @Override
        public void onDenied (int requestCode, String[] permissions){
            Toast.makeText(this, "permissions denied", permissions.length, Toast.LENGTH_LONG).show();
        }
        @Override
        public void onGranted (int requestCode, String[] permissions){
            Toast.makeText(this, "permissions granted", permissions.length, Toast.LENGTH_LONG).show();
    }
}
    public void startLocationService() {
        // 1. 위치 관리자 불러오기.
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try{
            // 가장 마지막으로 확인했던 기기의 위치.
            // GPS_PROVIDER - GPS를 통해서 파악한 기기의 위치
            // NETWORK_PROVIDER - network를 통해서 파악한 기기의 위치
            // network를 사용해서 기기의 위치를 파악할 때는 IP, wifi 접속 내역, 통화 기지국의 위치.
            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location != null) {
                // (우리 앱 기준이 아닌 기기 자체의 기준)
                // 가장 마지막으로 확인했던 기기의 위치가 있을 때
                double latitude = location.getLatitude();
                double longtitude = location.getLongitude();

                String message = "최근 위치 -> Latitude : " + latitude + ", Longitude : " + longtitude;

                tv.setText(message);
            }

            GPSListener gpsListener = new GPSListener();
            long minTime = 10000;
            float minDistance = 0;

            // 3.위치 정보 업데이트 요청하기
            // minTime -> 갱신할 주기(밀리초)
            // minDistance -> 갱신할 거리의 주기
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);
            Toast.makeText(getApplicationContext(),"내 위치 확인 요청", Toast.LENGTH_LONG).show();
        } catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    // 2.위치 리스너 구현하기.
    class GPSListener implements LocationListener {
        // GPS를 사용할 수 없을 때 또는 NETWORK를 사용할 수 없을 때 처리를 하는 콜백 메서드
        // 일반적으로 오버라이딩만 해두고 자주 사용하지는 않음.
        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }

        // 위치 관리자가 위치를 알려줄 때 받아서 처리할 메서드
        @Override
        public void onLocationChanged(@NonNull Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            String message = "내 위치 -> Latitude : " + latitude + ", Longitude = " + longitude;


            tv.setText(message);
        }
    }
}
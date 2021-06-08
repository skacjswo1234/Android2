package com.koreait.examplelocationmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity {
    SupportMapFragment mapFragment;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // XML로 SupportMapFragment 타입의 fragment를 추가
        // SupportMapFragment 타입의 fragment 안에 GoogleMap 객체가 들어있음
        // GoogleMap 객체를 통해서 지도를 볼 수 있음

        // XML의 fragment를 불러와서
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        // fragment 내 지도를 준비하도록 오버라이딩
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GooleMap gooleMap) {
                // GoogleMap 객체를 초기화
                Log.d("Map","지도준비됨");
                map = gooleMap;
            }
        });

        MapsInitializer.initialize(this);

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startLocationService();
            }
        });
    }

    public void startLocationService(){
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            String message = "최근위치\n Latitude :" + latitude + "\n Longtitude :" + longitude;

            Log.d("Map",  message);
        }

        GPSListener gpsListener = new GPSListener();
        long minTime = 10000;
        float minDistance = 0;

        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);
    } catch(SecurityException e) {
        e.printStackTrace();
    }
}
    class GPSListener implements LocationListener{

        @Override
        public void onLocationChanged(@NonNull Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            String message = "내위치\n Latitude : " + latitude + "\n Longitude : " + longitude;
            Log.d("Map", message);

            showCurrentLocation(latitude,longitude);
        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }
    }

    private void showCurrentLocation(Double latitude, Double longitude) {
        // 현재 위도와 경도를 정보를 갖고 있는 LatLng 타입의 curpoint 객체생성
        LatLng curPoint = new LatLng(latitude, longitude);
        // 지정한 위치의 지도 영역을 보여주기
        // 두 번째ㅐ 매개변수 - 확대 정도(클수록 확대, 작을수록 축소)
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currPoint, 15));
    }
}
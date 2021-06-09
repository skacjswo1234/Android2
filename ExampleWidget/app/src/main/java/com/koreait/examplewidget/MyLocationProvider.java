package com.koreait.examplewidget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;

import java.util.List;

// MyLocationProvider 위젯 제공자 클래스 생성
// AppWidgetProvider 클래스를 상속 받아야 앱 위젯 제공자가 됨.
public class MyLocationProvider extends AppWidgetProvider {
    public static double ycoord = 0.0d;
    public static double xcoord = 0.0d;

    // onUpdate메서드 오버라이딩
    // 앱 위젯이 주기적으로 업데이트될 때마다 호출되는 콜백 메서드
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        Log.d("MyLocationProvider", "onUpdate() called : " + ycoord + ", " + xcoord);

        final int size = appWidgetIds.length;

        for(int i=0; i<size; i++) {
            int appWidgetId = appWidgetIds[i];

            // 지도를 띄우기 위한 URI 생성
            String uri = "get: "+ ycoord+",xcoord" +"?z=10";

            // 지도를 띄우기위한 인텐트 생성
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            // 지도를 띄우기 위해 펜딩 인텐트 생성(앱 위젯을 통해서 액티비티 또는 다른 앱을 실행시키기위한 인텐트)
            // 액티비티 또는 앱으로 전달할 데이터나 명령 등은 intent가 갖고있음.
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,0);

            // 앱 위젯에서 다른 앱에 있는 뷰에 접근하기 위해 RemoteViews 객체 생성
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.mylocation);
            // 앱 위젯이 클랙했을 때 실행할 인텐트 지정
            views.setOnClickPendingIntent(R.id.textView, pendingIntent);
            // 앱 위젯이 업데이트 주기가 되서 업데이트를 할 때 바로 위에서 설정한 앱 위젯 클릭 이벤트가 발생하도록 하는 설정
            appWidgetManager.updateAppWidget(appWidgetId, views);
        } // end for

        // 앱 위젯이 업데이트 주기가 될 때마다 GPS로 위치 확인.
        context.startService(new Intent(context, GPSLocationService.class));
    }

    public static class GPSLocationService extends Service {
        public static final String TAG = "GPSLocationService";

        private LocationManager manager;
        private LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Log.d(TAG, "onLocationChanged() 호출됨.");

                updateCoordinates(location.getLatitude(), location.getLongitude());

                stopSelf();
            }
        };

        public IBinder onBind(Intent intent) {
            return null;
        }

        public void onCreate() {
            super.onCreate();

            Log.d(TAG, "onCreate 호출됨.");

            manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startListening();

            return super.onStartCommand(intent, flags, startId);
        }

        @Override
        public void onDestroy() {
            stopListening();

            Log.d(TAG, "onDestory 호출됨");

            super.onDestroy();
        }

        private void startListening() {
            Log.d(TAG, "startListening 호출됨");

            final Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(true);
            criteria.setPowerRequirement(Criteria.POWER_LOW);

            final String bestProvider = manager.getBestProvider(criteria, true);
            if (bestProvider != null && bestProvider.length() > 0) {
                try {
                    manager.requestLocationUpdates(bestProvider, 500, 10, listener);
                } catch (SecurityException e) {

                } // end try
            } else {
                final List<String> providers = manager.getProviders(true);

                for (final String provider : providers) {
                    try {
                        manager.requestLocationUpdates(provider, 500, 10, listener);
                    } catch (SecurityException e) {

                    } // end try
                } // end for
            } // else
        }

        private void stopListening() {
            try {
                if (manager != null && listener != null) {
                    manager.removeUpdates(listener);
                }

                manager = null;
            } catch (final Exception ex) {

            }
        }

        private void updateCoordinates(double latitude, double longitude) {
            Geocoder coder = new Geocoder(this);
            List<Address> addresses = null;
            String info = "";

            Log.d(TAG, "updateCoordinates() called.");

            try {
                addresses = coder.getFromLocation(latitude, longitude, 2);

                if (null != addresses && addresses.size() > 0) {
                    int addressCount = addresses.get(0).getMaxAddressLineIndex();

                    if (-1 != addressCount) {
                        for (int index = 0; index <= addressCount; ++index) {
                            info += addresses.get(0).getAddressLine(index);

                            if (index < addressCount)
                                info += ", ";
                        }
                    } else {
                        info += addresses.get(0).getFeatureName() + ", "
                                + addresses.get(0).getSubAdminArea() + ", "
                                + addresses.get(0).getAdminArea();
                    }
                }

                Log.d(TAG, "Address : " + addresses.get(0).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            coder = null;
            addresses = null;

            if (info.length() <= 0) {
                info = "[내 위치] " + latitude + ", " + longitude
                        + "\n터치하면 지도로 볼 수 있습니다.";
            } else {
                info += ("\n" + "[내 위치] " + latitude + ", " + longitude + ")");
                info += "\n터치하면 지도로 볼 수 있습니다.";
            }

            RemoteViews views = new RemoteViews(getPackageName(), R.layout.mylocation);

            views.setTextViewText(R.id.textView, info);

            ComponentName thisWidget = new ComponentName(this, MyLocationProvider.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            manager.updateAppWidget(thisWidget, views);

            xcoord = longitude;
            ycoord = latitude;
            Log.d(TAG, "coordinates : " + latitude + ", " + longitude);

        }

    }


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

}



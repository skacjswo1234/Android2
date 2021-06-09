package com.koreait.examplenoti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    NotificationManager notiMng;

    private static String CHANNEL_ID = "channel1";
    private static String CHANNEL_NAME = "Channel1";

    private static String CHANNEL_ID2 = "channel2";
    private static String CHANNEL_NAME2 = "Channel2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showNoti1();

            }
        });
        Button btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showNoti2();


            }
        });
    }

    public void showNoti1() {
        notiMng = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notiMng.getNotificationChannel(CHANNEL_ID) == null) {
                notiMng.createNotificationChannel(
                        new NotificationChannel(
                                CHANNEL_ID,
                                CHANNEL_NAME,
                                NotificationManager.IMPORTANCE_DEFAULT
                        )
                );

                builder = new NotificationCompat.Builder(this, CHANNEL_ID);
            } else {
                builder = new NotificationCompat.Builder(this);
            } // end if

            builder.setContentTitle("간단한 알림");
            builder.setContentTitle("알림 메세지입니다.");
            builder.setSmallIcon(android.R.drawable.ic_menu_view);
            // 푸시 생성
            Notification noti = builder.build();

            // 푸시
            notiMng.notify(1, noti);

        } // end if
    } // end showNoti1

    public void showNoti2() {
        notiMng = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notiMng.getNotificationChannel(CHANNEL_ID2) == null) {
                notiMng.createNotificationChannel(
                        new NotificationChannel(
                                CHANNEL_ID2,
                                CHANNEL_NAME2,
                                NotificationManager.IMPORTANCE_DEFAULT
                        )
                );

                builder = new NotificationCompat.Builder(this, CHANNEL_ID2);
            } else {
                builder = new NotificationCompat.Builder(this);
            } // end if

            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,101,intent,PendingIntent.FLAG_UPDATE_CURRENT);

            // 푸시 메세지를 구성
            builder.setContentTitle("간단한 알림");
            builder.setContentTitle("알림 메세지입니다.");
            builder.setSmallIcon(android.R.drawable.ic_menu_view);
            // 상단 알림을 클릭햇을 대 알림을 지워라
            builder.setAutoCancel(true);
            // 상단 알림을 클릭했을 때 pendingIntent가 갖고 잇는 intent의 명령을 수행하도록
            builder.setContentIntent(pendingIntent);
            // 푸시 생성
            Notification noti = builder.build();

            // 푸시
            notiMng.notify(2, noti);

        } // end if
     } // end showNoti1
}
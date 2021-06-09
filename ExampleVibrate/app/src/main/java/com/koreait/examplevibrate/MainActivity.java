package com.koreait.examplevibrate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 =findViewById(R.id.button);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                if(Build.VERSION.SDK_INT >= 26) {
                    vibrator.vibrate(VibrationEffect.createOneShot(1000,10));
                } else {
                    vibrator.vibrate(1000);
                }
        }
    });

        Button btn2 =findViewById(R.id.button2);
        btn2.setOnClickListener(new  View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 소리로 알림을 하기 위한 URI(시스템에 설정되있는 기본 알림음)
                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                // 구현되어있는 resolver를 통해서 URI로 소리 알림을 요청하는 내용 제공자(Ringrone) 객체 생성
                Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), uri);
                ringtone.play();
            }
        });

        Button btn3 =findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //MediaPlayer player = MediaPlayer.create(getApplicationContext(), R.raw.beep);
                // play.start();
            }
        });
    }
}
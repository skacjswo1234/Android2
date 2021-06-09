package com.koreait.examplepush;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

// 푸시 메세지(서비스)가 왔을 때 처리할 서비스
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FMS";


    public MyFirebaseMessagingService() {
    }

    // Firebase 서버에 등록되었을 때 호출됨
    // token => 앱아이디(패키지)
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d(TAG,"onNewToken 호출됨." + token);
    }

    // 푸시 메세지가 사용자의 기기로오면 이 콜백 메서드가 호출됨.
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG,"onMessageReceived 호출됨." );

        // 관리자가 firebase FCM을 사용해서 메세지를 전송했을 때
        // 전송한 메세지 관련된 데이터들이 remoteMessage 매개변수에 들어있음

        // 푸시 메세지가 누구에게로 왔고
        String from = remoteMessage.getFrom();

        // 그 안에 들어있는 데이터가 어떤 것인지 꺼냄
        Map<String, String> data = remoteMessage.getData();
        // 데이터 안에 contents 이름으로 들어있는 데이터를 꺼냄
        String contents = data.get("contents");

        Log.d(TAG,"from : " + from + ", contents : " + contents);
        sendToActivity(getApplication(), from, contents);
    }

    private void sendToActivity(Context context, String from, String contents) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("contents", contents);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        context.startActivity(intent);
    }
}
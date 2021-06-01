package com.koreait.examlplenumber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

public class MainActivity extends AppCompatActivity
        implements AutoPermissionsListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // loadAllPermissions 메서드 : 매니페스트에 등록한 우리 앱에서 필요한 권한들을 사용자에게 요청하는 메서드
        // 1.activity : 권한 허용/거부 창을 어느 액티비티에 띄울지 지정하는 매개변수
        // 2. requestCode : 어느 권한 허용/거부를 했는지
        AutoPermissions.Companion.loadAllPermissions(this,101);
    }

    // 1. requestCode :
    // 2. permissions : 우리 앱에서 필요한 권한 목록
    // 3. grantResults : 우리 앱에서 필요한 권한을 사용자가 허용했는지 거부했는지 여부
    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this,requestCode,permissions,this);
    }

    // 사용자가 권한을 거부했을 때 실행되는 콜백 메서드
    // 1. requestCode :
    // 2. permissions : 거부한 권한의 목록
    @Override
    public void onDenied(int requestCode, String[] permissions) {

    }

    // 사용자가 권한을 허용했을 때 실행되는 콜백 메서드
    // 1. requestCode :
    // 2. permissions : 허용한 권한의 목록
    @Override
    public void onGranted(int requestCode, String[] permissions) {

    }
}
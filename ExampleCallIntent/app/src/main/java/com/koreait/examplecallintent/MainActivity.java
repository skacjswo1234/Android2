package com.koreait.examplecallintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.editTextTel);

    }

    public void onButton1Clicked(View v){
        // 입력상자(edit text)에 입력한 전화번호
        String data = et.getText().toString();

        //전화걸기 화면을 보여줄 인텐트 객체생성
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(data));
        // 인텐트를 사용한 액티비티를 띄우기
        startActivity(intent);
    }

    public void onButton2Clicked(View v){
        Intent intent = new Intent();
        ComponentName name = new ComponentName("com.koreait.examplecallintent","com.koreait.examplecallintent.MenuActivity");
        intent.setComponent(name);

        startActivityForResult(intent,101);
    }
    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);

        // 이메서드를 오버라이딩하면은
        // 현재 액티비티가
    }

}
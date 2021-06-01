package com.koreait.examlplenumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

// SmsReceiver 가 실행시켜주는 액티비티
// 문자메세지로 전달된 인증번호를 화면상에 출력할 액티비티
public class SmsActivity extends AppCompatActivity {
    EditText et;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        // activity_sms.xml로 구성한 인증번호를 입력하는 뷰
        et.findViewById(R.id.number);

        processIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processIntent(intent);

        super.onNewIntent(intent);
    }

    // 인텐트 안에 들어있는 number 이름의 값을 꺼내서
    // 인증번호를 입력하는 뷰에 설정
    private void processIntent(Intent intent) {
        if(intent != null){
            String number = intent.getStringExtra("number");

            et.setText(number);
        }
}
}
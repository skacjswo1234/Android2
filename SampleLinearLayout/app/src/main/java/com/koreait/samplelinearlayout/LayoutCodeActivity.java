package com.koreait.samplelinearlayout;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class LayoutCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // new  연산자를 사용해서 LinearLayout을 생성
        LinearLayout mainLayout = new LinearLayout(this);
        // setOrientation 메서드를 사용해서 이 레이아웃의 방향 설정
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        // new 연산자를 사용해서 레이아웃 안에 추가될
        // 뷰들에 설정할 파라미터(매개변수) 생성
        LinearLayout.LayoutParams params 
        = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        // NEW 연산자를 사용해서 버튼 생성
        Button button1 = new Button(this);
        // setText 메서드를 사용해서 버튼에 text인 contents를 추가
        button1.setText("Button1");
        // setLayoutParams 메서드를 사용해서 버튼의 속성을 설정
        button1.setLayoutParams(params);
        // addView 메서드를 사용해서 버튼을 레이아웃에 부착
        mainLayout.addView(button1);

        // 새로 만든 레이아웃을 화면에 설정정
       setContentView(R.layout.activity_main);
    }
}
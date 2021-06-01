package com.koreait.examplelayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Layout1 layout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Layout1 layout1 = findViewById(R.id.layout1); // XML 레이아웃에 추가한 뷰 참조하기.

        layout1.setImage(R.drawable.ic_launcher_foreground);
        layout1.setName("김민수");
        layout1.setMobile("010-0000-0000");
    }

    public void onButton1Clicked(View view) {
        layout1.setImage(R.drawable.profile1);
    }

    public void onButton2Clicked(View view) {
        layout1.setImage(R.drawable.profile2);
    }
}
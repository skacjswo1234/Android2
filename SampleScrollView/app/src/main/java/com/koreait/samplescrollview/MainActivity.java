package com.koreait.samplescrollview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    HorizontalScrollView h_scrollView;
    ImageView imageView;
    BitmapDrawable bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        h_scrollView = findViewById(R.id.h_scrollView);
        imageView = findViewById(R.id.imageView);
        h_scrollView.setVerticalScrollBarEnabled(true);

        Resource res = getResources();
        // 이미지 리소스 불러옴
        // 불러온 이미지의 너비 값
        // 불러온 이미지의 높이값

        // 이미지뷰에 이미지 리소스 출력
        // 이미지뷰의 너비값을 이미지의 너비값으로 설정
        // 이미지뷰의 높이값을 이미지의 높이값으로 설정
    }

    public void onButton1Clicked(View v) {
        Resources res = getResources();

        // 이미지 리소스(파일) 불러옴
        // 불러온 이미지의 너비 값
        // 불러온 이미지의 높이 값

        // 이미지뷰에 이미지 리소스 출력
        // 이미지뷰의 너비값을 이미지의 너비값으로 설정
        // 이미지뷰의 높이값을 이미지의 높이값으로 설정
    }
}
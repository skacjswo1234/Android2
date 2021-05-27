package com.koreait.framelayouttest;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView iv1;
    ImageView iv2;

    int imageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv1 = findViewById(R.id.imageView);
        iv2 = findViewById(R.id.imageView2);
    }

    public void onButton1Clicked(View v) {
        if(imageIndex == 0) {
            iv1.setVisibility(View.VISIBLE);
            iv2.setVisibility(View.INVISIBLE);

            imageIndex = 1;
        } else {
            iv1.setVisibility(View.INVISIBLE);
            iv2.setVisibility(View.VISIBLE);

            imageIndex = 0;
        }
    }
}
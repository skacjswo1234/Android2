package com.koreait.examplelayoutinflater;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick (View v){
            Toast.makeText(getApplicationContext(), "레이아웃 인플레이트 된 후", Toast.LENGTH_LONG).show();

        }
    });


    }
}
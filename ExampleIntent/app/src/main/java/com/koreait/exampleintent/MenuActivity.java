package com.koreait.exampleintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {

           @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name","mike");
                // 새로 띄운 액티비티에서 이전 액티비티로 돌아갈 때\
                // 인텐트를 전달 할 수 있음
               //setResult(응답코드,인텐트);
                setResult(RESULT_OK, intent);

                finish();
            }
        });
    }
}
package com.koreait.exampleintent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_MENU = 101;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivityForResult(intent, REQUEST_CODE_MENU);
            }
        });
    }

    // 이 액티비티에서 띄운 다른 액티비티가 실행되다가 이 액티비티로 돌아오면 실행 되는 메서드
    // requestCode - 되돌아온 액티비티를 식별 할 수 있는 번호
    // resultCode - 되돌아온 액티비티에서 전달해주는 응답 코드 (결괏값을 식별 할 수 있는 번호)
    // data - 되돌아온 액티비티에서 전달해주는 데이터나 수행해야할 기능을 갖고 있는 Intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == REQUEST_CODE_MENU){
        Toast.makeText(getApplicationContext(),"onActivity메서드 호출됨. 요청코드 : " + requestCode + ",결과코드 : " + resultCode,Toast.LENGTH_LONG).show();

                if(resultCode == RESULT_OK) {
                    String name = data.getStringExtra("name");
                Toast.makeText(getApplicationContext(),"응답으로 전돨된 name = " + name, Toast.LENGTH_LONG).show();
        }

    }
    }
}
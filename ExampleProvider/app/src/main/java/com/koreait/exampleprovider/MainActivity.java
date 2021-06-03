package com.koreait.exampleprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);

        Button btn1 = findViewById(R.id.button);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        Button btn2 = findViewById(R.id.button2);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

        Button btn3 = findViewById(R.id.button3);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

        Button btn4 = findViewById(R.id.button4);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    } // onCreate

    public void queryPerson(){

    }

    public void insertPerson() {
        println("insertPerson 호출됨");

        // 데이터를 제공 받을 앱의 URI
        String uriString = "content://com.koreait.provider/person";
        Uri uri = new Uri.Builder().build().parse(uriString);

        // 다른 앱에 있는 데이터를 사용하기 위해서 조회
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        // 칼럼의 이름들을 저장
        String[] columns = cursor.getColumnNames();

        // 조회된 데이터의 개수 출력
        println("columns count ->" + columns.length);
        for(int i=0; i<columns.length; i++) {
            // 조회된 데이터의 처음부터 끝까지 하나씩 접근해서 출력
            println("#" + i + " : " + columns[i]);
        }

        // 다른 앱에 데이터를 넣기 위한 객체
        ContentValues values = new ContentValues();
        // name -> 칼럼의 이름
        // john -> 칼럼의 값
        values.put("name","john");
        values.put("age","20");
        values.put("mobile","010-1000-1000");

        uri = getContentResolver().insert(uri, values);
    }

    public void println(String message) {
        tv.append(message + "\n");
    }
}
package com.koreait.exampledatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText et1;
    EditText et2;
    TextView tv1;

    DatabaseHelper dbHelper;
    SQLiteDatabase database;

    String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.editText);
        et2 = findViewById(R.id.editText2);
        tv1 = findViewById(R.id.textView);

        Button btn1 = findViewById(R.id.button);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String databaseName = et1.getText().toString();
                createDatabase(databaseName);
            }
        });

        Button btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                tableName = et2.getText().toString();
                createTable(tableName);

                insertRecord();
            }
        });

        Button btn3 = findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                executeQuery();
            }
        });
    }

    private void executeQuery(){
        println("executeQuery 호출됨");

        Cursor cursor = database.rawQuery("SELECT _id, name, age, mobile form emp", null);
        int recordCount = cursor.getCount();
        println("레코드 개수 : " + recordCount);

        for(int i=0; i<recordCount; i++) {
            cursor.moveToNext();

            int _id = cursor.getInt(0);
            String name = cursor.getString(1);
            int age = cursor.getInt(2);
            String mobile = cursor.getString(3);

            println("레코드 #" + i + " : " + _id + ", " + name + ", " + age + ", " + mobile);
        }
    }

    private void createDatabase(String name) {
        println("createDatabase 호출됨");

        dbHelper = new DatabaseHelper(this);

        // 데이터베이스 생성
        database = dbHelper.getWritableDatabase();

        println("createDatabase 호출 완료");
    }

    private void createTable(String name) {
        println("createTable 호출됨");

        if(database == null) {
            println("데이터베이스를 먼저 생성하세요.");

            return;
        }

        database.execSQL("create table if not exists " + name + "("
                + "_id integer PRIMARY KEY autoincrement,"
                + " name text,"
                + "age integer,"
                + "mobile text)");

        println("테이블 생성 완료");
    }

    private void insertRecord() {
        println("insertRecord 호출됨");
        if(database == null) {
            println("데이터베이스를 먼저 생성하세요");
            // 메서드를 여기서 종료해라
            // 반환 타입이 있다면 return 데이터; 반환을 해서
            // 메서드를 끝낼수 있지만
            // 반환 타입이 없는 void 메서드의 경우에는
            // 메서드의 끝을 만나야지 끝이남.
            // 반환 타입이 void인 메서드에서만 return ; 이 코드를 사용하면\
            // 내가 원하는 시점에 메서드를 끝낼 수 있음.
            return;
        }
        if(tableName == null) {
            println("테이블을 먼저 생성하세요");
            return;
        }

        database.execSQL("insert into" + tableName + "(name, age, mobile) VALUES ('John', 20, '010-1234-5678')");

        println("레코드 추가 완료");
    }

    private void println(String message) {
        tv1.append(message + "\n");
    }
}
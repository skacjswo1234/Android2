package com.koreait.examplelifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Main","onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("Main","onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("Main","onStop");
    }

    // 이앱이 화면에 보일때 항상 실행되는 메서드
    @Override
    protected void onResume() {
        super.onResume();

        Log.d("Main","onResume");

        SharedPreferences pref = getSharedPreferences("pref",Activity.MODE_PRIVATE);
        if((pref != null) && (pref.contains("name"))) {
            String name = pref.getString("name", "");
            et.setText(name);
        }
    }

    // 이앱이 화면에 보이지 않을때 항상 실행되는 메서드
    @Override
    protected void onPause() {
        super.onPause();

        Log.d("Main","onPause");

        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("name", et.getText().toString());
        editor.commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("Main","onDestroy");
    }
}
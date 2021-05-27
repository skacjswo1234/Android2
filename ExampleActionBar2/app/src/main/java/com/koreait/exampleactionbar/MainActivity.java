package com.koreait.exampleactionbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // menu_main.xml로 정의된 메뉴 정보를 인플레이션하여 메모리에 로드
        getMenuInflater().inflate(R.menu.menu_main,menu);

        // 메뉴 아이템 중에서 검색을 위해 정의한 아이템을 뷰 객체로 가져옴
        View v = menu.findItem(R.id.menu_search).getActionView();
        if(v != null) {
            // 검색 위한 메뉴 아이템 안에 정의한 입력상자(EditText) 객체를 가져옴
            EditText et = v.findViewById(R.id.editText);

            if(et != null) {
                et.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        Toast.makeText(getApplicationContext(), "입력", Toast.LENGTH_SHORT).show();

                        return true;
                    }
                });
            } // end if
        } // end if

        return true;
    }
}
package com.koreait.examplespinner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv;

    String[] items = {"이름을 선택하세요.", "mike", "angel", "crow", "john", "ginnie", "sally", "cohen", "rice"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);

        Spinner spinner = findViewById(R.id.spinner);
        // 두 번째 파라미터는 사용할 XML 레이아웃의 리소스 ID 값값
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 어댑터 설정 (스피너도 선택위젯을 분류가 되므로 리싸이클러뷰처럼 어댑터를 설정해서 원본 데이터인 아이템을 관리하고 각 각의 아이템을 보여주는 역할)
        // 리싸이클러뷰에서는 어댑터를 직접 구현했는데 스피너에서는 어댑터를 직접 구현하지 않았음
        // 리싸이클러뷰를 위한 어댑터는 기본적으로 제공되지 않아서 직접 구현
        // 스피너를 위한 댑터는 기본적으로 제공되어서 제공된 어댑터를 사용
        spinner.setAdapter(adapter);

        // 옵션(아이템) 중에 하나가 선택됫을 때 동작할 메서드를 오버라이딩
        // 옵션이 선택되지 않았을 때 동작할 메서드를 오버라이딩
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 스피너의 한 아이템을 선택했을 때 선택한 값을 보여주는 동작
                tv.setText(items[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tv.setText("");
            }
        });
    }
}

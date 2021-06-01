package com.koreait.examplerecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        );
       // 레이아웃 매니저를 설정 -> 리싸이클러뷰가 보일 형태를 설정
       // - 세로방향, 가로방향, 격자모양으로 형태를 지정
        recyclerView.setLayoutManager(layoutManager);

        // 원본데이터를 관리하면서 각각의 아이템이 리싸이클러뷰에서 보이도록
        // 하는 adapter 생성
        PersonAdapter adapter = new PersonAdapter();
        adapter.addItem(new Person("김민수", "010-1234-5678"));
        adapter.addItem(new Person("김하늘", "010-5678-9012"));
        adapter.addItem(new Person("홍길동", "010-3456-7890"));
        adapter.addItem(new Person("김민수", "010-1234-5678"));
        adapter.addItem(new Person("김하늘", "010-5678-9012"));
        adapter.addItem(new Person("홍길동", "010-3456-7890"));
        adapter.addItem(new Person("김하늘", "010-5678-9012"));
        adapter.addItem(new Person("홍길동", "010-3456-7890"));
        adapter.addItem(new Person("김하늘", "010-5678-9012"));
        adapter.addItem(new Person("홍길동", "010-3456-7890"));
        adapter.addItem(new Person("김하늘", "010-5678-9012"));
        adapter.addItem(new Person("홍길동", "010-3456-7890"));
        adapter.addItem(new Person("김하늘", "010-5678-9012"));
        adapter.addItem(new Person("홍길동", "010-3456-7890"));
        adapter.addItem(new Person("김하늘", "010-5678-9012"));
        adapter.addItem(new Person("홍길동", "010-3456-7890"));

        recyclerView.setAdapter(adapter);
    }
}
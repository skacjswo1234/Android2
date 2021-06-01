package com.koreait.examplelayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class Layout1 extends LinearLayout {
    ImageView iv;
    TextView tv1;
    TextView tv2;

    public Layout1(Context context) {
        super(context);

        init(context);
    }

    public Layout1(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        // 레이아웃 인플레이터를 사용해서 layout1.xml을 인플레이션
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout1,this,true);

        // XML 레이아웃에서 정의했던 뷰 객체들을 가져옴
        iv = findViewById(R.id.imageView);
        tv1 = findViewById(R.id.textView);
        tv2 = findViewById(R.id.textView2);
    }

    // 아래 세 setter를 사용해서 뷰 객체에 적절한 데이터들을 설정
    // 예) 우리가 drawable house.png 를 추가했다면 R.drawable.house를 사용해서
    // house.png를 자바 코드에서 사용할 수 있음
    // R.drawable.house는 house.png를 인식할 수 있는 숫자로된 ID
    public void setImage(int resId) {
        iv.setImageResource(resId);
    }

    public void setName(String name) {
        tv1.setText(name);
    }
    public void setMobile(String mobile) {
        tv2.setText(mobile);
    }
}

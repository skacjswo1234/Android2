package com.koreait.exampleview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

// 나만의 버튼을 만들기 위한 클래스
// API 뷰 -> AppCompatButton
public class MyButton extends AppCompatButton {

    // 이 버튼을 자바 코드에서 추가하려고 할때는
    // 이 버튼 객체를 만들어야하므로
    // 자바코드에서 버튼 객체를 만들 때 사용하는 생성자
    public MyButton(Context context) {
        super(context);
        init(context);
    }

    // Context -> 이 버튼이 그려질 액티비티의 정보
    // AttributeSet -> XML레이아웃에서 태그에 추가하는 속성을 전달 받기 위한 역할
    public MyButton(Context context, AttributeSet attrs) {
        super(context);
        init(context);
    }

    // 생성자가 두 개이므로 생성자에서 초기화해야할 멤버 변수를
    // init 메서드에서 초기화하고
    // 두 개의 생성자에서는 init 메서드를 호출해서 사용
    private void init(Context context) {
        setBackground(Color.CYAN);
        setTextColor(Color.BLACK);

        // 픽셀 단위의 텍스트 사이즈를 설정 할 수 있음
        float textSize = getResources().getDimension(R.dimen.text_size);
        setTextSize(textSize);

        // sp단위의 텍스트사이즈를 설정하려면 XML 파일을 사용해야함.
    }

    private void setBackground(int cyan) {
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d("MyButton", "onDraw");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("MyButton","onTouch");

                int action = event.getAction();
        switch(action) {
            case MotionEvent.ACTION_DOWN:
                setBackgroundColor((Color.BLUE));
                setTextColor(Color.RED);
                break;
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                setBackgroundColor((Color.CYAN));
                setTextColor(Color.BLACK);
                break;
        }

        invalidate();

        return true;
    }
}
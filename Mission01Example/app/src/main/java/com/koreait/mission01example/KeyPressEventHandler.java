package com.koreait.mission01example;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

public class KeyPressEventHandler implements TextWatcher {
    private TextView counter;

    public KeyPressEventHandler(TextView counter) {
        this.counter = counter;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // 우리가 입력한 키가 화면에 보이기 전에 실행되는 메서드
    }

    // s -> 화면에 보이는 문자를 전달해주는 객체
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // 우리가 입력한 키가 화면에 보인 직후에 실행되는 메서드
        System.out.println("입력된 문자의 바이트 수를 세는 코드");
        try{
            // s.toString() -> 화면에 보이는 문자를 문자열로 변환
            // String.getBytes() -> 문자열을 바이트로 변환 / 영문은 1바이트, 한글은 2바이트
            // String.getBytes("인코딩이름") -> 문자열이 "인코딩이름" 으로 인코딩 되어있으니
            //                                  디코딩도 똑같은 방법으로 디코딩을해라
            byte[] bytes = s.toString().getBytes("KSC5601");
            // bytes.length -> 화면에 보이는 문자열의 바이트 수를 세는 코드
            int strCount = bytes.length;

            System.out.println("센 바이트 수를 textView에 출력하는 코드");
            // counter 객체가 가리키는 뷰의 text 속성을 아래와 같이 설정
            counter.setText(strCount + " / 80 바이트");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void afterTextChanged(Editable s) {
    // 우리가 입력한 키가 화면에 보인 직후에 실행되는 메서드
        // onTextChanged 메서드 후에 실행되는 메서드
    }
}

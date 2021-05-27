package com.koreait.examplelifecycle;

import androidx.fragment.app.Fragment;


// 부모 클래스인 Fragment에는 setContentView 메서드가 없음
// 그래서 Activity처럼 레이아웃을 연결할 수 없음
public class MainFragment extends Fragment {
    // 프래그먼트르를 위한 XML 파일을 인플레이션한 뒤
    // onCreateView 메서드를 통해서 인플레이션된 프래그 먼트의 화면을
    // 사용하도록 한다.
    // onCreateView 메서드는 콜백 메서드
    // onCreateView 메서드 안에 XML파일을 인플레이션하는 코드를 작성해야한다.
    // inflate() -> XML파일을 인플레이션하는 코드
}

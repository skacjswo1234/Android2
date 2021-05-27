package com.koreait.examplefragment2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ListFragment extends Fragment {
    public static interface  ImageSelectionCallback {
        public void onImageSelected(int position);
    }

    public ImageSelectionCallback callback;

    // 이 프래그먼트 생명주기에 관여하는 onAttach 콜백 메서드가 실행되는 상황
    // -프래그먼트가 액티비티와 연결될 때 호출됨
    // -액티비티에 프래그먼트를 추가했을 때 onAttach -> onCreate -> onCreateView -> ...
    @Override
    public void onAttach( Context context) {
        super.onAttach(context);

        if(context instanceof ImageSelectionCallback) {
            callback = (ImageSelectionCallback) context;
        }
    }

    @Nullable

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_list,container, false);
        Button btn1 = rootView.findViewById(R.id.button);
        Button btn2 = rootView.findViewById(R.id.button2);
        Button btn3 = rootView.findViewById(R.id.button3);

        //첫번 째 이미지 버튼을 눌렀을 때
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(callback != null) {
                    callback.onImageSelected(0);
                }
            }
        });

        //두번 째 이미지 버튼을 눌렀을 때
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(callback != null) {
                    callback.onImageSelected(1);
                }
            }
        });

        //세번 째 이미지 버튼을 눌렀을 때
        btn3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(callback != null) {
                    callback.onImageSelected(2);
                }
            }
        });

        return rootView;
    }
}

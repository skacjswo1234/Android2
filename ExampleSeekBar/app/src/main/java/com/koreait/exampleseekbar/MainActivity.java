package com.koreait.exampleseekbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);

        SeekBar sb = findViewById(R.id.seekBar);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            // SeekBar -> 드래그를 한 시크바
            // progress -> 드래그를 해서 옮긴 시크바의 값
            // fromUser -> 사용자가 직접 시크바를 옮긴건지 여부
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setBrightness(progress);
                tv.setText("변경된 값 : " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    } // end onCreate

    private void setBrightness(int value) {
        if(value < 10) {
            value = 10;
        } else if(value > 100) {
            value = 100;
        }

        // 핸드폰 화면과 관련된 설정을 할 수 있는 객체를 생성
        WindowManager.LayoutParams params = getWindow().getAttributes();

        params.screenBrightness = (float) value / 100;
        getWindow().setAttributes(params);
    }
}
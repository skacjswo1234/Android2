package com.koreait.examplehttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText et;
    TextView tv;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.editText);
        tv = findViewById(R.id.textView);

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String urlStr = et.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        request(urlStr);
                    }
                }).start();
            }
        });
    }

    public void request(String urlStr) {
        StringBuilder output = new StringBuilder();
        try {
            URL url = new URL(urlStr);

            url.openConnection();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if(conn != null) {
                // 서버가 응답하기까지 클라이언트가 기다리는 시간
                conn.setConnectTimeout(10000);
                // 서버로 요청하는 방법
                conn.setRequestMethod("GET");
                // 요청 파라미터를 보낼 수 있도록 설정
                conn.setDoInput(true);

                // HTTP_OK -> 정상
                // HTTP_NOT_FOUND -> URL이 잘못되서 요청을 하지 못했을 때
                int resCode = conn.getResponseCode();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = null;

                while(true) {
                    line = reader.readLine();
                    if(line == null) {
                        break;
                    }

                    output.append(line + "\n");
                }
                reader.close();
                conn.disconnect();
            }
        } catch (Exception e) {

        }

        Log.d("request", output.toString());
    }
}
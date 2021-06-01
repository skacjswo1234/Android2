package com.koreait.examplewebview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText et;
    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 사용자가 URL을 입력하는 뷰
        et = findViewById(R.id.editText);
        // 사용자가 입력한 URL의 웹페이지를 보여줄 뷰
        wv = findViewById(R.id.webView);

        // 웹뷰와 관련된 설정 객체
        WebSettings webSettings = wv.getSettings();
        // 웹뷰에서 웹 페이지를 보여줄 때 웹 페이지에 자바스크립트가 사용됬다면
        // 그 자바스크립트를 실행시킬것인지 여부
        webSettings.setJavaScriptEnabled(true);

        // 웹 뷰에서 사용할 뷰를 설정
        wv.setWebViewClient(new WebViewClient());

        // [ 열기 ] 버튼
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(android.view.View v) {
                wv.loadUrl(et.getText().toString());
            }
        });
    } // end onCreate

    private class ViewClient extends  WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, final String request) {
            view.loadUrl(request);

            return true;
        }
    }
}
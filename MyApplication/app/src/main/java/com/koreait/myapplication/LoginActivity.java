package com.koreait.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import util.ToastUtil;

public class LoginActivity extends AppCompatActivity {
    private static RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(rq == null) {
            Volley.newRequestQueue(this);
        }
    }

    public void clickLoginBtn(View v) {
        EditText idEditText = findViewById(R.id.ideditText);
        EditText pwEditText = findViewById(R.id.pweditText);

        String id =idEditText.getText().toString();
        String pw = pwEditText.getText().toString();

        if(id.trim().length() == 0 || pw.trim().length() == 0) {
            ToastUtil.showShort(this,"아이디 또는 비밀번호가 비어있습니다.");
            return;
        } else if(id.length() > 20 ) {
            ToastUtil.showShort(this,"아이디는 20자 이하입니다.");
            return;
        } else if(pw.length() > 16) {
            ToastUtil.showShort(this,"비밀번호는 16자 이하입니다.");
            return;
        }

        // 아이디, 비밀번호를 정상적으로 입력했을 때
        StringRequest request = new StringRequest(
                Request.Method.POST,
                "http:// 192.168.2.15:8081/member/login",
                // 성공했을 때 이벤트 리스너,
                // 실패했을 때 이벤트 리스너
        );
    }

    // 로그인이 성공했을 때를 처리할 이벤트 리스너 클래스
    private class LoginSuccessListener implements Response.Listener<String> {
        @Override
        public void onResponse(String response) {
            ToastUtil.showLong(getApplicationContext(),"로그인이 되었습니다.");

            finish();
        }
    }
    private class LoginFailureListener implements Response.ErrorListener{
        @Override
        public void onErrorResponse(VolleyError error) {
            // 응답코드
            int statusCode = error.networkResponse.statusCode;

            if(statusCode == 400) {
                // 응답 코드가 400일 때
                ToastUtil.showShort(getApplicationContext(),"잘못된 값을 입력하였습니다.");
            } else if(statusCode == 404) {
                // 응답 코드가 404일 때
                ToastUtil.showShort(getApplicationContext(),"아이디 또는 비밀번호가 올바르지 않습니다.");
            } else {
                // 응답 코드가 500일 때
                ToastUtil.showShort(getApplicationContext(),"잠시 후 다시 시도해주세요.");
            }



        }
    }
}








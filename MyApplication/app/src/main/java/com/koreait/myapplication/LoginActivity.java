package com.koreait.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import util.ToastUtil;

public class LoginActivity extends AppCompatActivity {
    private static RequestQueue rq;

    private int statusCode;

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
                "http://192.168.2.15:8081/member/login",
                new LoginSuccessListener(),
                new LoginFailureListener()
        ) {
        protected Map<String, String> getParams()throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();

            params.put("id", id);
            params.put("pw", pw);

            return params;
        }

            // 요청을 했을 때 성공을 했다. 이때 성공에 관련된 status code가 여러개 일 수 있음.
            // 회원가입
            // 1. 이전에 가입 했던 정보 그대로 다시 회원 가입을 시도 - 200
            // 2. 새롭게 회원 가입을 시도한다. - 201
            // 3. 휴면 계정의 정보를 그대로 사용해서 회원 가입을 시도 - 202
            // 4. 탈퇴한 계정의 정보를 그대로 사용해서 회원 가입을 시도 - 203

            // 응답 코드, 결괏값 이러한 것들을 우리 앱에 맞게 변환시켜주는 역할
            // 요청이 성공했을 때(100,200,300번대 응답코드)
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
            if(response == null) {
                // 요청이 성공했을 때 응답 코드가 몇번인지 저장
                statusCode = response.statusCode;
            }
                return super.parseNetworkResponse(response);
            }
        };

        request.setShouldCache(false);

        if(rq == null) {
            rq = Volley.newRequestQueue(this);
        }

    }


    // 로그인이 성공했을 때를 처리할 이벤트 리스너 클래스
    private class LoginSuccessListener implements Response.Listener<String> {
        @Override
        public void onResponse(String response) {
            ToastUtil.showLong(getApplicationContext(),"로그인이 되었습니다.");

            // 일반적으로 앱은 설치를 하고 로그인을 하지 않았을 때만 로그인을 해야하고
            // 로그인을 한번 하고 나면 지우기 전까지는 로그인이 풀리지 않음
            // 로그인을 한번하고 난 다음에 지우기 전까지 로그인이 풀리지 않도록 하려면 ?
            // hint 앱의 데이터베이스를 활용해서 로그인한 사용자의 정보를 앱에서 저장해둔다.

            // 위의 문제를 해결하고 나면 회원 정보를 수정했을 때 앱의 데이터베이스 내 사용자 정보를 갱신해줘야함.
            // 이렇게 구성을 하고나면 서버의 DB에서 사용자의 정보는 앱을 지우고 다시 설치했을 때 로그인을 하면
            // 사용자의 정보를 전달해주는 용도가 될 것.

            // 앱의 DB랑 서버의 DB랑 사용자의 정보가 달라질수있음.
            // 같은 사용자지만 앱 내 저장된 사용자의 정보랑 서버에 저장된 사용자의 정보가 달라질 수 있음.
            // EX) 앱 개발자가 사용자가 정보를 수정했을 때 앱 DB 내 사용자 정보는 수정했는데 서버의 회원 정보 수정 API를 호출하지 않았다던지
            //     서버의 회원 정보 수정 API는 호출 했는데 앱 내 사용자 정보를 수정하지 않았다던지 하는 등
            // 위와 같이 구성을 하면 주기적으로 서버와 앱 내 사용자 정보를 대조해서 동일하게 맞추는 동작도 필요해짐 / 일명 동기화.

            Log.d("login","응답 코드 : " + statusCode);
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








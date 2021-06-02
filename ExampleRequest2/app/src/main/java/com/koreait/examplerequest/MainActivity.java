package com.koreait.examplerequest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText et;
    TextView tv;

    //요청 큐는 한번만 만들고 사용
    static RequestQueue requestQueue;

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
                makeRequest();
            }
        });

        if(requestQueue == null) {
            // 요청 큐가 만들어지지 않았다면 요청 큐를 만들어라
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }

    public void makeRequest(){
        String url = et.getText().toString();

        // 첫 번째 매개변수 -> 요청방식
        // 두 번째 매개변수 -> 요청URL
        // 세 번째 매개변수 -> 성공적으로 응답이 왔을 대 호출될 콜백 메서드를 갖고 있는 리스너
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            tv.append(response);
        }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv.append(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                return super.getParams();
            }
        };

        // 요청 객체 안에 cache 기능이 내장되어있음.
        // cache - 응답 결과를 빠르게 보여주기 위해서 가장 마지막에 받았던 응답 결과를 저장해두는 곳
        // request.setShouldCache(false) -> 내장되어있는 캐시 기능을 사용하지 않겠다.
        // 내장되어있는 캐시기능을 사용하지 않겠다 -> 매번 새로운 결괏값으 받아서 보여주겠다.
        request.setShouldCache(false);
        requestQueue.add(request);
        tv.append("요청 보냄\n");
    }

    public void processResponse(String response) {
        Gson gson = new Gson();
        MovieList movieList = gson.fromJson(response, MovieList.class);
        Log.d("tag", "전달 받은 영화 정보의 수 :" + movieList.boxOfficeResult.dailyBoxOfficeList.size());
    }
}
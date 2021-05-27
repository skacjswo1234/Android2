package com.koreait.exampleextra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        tv = findViewById(R.id.textView);
        Button btn = findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name","mike");
                setResult(RESULT_OK, intent);

                finish();
            } // end onClick
        }); // end setOnClick

        Intent intent = getIntent();
        processIntent(intent);
    } // end onCreate

    private void processIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        SimpleData data = bundle.getParcelable("data");
        if(data != null) {
            tv.setText("전달 받은 데이터\nNumber = " + data.number + "\nMessage =" + data.message);
        }

    }
}
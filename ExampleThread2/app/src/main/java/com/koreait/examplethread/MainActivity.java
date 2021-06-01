package com.koreait.examplethread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
 
    Handler handler = new Handler();

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);


        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackgroundThread thread = new BackgroundThread();
                thread.start();
            }
        });

        handler = new Handler();

    }

    class BackgroundThread extends  Thread {
        int value = 0;

        @Override
        public void run() {
            for (int i=0; i<100; i++) {
                try{
                    Thread.sleep(1000);

          
                } catch(Exception e){
                    e.printStackTrace();
                } // end try



                value += 1;
                Log.d("Thread","value :" + value);

             handler.post(new Runnable() {
                 @Override
                 public void run() {
                     tv.setText("value 값 : " + value);
                 }
             });
            } // end for
        } // end run
    } // end backgr
}
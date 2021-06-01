package com.koreait.examplereceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class smsActivity extends AppCompatActivity {
    EditText sender;
    EditText content;
    EditText receiveDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        sender = findViewById(R.id.sender);
        content = findViewById(R.id.content);
        receiveDate = findViewById(R.id.receiveDate);

        processIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processIntent(intent);

        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent) {
        if(intent != null) {
            String sender_text = intent.getStringExtra("sender");
            String content_text = intent.getStringExtra("content");
            String receiveDate_text = intent.getStringExtra("receiveDate");

            sender.setText(sender_text);
            content.setText(content_text);
            receiveDate.setText(receiveDate_text);
        }
    }
}
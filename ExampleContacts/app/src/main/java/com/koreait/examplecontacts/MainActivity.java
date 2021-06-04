package com.koreait.examplecontacts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {
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
                chooseContacts();
            }
        });

        AutoPermissions.Companion.loadAllPermissions(this,101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this,requestCode, permissions, this);
    }

    @Override
    public void onDenied(int requestCode, String[] strings) {
        // 권한을 거절 했을 때
    }
    @Override
    public void onGranted(int requestCode, String[] permissions) {
        // 권한을 승낙 했을 때
        Toast.makeText(this,"granted : " + permissions.length, Toast.LENGTH_LONG).show();
    }
    public void chooseContacts() {
        // 연락처 화면을 띄우기 위한 인텐트 생성
        Intent contactsPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);

        startActivityForResult(contactsPickerIntent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101) {
            if(resultCode == RESULT_OK) {
                // 연락처 앱을 통해서 선택한 연락처의 URI
                Uri conntractsUri = data.getData();
                // 선택한 연락처의 id값.
                String id = conntractsUri.getLastPathSegment();

                getContracts(id);
            }
        }
    }

    public void println(String data) {
        tv.append(data + "\n");
    }

    public void getContracts(String id) {
        Cursor cursor = null;
        String name = "";

        cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                null, ContactsContract.Data.CONTACT_ID + " =? ",
                new String[] {id},
                null);

        if(cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME);
            // 연락처에 등록된 이름
            name = cursor.getString(nameColumnIndex);
            println("Name : " + name);

            String columns[] = cursor.getColumnNames();
            for(String column : columns) {
                int index = cursor.getColumnIndex(column);
                String columnOutput = ("#" + index + "-> [" + column + " ] " + cursor.getString(index));
                println(columnOutput);
            } // end for

            cursor.close();
        } // end if
    }
}
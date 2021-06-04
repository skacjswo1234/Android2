package com.koreait.examplealbum;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    ImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = findViewById(R.id.imageView);

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    public void openGallery(){
        Intent intent = new Intent();
        // 인텐트의 행동설정
        // ACTION_GET_CONTENT : 데이터를 가져와라
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // 모든 형태의 이미지를 가져오도록 지정
        // 이미지확장자 : JPG, PNG, GIF, ...
        intent.setType("image/*");

        // 앨범에서 사진(이미지)를 선택할 수 있는 화면이 띄워짐.
        startActivityForResult(intent, 101);
    }

    // 콜백 메서드 :
    // 콜백 : 예전에 국제전화를 할 때 상대방에게 나한테 전화좀 해줘 하고 끊으면은 상대방이 전화를 해줬던
    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101) {
            if(resultCode == RESULT_OK) {
                // 이미지의 URI(경로)
                Uri fileUri = data.getData();

                ContentResolver resolver = getContentResolver();

                 try{
                   // 이미지 경로에 데이터를 제공해주는 내용 제공자를 사용해서
                   // 이미지를 가져올 수 있도록 스트림을 열었음.
                  InputStream is = resolver.openInputStream(fileUri);
                  // InputStream과 연결된 이미지 파일을 Bitmap 객체로 변환
                  Bitmap imgBitmap = BitmapFactory.decodeStream(is);
                  // 앨범에서 선택한 이미지를 이미지뷰에 출력
                  iv.setImageBitmap(imgBitmap);

                  is.close();
              } catch(Exception e){
                  e.printStackTrace();
              }

            } // end if
        } // end if
    }
}
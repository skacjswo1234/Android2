package com.tistory.massivcode.memo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tistory.massivcode.memo.models.Memo;

@SuppressWarnings("ConstantConditions")
public class ViewerActivity extends AppCompatActivity implements View.OnClickListener {
    // MainActivity 로부터 넘겨받은 메모 객체
    private Memo mMemo;
    // 0 : 뷰어 모드, 1 : 메모 추가 모드, 2 : 메모 수정 모드
    private int mMode;
    // 제목, 내용을 담당하는 EditText
    private EditText mTitleEditText, mContentsEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);

        receiveData();
        initViews();
    }

    /**
     * 모드 설정을 위해 MainActivity 로부터 정보를 얻음
     */
    private void receiveData() {
        Intent intent = getIntent();

        if (intent != null) {
            mMemo = (Memo) intent.getSerializableExtra("memo");
            mMode = intent.getIntExtra("mode", 0);
        }
    }

    /**
     * 뷰 초기화
     */
    private void initViews() {
        mTitleEditText = (EditText) findViewById(R.id.viewer_title_et);
        mContentsEditText = (EditText) findViewById(R.id.viewer_contents_et);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.viewer_save_fab);

        switch (mMode) {
            // 뷰어모드 일떄는 EditText 를 TextView 처럼 활용
            case 0:
                mTitleEditText.setText(mMemo.getTitle());
                mContentsEditText.setText(mMemo.getContents());
                mTitleEditText.setFocusable(false);
                mContentsEditText.setFocusable(false);
                mTitleEditText.setCursorVisible(false);
                mContentsEditText.setCursorVisible(false);
                floatingActionButton.setVisibility(View.GONE);
                break;
            // 추가모드 일때는 클릭 리스너만 세팅
            case 1:
                floatingActionButton.setOnClickListener(this);
                break;
            // 수정모드 일때는 기존 메모 값을 뷰에 세팅
            case 2:
                mTitleEditText.setText(mMemo.getTitle());
                mContentsEditText.setText(mMemo.getContents());
                floatingActionButton.setOnClickListener(this);
                break;
        }
    }

    // 뒤로가기 버튼을 눌렀을 때 result 세팅
    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    // 메모 추가 또는 수정 모드일 때 버튼 클릭시 메모 내용 체크 후 MainActivity 로 전달
    @Override
    public void onClick(View v) {
        String title = mTitleEditText.getText().toString();

        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "제목을 입력해 주세요!", Toast.LENGTH_SHORT).show();
            return;
        }

        String contents = mContentsEditText.getText().toString();

        if (TextUtils.isEmpty(contents)) {
            Toast.makeText(this, "내용을 입력해주세요!", Toast.LENGTH_SHORT).show();
        }

        mMemo.setTitle(title);
        mMemo.setContents(contents);
        mMemo.setTime(System.currentTimeMillis());


        setResult(RESULT_OK, new Intent().putExtra("memo", mMemo).putExtra("mode", mMode));
        finish();
    }
}

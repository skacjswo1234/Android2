package com.tistory.massivcode.memo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tistory.massivcode.memo.adapters.MemoAdapter;
import com.tistory.massivcode.memo.models.Memo;
import com.tistory.massivcode.memo.models.MemoDAO;

import java.util.ArrayList;

/**
 * Created by massivcode@gmail.com on 2017. 1. 3..
 */
@SuppressWarnings("ConstantConditions")
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnClickListener {
    private static final int REQUEST_WRITE = 1000;

    // 메모 쿼리 수행
    private MemoDAO mMemoDAO;
    private MemoAdapter mMemoAdapter;
    private int mCurrentLongClickPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMemoDAO = new MemoDAO(getApplicationContext());

        initViews();
    }

    /**
     * 뷰 초기화
     */
    private void initViews() {
        ArrayList<Memo> memoArrayList = mMemoDAO.findAll();

        mMemoAdapter = new MemoAdapter(memoArrayList);
        ListView listView = (ListView) findViewById(R.id.lv);
        listView.setAdapter(mMemoAdapter);

        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        registerForContextMenu(listView);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);
    }

    /**
     * 메모 추가 버튼 클릭시 호출
     * - 메모 추가 모드
     */
    @Override
    public void onClick(View v) {
        Intent addNewMemoIntent = new Intent(getApplicationContext(), ViewerActivity.class);
        addNewMemoIntent.putExtra("mode", 1).putExtra("memo", new Memo());
        startActivityForResult(addNewMemoIntent, REQUEST_WRITE);
    }

    /**
     * ListView Item 클릭시 호출
     * - 메모 뷰어 모드
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Memo item = (Memo) mMemoAdapter.getItem(position);
        System.out.println(item);
        Intent memoViewerIntent = new Intent(getApplicationContext(), ViewerActivity.class);
        memoViewerIntent.putExtra("mode", 0).putExtra("memo", item);
        startActivity(memoViewerIntent);
    }

    /**
     * ListView Item 롱클릭시 호출
     * - 메모 수정모드
     * - 메모 삭제모드
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        mCurrentLongClickPosition = position;
        return false;
    }

    /**
     * ListView Item 롱클릭시 보여줄 ContextMenu 세팅
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.memo_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    /**
     * ContextMenu 클릭시 수정 또는 삭제 작업 수행
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Memo selectedItem = (Memo) mMemoAdapter.getItem(mCurrentLongClickPosition);

        switch (item.getItemId()) {
            // db 에서 해당 메모 삭제 후 화면 갱신
            case R.id.menu_delete:
                mMemoDAO.delete(selectedItem.getId());
                mMemoAdapter.swapData(mMemoDAO.findAll());
                break;
            // 수정을 위해 ViewerActivity 실행
            case R.id.menu_modify:
                Intent modifyIntent = new Intent(getApplicationContext(), ViewerActivity.class);
                modifyIntent.putExtra("mode", 2).putExtra("memo", selectedItem);
                startActivityForResult(modifyIntent, REQUEST_WRITE);
                break;
        }
        return true;
    }

    /**
     * 수정 또는 메모 추가 모드로 ViewerActivity 실행 후 종료시 호출
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_WRITE && resultCode == RESULT_OK && data != null) {
            Memo memo = (Memo) data.getSerializableExtra("memo");
            int mode = data.getIntExtra("mode", -1);

            switch (mode) {
                // 메모 저장
                case 1:
                    mMemoDAO.save(memo);
                    break;
                // 메모 수정
                case 2:
                    mMemoDAO.update(memo);
                    break;
            }

            mMemoAdapter.swapData(mMemoDAO.findAll());
        }
    }
}

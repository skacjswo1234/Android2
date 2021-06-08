package com.tistory.massivcode.memo.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tistory.massivcode.memo.db.MemoDbContract;
import com.tistory.massivcode.memo.db.MemoDbOpenHelper;

import java.util.ArrayList;

/**
 * Created by massivcode@gmail.com on 2017. 1. 3. 09:03
 * <p>
 * Memo 테이블과 관련된 쿼리를 수행하는 클래스입니다.
 */
public class MemoDAO {
    private MemoDbOpenHelper mHelper;

    public MemoDAO(Context context) {
        this.mHelper = MemoDbOpenHelper.getInstance(context);
    }

    /**
     * 메모를 테이블에 추가합니다.
     *
     * @param data : 추가할 메모
     * @return : 성공 유무
     */
    public boolean save(Memo data) {
        // db는 읽기만 가능한 것과 읽고 쓸 수 있는 것이 있습니다.
        // 삽입은 쓰는 것이므로 getWritableDatabase() 메소드를 이용해야 합니다.
        SQLiteDatabase db = mHelper.getWritableDatabase();

        // 삽입할 데이터는 ContentValues 객체에 담깁니다.
        // 맵과 동일하게 key 와 value 로 데이터를 저장합니다.
        ContentValues values = new ContentValues();

        // 삽입할 메모의 제목, 내용, 시간을 ContentValues 에 넣습니다.
        // 메모의 id 는 AUTO INCREMENT 이므로 추가하지 않습니다.
        values.put(MemoDbContract.MemoDbEntry.COLUMN_NAME_TITLE, data.getTitle());
        values.put(MemoDbContract.MemoDbEntry.COLUMN_NAME_CONTENTS, data.getContents());
        values.put(MemoDbContract.MemoDbEntry.COLUMN_NAME_TIME, data.getTime());

        /**
         * 안드로이드에서는 기본적으로 삽입, 갱신, 삭제, 조회의 기능을 하는 메소드를 구현해놓았습니다.
         * insert 메소드의 파라메터는 다음과 같습니다.
         *
         * public long insert (String table, String nullColumnHack, ContentValues values)
         *
         * table : 삽입할 테이블, nullColumnHack : null , values : 삽입할 데이터들
         *
         * 이 메소드는 삽입된 행의 id 를 리턴합니다.
         *
         * -1 을 리턴할 경우 작업에 오류가 발생한 것 입니다.
         */
        long insertedId = db.insert(MemoDbContract.MemoDbEntry.TABLE_NAME, null, values);

        return insertedId != -1;
    }

    /**
     * 메모 테이블에 존재하는 모든 데이터를 로드합니다.
     */
    public ArrayList<Memo> findAll() {
        // SELECT 작업은 읽기 작업이므로 getReadableDatabase 메소드를 이용하여 읽기 전용 database 를 얻습니다.
        SQLiteDatabase db = mHelper.getReadableDatabase();

        /**
         * public Cursor query (String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
         * table : 접근할 테이블명, columns : 가져올 데이터들의 컬럼명,
         * selection : where 절의 key 들, selectionsArgs : where 절의 value 들
         *
         * Cursor 객체는 해당 쿼리의 결과가 담기는 객체입니다.
         */
        Cursor cursor = db.query(MemoDbContract.MemoDbEntry.TABLE_NAME, null, null, null, null, null, MemoDbContract.MemoDbEntry.COLUMN_NAME_TIME + " DESC");

        /**
         * 커서 객체는 최초 생성시 포지션이 -1 로 지정되어 있습니다.
         * cursor.moveToFirst() 메소드를 이용하면 포지션이 0으로 이동하는데,
         * moveToNext() 메소드를 이용하면 일단 0으로 이동한 뒤, 포지션을 1씩 이동합니다.
         *
         * 아래의 반복문은 커서를 0 부터 끝까지 탐색하여 각각의 위치에서 사용자가 입력한 문자열을
         * 얻어오고, 그것을 리스트에 하나씩 저장합니다.
         */
        if (cursor != null) {
            ArrayList<Memo> memoArrayList = new ArrayList<>();

            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(MemoDbContract.MemoDbEntry._ID));
                String title = cursor.getString(cursor.getColumnIndex(MemoDbContract.MemoDbEntry.COLUMN_NAME_TITLE));
                String contents = cursor.getString(cursor.getColumnIndex(MemoDbContract.MemoDbEntry.COLUMN_NAME_CONTENTS));
                long time = cursor.getLong(cursor.getColumnIndex(MemoDbContract.MemoDbEntry.COLUMN_NAME_TIME));
                memoArrayList.add(new Memo(id, title, contents, time));
            }

            cursor.close();
            return memoArrayList;
        } else {
            return null;
        }
    }

    /**
     * ListView 에서 클릭한 메모를 수정합니다.
     */
    public boolean update(Memo data) {
        // UPDATE 작업은 읽기 권한이 필요합니다.
        SQLiteDatabase db = mHelper.getWritableDatabase();

        // 수정될 데이터는 contentValues 에 담깁니다.
        ContentValues contentValues = new ContentValues();

        // 수정될 새 데이터인 제목, 내용, 시간을 contentValues 에 저장합니다.
        contentValues.put(MemoDbContract.MemoDbEntry.COLUMN_NAME_TITLE, data.getTitle());
        contentValues.put(MemoDbContract.MemoDbEntry.COLUMN_NAME_CONTENTS, data.getContents());
        contentValues.put(MemoDbContract.MemoDbEntry.COLUMN_NAME_TIME, data.getTime());

        /**
         * public int update (String table, ContentValues values, String whereClause, String[] whereArgs)
         * table : 갱신할 데이터가 존재하는 테이블, values : 변경할 값들,
         * whereClause : 조건절 (key), whereArgs : 값들
         *
         * update 메소드는 반영된 행의 카운트를 리턴합니다.
         *
         * 여기서는 메모의 id 값을 where 절로 사용하는데, id 값은 UNIQUE 하므로 반드시 반영된 행의 카운트가 1이 나와야 합니다.
         */
        int affectedRowsCount = db.update(MemoDbContract.MemoDbEntry.TABLE_NAME, contentValues, MemoDbContract.MemoDbEntry._ID + " = ?", new String[]{String.valueOf(data.getId())});
        return affectedRowsCount == 1;
    }

    /**
     * ListView 에서 롱클릭한 메모를 삭제합니다.
     */
    public boolean delete(int id) {
        // DELETE 작업은 쓰기 권한이 필요합니다.
        SQLiteDatabase db = mHelper.getWritableDatabase();

        /**
         * public int delete (String table, String whereClause, String[] whereArgs)
         * table : 지울 데이터가 위치하는 테이블, whereClause : 조건절, whereArgs : 조건절
         *
         * update 메소드와 동일하게 delete 메소드는 반영된 행의 카운트를 리턴합니다.
         */
        int affectedRowsCount = db.delete(MemoDbContract.MemoDbEntry.TABLE_NAME, MemoDbContract.MemoDbEntry._ID + " = ? ", new String[]{String.valueOf(id)});
        return affectedRowsCount == 1;
    }
}

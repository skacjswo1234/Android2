package com.tistory.massivcode.memo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.MessageFormat;

/**
 * Created by massivcode@gmail.com on 2017. 1. 3..
 * <p>
 * 안드로이드에서 DB를 여는데 도움을 주는 클래스가 아래의 SQLiteOpenHelper 클래스 입니다.
 */

public class MemoDbOpenHelper extends SQLiteOpenHelper {

    // 테이블을 만들기 전에 db 파일을 만들어야 합니다. db 파일에 관련된 테이블들을 생성해서 저장할 수 있습니다.
    private static final String DATABASE_NAME = "MemoDb.db";
    // 데이터베이스의 버전입니다. db 를 업데이트할 때 사용됩니다.
    private static final int DATABASE_VERSION = 1;
    // 메모 테이블을 만들때 사용되는 SQL 포맷 입니다
    private static final String SQL_CREATE_TABLE_FORMAT = "CREATE TABLE IF NOT EXISTS {0} " +
            "( " +
            "{1} INTEGER PRIMARY KEY AUTOINCREMENT," +
            " {2} TEXT NOT NULL," +
            " {3} TEXT NOT NULL," +
            " {4} INTEGER NOT NULL" +
            " );";

    // db 헬퍼는 싱글톤 패턴으로 구현하는 것이 좋습니다.
    // 싱글톤 패턴은 프로그램 내에서 객체의 개수가 1개로 고정되게 하는 패턴입니다.
    private static MemoDbOpenHelper sSingleton = null;

    // 싱글톤 패턴을 구현할 때, 주로 메소드를 getInstance 로 명명합니다.
    // 여러 곳에서 동시에 db 를 열면 동기화 문제가 생길 수 있기 때문에 synchronized 키워드를 이용합니다.
    public static synchronized MemoDbOpenHelper getInstance(Context context) {
        // 객체가 없을 경우에만 객체를 생성합니다.
        if (sSingleton == null) {
            sSingleton = new MemoDbOpenHelper(context);
        }

        // 객체가 이미 존재할 경우, 기존 객체를 리턴합니다.
        return sSingleton;
    }


    // 싱글톤 패턴을 구현할 때, 해당 클래스의 생성자는 private 로 선언하여 외부에서의 직접 접근을 막아야 합니다.
    private MemoDbOpenHelper(Context context) {
        // 파라메터는 다음과 같습니다.
        // Context context : db를 만들거나 열 때 사용함
        // String name : 접근할 데이터베이스의 이름입니다.
        // CursorFactory factory : Cursor 객체를 만들어야 할 때 사용하는데, 기본값은 null 입니다.
        // int version : 데이터베이스의 버전입니다. 이 값을 이용하여 업그레이드나 다운그레이드의 여부를 판단합니다.
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * DbHelper 클래스가 생성된 다음에 바로 실행되는 부분입니다.
     * db가 존재하지 않을 경우 db를 생성합니다.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = MessageFormat.format(SQL_CREATE_TABLE_FORMAT
                , MemoDbContract.MemoDbEntry.TABLE_NAME,
                MemoDbContract.MemoDbEntry._ID,
                MemoDbContract.MemoDbEntry.COLUMN_NAME_TITLE,
                MemoDbContract.MemoDbEntry.COLUMN_NAME_CONTENTS,
                MemoDbContract.MemoDbEntry.COLUMN_NAME_TIME);

        // db.execSQL(String query) 는 입력한 쿼리문을 실행하는 메소드 입니다.
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

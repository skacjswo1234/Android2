package com.koreait.exampleprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PersonProvider extends ContentProvider {
    private static final String AUTHORITY = "com.koreait.provider";
    private static final String BASE_PATH = "person";

    // content:// -> 내용 제공자에 의해 제어되는 데이터라는 의미 (프로토콜)
    // AUTHORITY -> 내용 제공자의 ID의 역할
    // BASE_PATH -> 테이블 이름
    //
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    private static final int PERSONS = 1;
    private static final int PERSON_ID = 2;

    // 내용 제공자의 CRUD 동작을 실제로 수행하는 DB
    private SQLiteDatabase database;

    //URI를 매칭하는데 사용
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // PersonProvider 내용제공자로 접근할 수 있는 URI(통로)를 제한하기 위해서
        // URIMatcher 객체에 두 개의 URI를 설정
        uriMatcher.addURI(AUTHORITY, BASE_PATH, PERSONS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", PERSON_ID);
    }

    @Override
    public boolean onCreate() {
        DatabaseHelper helper = new DatabaseHelper(getContext());
        database = helper.getWritableDatabase();

        return false;
    }

    @Nullable
    @Override
    public Cursor query( Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    // 내용 제공자를 통해서 Read를 할때 호출되는 콜백 메서드
    // projection -> 어떤 칼럼을 조회할 것인지 (null이 전달된다면 모든 칼럼을 조회)
    // selection -> WHERE절에 들어갈 조건 (null이 전달된다면 모든 데이터(레코드)를 조회)
    // selectionArgs -> JSP/Servlet에서 PreparedStatement를 구성했을 때처럼 where절을 동적으로 구성했을 때
    //                  where절에 들어가야할 값
    // sortOrder -> 정렬 방법
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignal cancellationSignal) {
        Cursor cursor;

        switch(uriMatcher.match(uri)) {
            case PERSONS:
                // 데이터베이스를 조회(SELECT)해라
                // query(1, 2, 3, 4, 5, 6, 7)
                cursor = database.query(
                        DatabaseHelper.TABLE_NAME,
                        DatabaseHelper.ALL_COLUMNS,
                        selection, null, null, null, DatabaseHelper.PERSON_NAME + "ASC");
                break;
            default:
                throw new IllegalArgumentException("알수없는 URI :" + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    // HTML타입이 무언지 알려주는 메서드
    // query 콜백 메서드를 사용해서 조회를 했을 때 조회 결과가 어떤 데이터타입인지 알려주는 메서드
    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case PERSONS:
                return "vnd.android.cursor.dir/persons";
            default:
                throw new IllegalArgumentException("알수없는 URI :" + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    // 내용제공자를 통해서 insert를 할때 호출 되는 콜백 메서드
    // values -> 추가할 데이터의 칼럼명과 칼럼의 값.
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values, Bundle extras) {
        // insert메서드는 추가된 레코드의 PRIMARY KEY값을 반환 (insert를 하지 못했다면 0을 반환)
        long id = database.insert(DatabaseHelper.TABLE_NAME, null, values);

        if (id > 0) {
            // 레코드를 추가했다면
            // 그 레코드에 접근할 수 있는 URI를 구성
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }

        throw new SQLException("추가 실패 -> URI : " + uri);
    }

    // 내용제공자를 통해서 delete 내용을삭제
    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        int count = 0;

        switch (uriMatcher.match(uri)) {
            case PERSONS:
                count = database.delete(DatabaseHelper.TABLE_NAME, s, strings);
                break;
            default:
                throw new IllegalArgumentException("알수없는 URI :" + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    // 내용제공자를 통해서 UPDATE를 하고 싶을 때 호출되는 콜백 메서드
    // values -> 수정할 칼럼의 이름, 칼럼의 값(이 매개변수는 절대 null이 되면 안된다.)
    // s -> where절
    @Override
    public int update(Uri uri, ContentValues values, String s, String[] strings) {
        int count = 0;

        switch (uriMatcher.match(uri)) {
            case PERSONS:
                count = database.update(DatabaseHelper.TABLE_NAME, values, s, strings);
                break;
            default:
                throw new IllegalArgumentException("알수없는 URI :" + uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);

        return count;
    }
}

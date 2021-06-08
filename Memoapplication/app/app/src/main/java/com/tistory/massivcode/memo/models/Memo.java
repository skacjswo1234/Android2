package com.tistory.massivcode.memo.models;

import java.io.Serializable;

/**
 * Created by massivcode@gmail.com on 2017. 1. 3..
 * <p>
 * 메모 모델 클래스
 */
public class Memo implements Serializable {
    // 메모 id
    private int id;
    // 메모 제목
    private String title;
    // 메모 내용
    private String contents;
    // 메모 시간
    private long time;

    public Memo() {
    }

    public Memo(String title, String contents, long time) {
        this.title = title;
        this.contents = contents;
        this.time = time;
    }

    public Memo(int id, String title, String contents, long time) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Memo{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", contents='").append(contents).append('\'');
        sb.append(", time=").append(time);
        sb.append('}');
        return sb.toString();
    }
}

package com.koreait.exampleextra;

import android.os.Parcel;
import android.os.Parcelable;

public class SimpleData implements Parcelable {

    int number;
    String message;

    public SimpleData(int num, String msg) {
        number = num;
        message = msg;
    }

    // 이 인스턴스가 Parcel 객체에서 다시 원래의 SimpleData 객체로 돌아오기 위한 생성자
    protected SimpleData(Parcel in) {
        number = in.readInt();
        message = in.readString();
    }

    // 이 인스턴스가 갖고 있는 데이터를 Parcel 객체로 만들어주는 역할
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(number);
        dest.writeString(message);
    }

    // 객체를 파일에 저장할 수 있음 -> 객체를 직렬화해야지 파일에 저장할 수 있음
    // 직렬화 하려는 객체의 유형을 구분할 때 사용하는 메서드
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SimpleData> CREATOR = new Creator<SimpleData>() {
        @Override
        public SimpleData createFromParcel(Parcel in) {
            return new SimpleData(in);
        }

        @Override
        public SimpleData[] newArray(int size) {
            return new SimpleData[size];
        }
    };
}

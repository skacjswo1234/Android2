package com.koreait.examplerequest;

import android.graphics.Movie;

import java.util.ArrayList;

public class MovieListResult {
    // 프로퍼티이름이 boxofficeType 이고 값의 타입이 문자열인 JSON 프로퍼티를 꺼내 담는 역할.
    String boxofficeType;
    // 프로퍼티 이름이 showRange 이고 값의 타입이 문자열인 JSON 프로퍼티를 꺼내 담는 역할.
    String showRange;

    // 프로퍼티 이름이 dailyBoxOfficeList 이고 값의 타입이 배열인 JSON 프로퍼티를 꺼내 담는 역할.
    ArrayList<Movie> dailyBoxOfficeList = new ArrayList<Movie>();
}

package com.koreait.examplerequest;

public class MovieList {
    // 변수의 이름 -> 그냥 지은게 아닌 의미를 갖고있는 변수 이름
    // 응답을 받았을 때 Gson 라이브러리를 통해서 JSON 으로 변환
    // boxOfficeResult 변수이름 -> JSON 내 프로퍼티 이름 중 하나
    // Gson을 사용해서 JSON 내 프로퍼티 값을 꺼내오려면
    // boxOfficeResult 변수 이름처럼 JSON 내 프로퍼티 이름과 동일하게
    // 변수 이름을 맞춰줘야함.
    MovieListResult boxOfficeResult;
}

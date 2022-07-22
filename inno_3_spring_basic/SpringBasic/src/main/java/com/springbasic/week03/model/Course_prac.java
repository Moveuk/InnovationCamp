package com.springbasic.week03.model;

import lombok.Data;

@Data
public class Course_prac {
    // title, tutor, days 가 Course 라는 맥락 아래에서 의도가 분명히 드러나죠!
    public String title;
    public Tutor tutor;
    public int days;
}
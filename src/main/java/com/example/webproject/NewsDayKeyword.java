package com.example.webproject;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class NewsDayKeyword {

    @Id
    private String news_date;

    private String keyword;
    private Integer keyword_count;

}

//public class NewsDayKeyword1 {
//
//    @Id
//    private String news_date;
//
//    private String keyword;
//    private Integer keyword_count;
//
//    private Float diff;
//
//}
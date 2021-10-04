package com.example.webproject;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class NewsPreviousDayKeyword {

    @Id
    private String news_date;

    private String keyword;
    private Integer keyword_count;
    private Float diff;

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
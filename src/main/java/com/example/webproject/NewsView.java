package com.example.webproject;

import groovy.transform.Immutable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Immutable
@Table(name = "news_view")
//@Subselect("select nv.* from news_view nv")
public class NewsView implements Serializable {

    @Id
    @Column(name="id", table = "news_view")
    private BigInteger id;

    @Column(name="news_date", table = "news_view")
    private String news_date;

    @Column(name="category", table = "news_view")
    private String category;

    @Column(name="title", table = "news_view")
    private String title;

    @Column(name="link", table = "news_view")
    private String link;

    @Column(name="press_agency", table = "news_view")
    private String press_agency;

    @Column(name="input_date", table = "news_view")
    private String input_date;

    @Column(name="keyword", table = "news_view")
    private String keyword;


}

package com.example.webproject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class News {

    @Id
    @Column(name="id")
    private BigInteger id;

    private String news_date;
    private String category;
    private String title;
    private String link;
    private String press_agency;
    private String input_date;
    private String keyword;


}

package com.example.webproject;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class NewsDate {

    @Id
    public String news_date;

    private int news_date_count;
}

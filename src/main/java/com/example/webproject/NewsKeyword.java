package com.example.webproject;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(KeywordID.class)
public class NewsKeyword {

    @Id
    public String category;
    @Id
    public String keyword;

    public Integer keyword_count;



}

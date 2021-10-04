package com.example.webproject;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class NewsDateKeyword {

    @Id
    @Column(name="id")
    private BigInteger id;

    private String keyword;
    private Integer keyword_count;

    public Integer total;
}

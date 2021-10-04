package com.example.webproject;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(CategoryID.class)
@Table(name = "news_category")

public class NewsCategory {

    @Id
    private String news_date;
    @Id
    private String category;

    public String getCategory() {
        return category;
    }

    private BigInteger category_count;


}

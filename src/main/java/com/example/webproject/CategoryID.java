package com.example.webproject;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryID implements Serializable {

    private String news_date;
    private String category;


}

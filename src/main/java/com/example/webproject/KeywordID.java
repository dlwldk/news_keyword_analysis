package com.example.webproject;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class KeywordID implements Serializable {
    public String category;
    public String keyword;

}

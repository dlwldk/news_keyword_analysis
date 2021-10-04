package com.example.webproject.mapper;


import com.example.webproject.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface NewsMapper {

    List<NewsCategory> getNewsCategoryList(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
    List<NewsKeyword> getNewsKeywordList(@Param("fromDate") String fromDate, @Param("toDate") String toDate, @Param("category") String category);
    List<NewsDateKeyword> getNewsDateKeywordList(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
    int getNewsDayKeywordTotalList(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
    int getNewsDateKeywordTotalList(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
    List<NewsDate> getNewsDateList(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
    List<NewsDayKeyword> getNewsDayKeywordList(@Param("fromDate") String fromDate, @Param("toDate") String toDate, @Param("keyword") String keyword);
    Page<News> gettest(@Param("fromDate") String fromDate, @Param("toDate") String toDate, Pageable pageable);
}

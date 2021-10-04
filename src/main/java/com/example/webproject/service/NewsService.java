package com.example.webproject.service;


import com.example.webproject.*;
import com.example.webproject.mapper.NewsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

//import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("newsService")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsService {

    private final NewsMapper newsMapper;

    public List<NewsCategory> newsCategoryCount(String fromDate, String toDate) {
        return newsMapper.getNewsCategoryList(fromDate, toDate);
    }

    public List<NewsKeyword> newsKeywordCount(String fromDate, String toDate, String category) {
        return newsMapper.getNewsKeywordList(fromDate, toDate, category);
    }

    public List<NewsDateKeyword> newsDateKeywordCount(String fromDate, String toDate) {
        return newsMapper.getNewsDateKeywordList(fromDate, toDate);
    }

    public int newsDayKeywordTotalCount(String fromDate, String toDate) {
        return newsMapper.getNewsDayKeywordTotalList(fromDate, toDate);
    }

    public int newsDateKeywordTotalCount(String fromDate, String toDate) {
        return newsMapper.getNewsDateKeywordTotalList(fromDate, toDate);
    }

    public List<NewsDate> newsDateCount(String fromDate, String toDate) {
        return newsMapper.getNewsDateList(fromDate, toDate);
    }

    public List<NewsDayKeyword> newsDayKeywordCount (String fromDate, String toDate, String keyword) {
        return newsMapper.getNewsDayKeywordList(fromDate, toDate, keyword);
    }

    public Page<News> test (String fromDate, String toDate, Pageable pageable) {
        return newsMapper.gettest(fromDate, toDate, pageable);
    }

//    public List<NewsDayKeyword> newsDayKeywordCountcompare (List<NewsDayKeyword> list1, List<NewsDayKeyword> list2) {
//        List<NewsDayKeyword1> result = new List<NewsDayKeyword1>() {
//            list1.item list2.
//        }
//        return newsMapper.getNewsDayKeywordList(fromDate, toDate, keyword);
//    }

}

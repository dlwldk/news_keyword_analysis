package com.example.webproject.controller;

import com.example.webproject.*;
import com.example.webproject.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class ProgramController {

    @Resource(name = "newsService")
    private final NewsService newsService;
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private NewsDateRepository newsDateRepository;



    @RequestMapping(value = "/collected_data", method = RequestMethod.GET)
    public String collected_data(
            Model model,
            HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "") String searchText,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String firstcolName = request.getParameter("firstcolName");
        String secondcolName = request.getParameter("secondcolName");
        //String searchText = request.getParameter("searchText");
        String toDate;
        String fromDate;

        if (from != null && to != null) {
            fromDate = from.replace("-", "");
            toDate = to.replace("-", "");
        } else {
            fromDate = "20210801";
            toDate = "20210831";
            from = "2021-08-01";
            to = "2021-08-31";
        }


        System.out.println("되긴하니----------------" );
        //Page<News> newsList;
        Page<News> newsList = newsRepository.findNewsAll(fromDate, toDate, firstcolName, pageable);

        if(searchText.length() > 0) { //검색한 키워드가 있을 때
            newsList = newsRepository.findNewsSearch(fromDate, toDate, firstcolName, secondcolName, searchText, pageable);
        }
        else { //검색한 키워드가 없을 때
            newsList = newsRepository.findNewsAll(fromDate, toDate, firstcolName, pageable);
        }




//        Page<News> newsList = newsService.test(fromDate, toDate, pageable);
//        Page<News> newsList = newsRepository.findNewsSearch(fromDate, toDate, searchText, pageable);
//        Page<NewsView> newsList = newsViewRepository.findNewsViewAll(fromDate, toDate, pageable);
//        if (firstcolName == "" && secondcolName == "" && searchText.length() == 0) {
//            newsList = newsRepository.findNewsAll(fromDate, toDate, pageable);
//        }
//        if(firstcolName != "") { //분류 설정을 했을 때
//            if(secondcolName != "") {
//                if(searchText.length() == 0) { //검색한 키워드가 없을 때
//
//
//                }
//                else { //검색한 키워드가 없을 때
//
//                }
//
//            }
//            else {
//                if(searchText.length() == 0) { //검색한 키워드가 없을 때
//
//
//                }
//                else { //검색한 키워드가 있을 때
//                    newsList = newsRepository.findNewsCategorySearch(fromDate, toDate, firstcolName, searchText, pageable);
//                }
//
//            }
//            newsList = newsRepository.findNewsCategory(fromDate, toDate, firstcolName, pageable);
//
//        }
//        else { //분류 설정을 하지 않았을 때
//            if(secondcolName != "") {
//                if(searchText.length() == 0) { //검색한 키워드가 없을 때
//                    newsList = newsRepository.findNewsAll(fromDate, toDate, pageable);
//
//                }
//                else { //검색한 키워드가 있을 때
//
//                }
//
//            }
//            else {
//                if(searchText.length() == 0) { //검색한 키워드가 없을 때
//
//
//                }
//                else { //검색한 키워드가 없을 때
//
//                }
//
//            }
//
//
//
//        }
//        if(firstcolName != "" && secondcolName == "" && searchText.length() == 0) {
//            newsList = newsRepository.findNewsCategory(fromDate, toDate, firstcolName, pageable);
//        }
//        if(firstcolName == "" && secondcolName == "" && searchText.length() > 0) {
//            newsList = newsRepository.findNewsSearch(fromDate, toDate, searchText, pageable);
//        }

        System.out.println("firstcolName: " + firstcolName);
        System.out.println("secondcolName: " + secondcolName);
        System.out.println("searchText: " + searchText);

        List<NewsDate> newsDateList = newsDateRepository.newsListDate(fromDate, toDate);

        model.addAttribute("fromDate", from);
        model.addAttribute("toDate", to);
        int startPage = Math.max(1, newsList.getPageable().getPageNumber() - 5); //시작 페이지
        int endPage = Math.min(newsList.getTotalPages(), newsList.getPageable().getPageNumber() + 5); //끝페이지
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("list", newsList); //뉴스 목록
        model.addAttribute("newsdatelist", newsDateList); //뉴스 날짜 갯수 목록

        return "collected_data_page.html"; // templates/collected_data.html -> 브라우저로 전송
    }

    @RequestMapping(value = "/classification", method = RequestMethod.GET)
    public String classification(Model model, HttpServletRequest request) {
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String toDate;
        String fromDate;

        if (from != null && to != null) {
            fromDate = from.replace("-", "");
            toDate = to.replace("-", "");

        } else {
            fromDate = "20200901";
            toDate = "20210831";
            from = "2020-09-01";
            to = "2021-08-31";
        }

        model.addAttribute("fromDate", from);
        model.addAttribute("toDate", to);
        model.addAttribute("newscategorycount", newsService.newsCategoryCount(fromDate, toDate));

        return "classification_page.html"; // templates/greetings.mustache -> 브라우저로 전송
    }

    @RequestMapping(value = "/classification_keyword", method = RequestMethod.GET)
    public String classification_keyword(Model model, String from, String to, String category) {

        String fromDate;
        String toDate;

        if (from != null && to != null) {
            fromDate = from.replace("-", "");
            toDate = to.replace("-", "");

        } else {
            fromDate = "20200901";
            toDate = "20210831";
        }
        model.addAttribute("newskeywordcount", newsService.newsKeywordCount(fromDate, toDate, category));
        return "classification_keyword.html";
    }


    @RequestMapping(value = "/keyword_analysis", method = RequestMethod.GET)
    public String keyword_analysis(Model model, HttpServletRequest request) throws ParseException {
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String current_toDate;
        String current_fromDate;
        String previous_fromDate;
        String previous_toDate;

        if (from != null && to != null) {
            current_fromDate = from.replace("-", "");
            current_toDate = to.replace("-", "");

        } else {
            current_fromDate = "20200901";
            current_toDate = "20210831";
            from = "2020-09-01";
            to = "2021-08-31";
        }
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        Date pvfr = transFormat.parse(current_fromDate);
        cal.setTime(pvfr);
        cal.add(Calendar.YEAR, -1);
        previous_fromDate = transFormat.format(cal.getTime());
        Date pvto = transFormat.parse(current_toDate);
        cal.setTime(pvto);
        cal.add(Calendar.YEAR, -1);
        previous_toDate = transFormat.format(cal.getTime());
        model.addAttribute("fromDate", from);
        model.addAttribute("toDate", to);
        model.addAttribute("current_newsdatekeyword", newsService.newsDateKeywordCount(current_fromDate, current_toDate));
        model.addAttribute("previous_newsdatekeyword", newsService.newsDateKeywordCount(previous_fromDate, previous_toDate));
        model.addAttribute("newsdaykeywordtotal", newsService.newsDayKeywordTotalCount(current_fromDate, current_toDate));
        model.addAttribute("newsdatekeywordtotal", newsService.newsDateKeywordTotalCount(current_fromDate, current_toDate));
        return "keyword_analysis_page.html";
    }

    @RequestMapping(value = "/keyword_analysis_keyword", method = RequestMethod.GET)
    public String keyword_analysis_keyword(Model model, String from, String to, String keyword) throws ParseException {

        String previous_fromDate;
        String previous_toDate;
        String current_fromDate;
        String current_toDate;

        if (from != null && to != null) {
            current_fromDate = from.replace("-", "");
            current_toDate = to.replace("-", "");
        } else {
            current_fromDate = "20200901";
            current_toDate = "20210831";
        }
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        Date pvfr = transFormat.parse(current_fromDate);
        cal.setTime(pvfr);
        cal.add(Calendar.YEAR, -1);
        previous_fromDate = transFormat.format(cal.getTime());
        Date pvto = transFormat.parse(current_toDate);
        cal.setTime(pvto);
        cal.add(Calendar.YEAR, -1);
        previous_toDate = transFormat.format(cal.getTime());


        model.addAttribute("current_newsdatekeyword", newsService.newsDateKeywordCount(current_fromDate, current_toDate));
        model.addAttribute("previous_newsdatekeyword", newsService.newsDateKeywordCount(previous_fromDate, previous_toDate));
        model.addAttribute("current_newsdate", newsService.newsDateCount(current_fromDate, current_toDate));
        model.addAttribute("previous_newsdate", newsService.newsDateCount(previous_fromDate, previous_toDate));
        model.addAttribute("current_newsdaykeyword", newsService.newsDayKeywordCount(current_fromDate, current_toDate, keyword));
        model.addAttribute("previous_newsdaykeyword", newsService.newsDayKeywordCount(previous_fromDate, previous_toDate, keyword));
        return "keyword_analysis_keyword.html";
    }

}

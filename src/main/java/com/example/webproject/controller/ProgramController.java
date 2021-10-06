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
            @ModelAttribute("search") Search search,
            HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "") String searchText,
            @RequestParam(required = false, defaultValue = "2021-08-01") String from,
            @RequestParam(required = false, defaultValue = "2021-08-31") String to,
            @RequestParam(required = false, defaultValue = "") String firstcolName,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        String secondcolName = request.getParameter("secondcolName");

        String fromDate = from.replace("-", "");
        String toDate = to.replace("-", "");


        Page<News> newsList = newsRepository.findNewsAll(fromDate, toDate, firstcolName, pageable);

        if(searchText.length() > 0) { //검색한 키워드가 있을 때
            if(secondcolName.equals("keyword")) {
                System.out.println("여기는 keyword");
                newsList = newsRepository.findNewsKeyword(fromDate, toDate, firstcolName, searchText, pageable);

            }
            else if(secondcolName.equals("title")) {
                System.out.println("여기는 title");
                newsList = newsRepository.findNewsTitle(fromDate, toDate, firstcolName, searchText, pageable);

            }
            else {
                System.out.println("여기는 press_agency");
                newsList = newsRepository.findNewsPressAgency(fromDate, toDate, firstcolName, searchText, pageable);

            }
        }

        List<NewsDate> newsDateList = newsDateRepository.newsListDate(fromDate, toDate);

        model.addAttribute("fromDate", from);
        model.addAttribute("toDate", to);
//        int startPage = Math.max(1, newsList.getPageable().getPageNumber() - 5); //시작 페이지
        int startPage = Math.max(1, newsList.getPageable().getPageNumber()); //시작 페이지
        int endPage = Math.min(newsList.getTotalPages(), newsList.getPageable().getPageNumber() + 10); //끝페이지
        System.out.println("startPage:" + startPage);
        System.out.println("endPage:" + endPage);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("list", newsList); //뉴스 목록
        model.addAttribute("newsdatelist", newsDateList); //뉴스 날짜 갯수 목록

        return "collected_data_page.html"; // templates/collected_data.html -> 브라우저로 전송
    }

    @RequestMapping(value = "/classification", method = RequestMethod.GET)
    public String classification(Model model, @RequestParam(required = false, defaultValue = "2020-09-01") String from,
                                 @RequestParam(required = false, defaultValue = "2021-08-31") String to) {
        String fromDate = from.replace("-", "");
        String toDate = to.replace("-", "");
        model.addAttribute("fromDate", from);
        model.addAttribute("toDate", to);
        model.addAttribute("newscategorycount", newsService.newsCategoryCount(fromDate, toDate));

        return "classification_page.html"; // templates/greetings.mustache -> 브라우저로 전송
    }

    @RequestMapping(value = "/classification_keyword", method = RequestMethod.GET)
    public String classification_keyword(Model model, @RequestParam(required = false, defaultValue = "2020-09-01") String from,
                                         @RequestParam(required = false, defaultValue = "2021-08-31") String to, String category) {

        String fromDate = from.replace("-", "");
        String toDate = to.replace("-", "");

        model.addAttribute("newskeywordcount", newsService.newsKeywordCount(fromDate, toDate, category));
        return "classification_keyword.html";
    }


    @RequestMapping(value = "/keyword_analysis", method = RequestMethod.GET)
    public String keyword_analysis(Model model, @RequestParam(required = false, defaultValue = "2020-09-01") String from,
                                   @RequestParam(required = false, defaultValue = "2021-08-31") String to) throws ParseException {
        String current_toDate = to.replace("-", "");;
        String current_fromDate = from.replace("-", "");;
        String previous_fromDate;
        String previous_toDate;

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
    public String keyword_analysis_keyword(Model model, @RequestParam(required = false, defaultValue = "2020-09-01") String from,
                                           @RequestParam(required = false, defaultValue = "2021-08-31") String to, String keyword) throws ParseException {

        String previous_fromDate;
        String previous_toDate;
        String current_fromDate = from.replace("-", "");;
        String current_toDate = to.replace("-", "");;

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

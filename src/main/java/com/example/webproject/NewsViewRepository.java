package com.example.webproject;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigInteger;

@Repository
@Transactional
public interface NewsViewRepository extends JpaRepository<NewsView, BigInteger> {


    @Query(value = "SELECT ROW_NUMBER() OVER(ORDER BY nv.input_date) AS id, nv.news_date AS news_date, nv.category AS category, nv.title AS title, nv.link AS link, nv.press_agency AS press_agency, nv.keyword AS keyword, nv.input_date AS input_date\n" +
            "fROM news_view nv \n" +
            "where nv.news_date >= to_date(:fromDate, 'YYYYMMDD') and nv.news_date <= to_date(:toDate, 'YYYYMMDD') group by nv.news_date, nv.input_date, nv.serial_num, nv.category, nv.keyword, nv.title, nv.press_agency, nv.link", nativeQuery = true)
    Page<NewsView> findNewsViewAll(@Param("fromDate") String fromDate, @Param("toDate") String toDate, Pageable pageable);

    @Query(value = "SELECT ROW_NUMBER() OVER(ORDER BY nv.input_date) AS id, nv.news_date AS news_date, nv.category AS category, nv.title AS title, nv.link AS link, nv.press_agency AS press_agency, nv.keyword AS keyword, nv.input_date AS input_date\n" +
            "fROM news_view nv \n" +
            "where nv.news_date >= to_date(:fromDate, 'YYYYMMDD') and nv.news_date <= to_date(:toDate, 'YYYYMMDD') AND text(category) = text(:category) group by nv.news_date, nv.input_date, nv.serial_num, nv.category, nv.keyword, nv.title, nv.press_agency, nv.link", nativeQuery = true)
    Page<NewsView> findNewsViewCategory(@Param("fromDate") String fromDate, @Param("toDate") String toDate, @Param("firstcolName") String category, Pageable pageable);

    @Query(value = "SELECT ROW_NUMBER() OVER(ORDER BY nv.input_date) AS id, nv.news_date AS news_date, nv.category AS category, nv.title AS title, nv.link AS link, nv.press_agency AS press_agency, nv.keyword AS keyword, nv.input_date AS input_date\n" +
            "fROM news_view nv \n" +
            "where nv.news_date >= to_date(:fromDate, 'YYYYMMDD') and nv.news_date <= to_date(:toDate, 'YYYYMMDD') AND text(keyword) like '%text(:search)%' group by nv.news_date, nv.input_date, nv.serial_num, nv.category, nv.keyword, nv.title, nv.press_agency, nv.link", nativeQuery = true)
    Page<NewsView> findNewsViewSearch(@Param("fromDate") String fromDate, @Param("toDate") String toDate, @Param("searchText") String search, Pageable pageable);



//    @Query(value = "SELECT ROW_NUMBER() OVER(ORDER BY n.input_date) AS id, n.news_date AS news_date, nc.category AS category, n.title AS title, nl.link AS link, n.press_agency AS press_agency, array_to_string(array_agg(nc.keyword), ', ') AS keyword, n.input_date AS input_date\n" +
//            " \t\tFROM news n LEFT JOIN news_category nc ON n.news_date = nc.news_date AND n.serial_num = nc.serial_num\n" +
//            " \t\t\t\t\tLEFT JOIN news_list nl ON n.news_date = nl.news_date AND n.serial_num = nl.serial_num\n" +
//            " \t\tWHERE n.news_date >= to_date(:fromDate, 'YYYYMMDD') AND n.news_date <= to_date(:toDate, 'YYYYMMDD') group by n.news_date, n.input_date, n.serial_num, nc.category, n.title, n.press_agency, nl.link", nativeQuery = true)
//    Page<News> findNewsDivision(@Param("fromDate") String fromDate, @Param("toDate") String toDate, @Param("secondcolName") String division, Pageable pageable);

}

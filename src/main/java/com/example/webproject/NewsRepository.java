package com.example.webproject;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, BigInteger> {

    @Query(value = "SELECT ROW_NUMBER() OVER(ORDER BY n.input_date) AS id, n.news_date AS news_date, nc.category AS category, n.title AS title, nl.link AS link, n.press_agency AS press_agency, array_to_string(array_agg(nc.keyword), ', ') AS keyword, n.input_date AS input_date\n" +
            " \t\tFROM news n LEFT JOIN news_category nc ON n.news_date = nc.news_date AND n.serial_num = nc.serial_num\n" +
            " \t\t\t\t\tLEFT JOIN news_list nl ON n.news_date = nl.news_date AND n.serial_num = nl.serial_num\n" +
            " \t\tWHERE n.news_date >= to_date(:fromDate, 'YYYYMMDD') AND n.news_date <= to_date(:toDate, 'YYYYMMDD') AND (:category = '' or text(nc.category) = text(:category)) group by n.news_date, n.input_date, n.serial_num, nc.category, n.title, n.press_agency, nl.link", nativeQuery = true)
    Page<News> findNewsAll(@Param("fromDate") String fromDate, @Param("toDate") String toDate, @Param("firstcolName") String category, Pageable pageable);


//    @Query(value = "SELECT ROW_NUMBER() OVER(ORDER BY n.input_date) AS id, n.news_date AS news_date, nc.category AS category, n.title AS title, nl.link AS link, n.press_agency AS press_agency, array_to_string(array_agg(nc.keyword), ', ') AS keyword, n.input_date AS input_date\n" +
//            " \t\tFROM news n LEFT JOIN news_category nc ON n.news_date = nc.news_date AND n.serial_num = nc.serial_num\n" +
//            " \t\t\t\t\tLEFT JOIN news_list nl ON n.news_date = nl.news_date AND n.serial_num = nl.serial_num\n" +
//            " \t\tWHERE n.news_date >= to_date(:fromDate, 'YYYYMMDD') AND n.news_date <= to_date(:toDate, 'YYYYMMDD') AND text(category) = text(:category) group by n.news_date, n.input_date, n.serial_num, nc.category, n.title, n.press_agency, nl.link", nativeQuery = true)
//    Page<News> findNewsCategory(@Param("fromDate") String fromDate, @Param("toDate") String toDate, @Param("firstcolName") String category, Pageable pageable);
//    @Query(value = "SELECT ROW_NUMBER() OVER(ORDER BY n.input_date) AS id, n.news_date AS news_date, nc.category AS category, n.title AS title, nl.link AS link, n.press_agency AS press_agency, array_to_string(array_agg(nc.keyword), ', ') AS keyword, n.input_date AS input_date\n" +
//            " \t\tFROM news n LEFT JOIN news_category nc ON n.news_date = nc.news_date AND n.serial_num = nc.serial_num\n" +
//            " \t\t\t\t\tLEFT JOIN news_list nl ON n.news_date = nl.news_date AND n.serial_num = nl.serial_num\n" +
//            " \t\tWHERE n.news_date >= to_date(:fromDate, 'YYYYMMDD') AND n.news_date <= to_date(:toDate, 'YYYYMMDD') AND (text(:category) = '' or text(nc.category) = text(:category)))" +
//            "group by n.news_date, n.input_date, n.serial_num, nc.category, n.title, n.press_agency, nl.link", nativeQuery = true)
//    Page<News> findNewsCategory(@Param("fromDate") String fromDate, @Param("toDate") String toDate, @Param("firstcolName") String category, @Param("secondcolName") String distinct, Pageable pageable);


    @Query(value = "select ROW_NUMBER() OVER(ORDER BY n.input_date) AS id, n.news_date AS news_date, nc.category AS category, n.title AS title,n.link, n.press_agency AS press_agency ,nc3.keyword AS keyword ,n.input_date AS input_date\n" +
            "from news n ,news_category nc ,news_list nl , \n" +
            "\t(select nc.news_date ,nc.serial_num , array_to_string(array_agg(distinct (nc.keyword)), ', ') AS keyword\n" +
            "\tfrom news_category nc ,(select * from news_category nc2 where (keyword like '%' || text(:search) || '%')) as nc2\n" +
            "\twhere nc.news_date = nc2.news_date and nc.serial_num = nc2.serial_num\n" +
            "\tgroup by  nc.news_date ,nc.serial_num) as nc3 \n" +
            "where n.news_date = nc.news_date and n.serial_num = nc.serial_num \n" +
            "and n.news_date = nl.news_date and n.serial_num = nl.serial_num\n" +
            "and n.news_date = nc3.news_date and n.serial_num = nc3.serial_num\n" +
            "and n.news_date between to_date(:fromDate, 'YYYYMMDD') and to_date(:toDate, 'YYYYMMDD') and (text(:category) = '' or text(nc.category) = text(:category))\n" +
            "group by n.news_date, nc.category ,n.title ,n.press_agency, n.link ,nc3.keyword,n.input_date \n", nativeQuery = true)
    Page<News> findNewsKeyword(@Param("fromDate") String fromDate, @Param("toDate") String toDate, @Param("firstcolName") String category, @Param("searchText") String search, Pageable pageable);

    @Query(value = "SELECT ROW_NUMBER() OVER(ORDER BY n.input_date) AS id, n.news_date AS news_date, nc.category AS category, n.title AS title, nl.link AS link, n.press_agency AS press_agency, array_to_string(array_agg(nc.keyword), ', ') AS keyword, n.input_date AS input_date\n" +
            "FROM news n LEFT JOIN news_category nc ON n.news_date = nc.news_date AND n.serial_num = nc.serial_num\n" +
            "LEFT JOIN news_list nl ON n.news_date = nl.news_date AND n.serial_num = nl.serial_num\n" +
            "WHERE n.news_date >= to_date(:fromDate, 'YYYYMMDD') AND n.news_date <= to_date(:toDate, 'YYYYMMDD') AND (text(:category) = '' or text(nc.category) = text(:category)) AND text(n.title) like '%' || text(:search) || '%'\n" +
            "group by n.news_date, n.input_date, n.serial_num, nc.category, n.title, n.press_agency, nl.link", nativeQuery = true)
    Page<News> findNewsTitle(@Param("fromDate") String fromDate, @Param("toDate") String toDate, @Param("firstcolName") String category, @Param("searchText") String search, Pageable pageable);


    @Query(value = "SELECT ROW_NUMBER() OVER(ORDER BY n.input_date) AS id, n.news_date AS news_date, nc.category AS category, n.title AS title, nl.link AS link, n.press_agency AS press_agency, array_to_string(array_agg(nc.keyword), ', ') AS keyword, n.input_date AS input_date\n" +
            "FROM news n LEFT JOIN news_category nc ON n.news_date = nc.news_date AND n.serial_num = nc.serial_num\n" +
            "LEFT JOIN news_list nl ON n.news_date = nl.news_date AND n.serial_num = nl.serial_num\n" +
            "WHERE n.news_date >= to_date(:fromDate, 'YYYYMMDD') AND n.news_date <= to_date(:toDate, 'YYYYMMDD') AND (text(:category) = '' or text(nc.category) = text(:category)) AND text(n.press_agency) like '%' || text(:search) || '%'\n" +
            "group by n.news_date, n.input_date, n.serial_num, nc.category, n.title, n.press_agency, nl.link", nativeQuery = true)
    Page<News> findNewsPressAgency(@Param("fromDate") String fromDate, @Param("toDate") String toDate, @Param("firstcolName") String category, @Param("searchText") String search, Pageable pageable);

//
//
//    @Query(value = "select ROW_NUMBER() OVER(ORDER BY n.input_date) AS id, n.news_date AS news_date, nc.category AS category, n.title AS title,n.link, n.press_agency AS press_agency ,nc3.keyword AS keyword ,n.input_date AS input_date\n" +
//            "from news n ,news_category nc ,news_list nl , \n" +
//            "\t(select nc.news_date ,nc.serial_num , array_to_string(array_agg(distinct (nc.keyword)), ', ') AS keyword\n" +
//            "\tfrom news_category nc ,(select * from news_category nc2 where keyword like %:search%) as nc2\n" +
//            "\twhere nc.news_date = nc2.news_date and nc.serial_num = nc2.serial_num\n" +
//            "\tgroup by  nc.news_date ,nc.serial_num) as nc3 \n" +
//            "where n.news_date = nc.news_date and n.serial_num = nc.serial_num \n" +
//            "and n.news_date = nl.news_date and n.serial_num = nl.serial_num\n" +
//            "and n.news_date = nc3.news_date and n.serial_num = nc3.serial_num\n" +
//            "and n.news_date between to_date(:fromDate, 'YYYYMMDD') and to_date(:toDate, 'YYYYMMDD') and text(nc.category) = text(:category)\n" +
//            "group by n.news_date, nc.category ,n.title ,n.press_agency, n.link ,nc3.keyword,n.input_date \n", nativeQuery = true)
//    Page<News> findNewsCategorySearch(@Param("fromDate") String fromDate, @Param("toDate") String toDate, @Param("firstcolName") String category, @Param("searchText") String search, Pageable pageable);




//    @Query(value = "SELECT ROW_NUMBER() OVER(ORDER BY n.input_date) AS id, n.news_date AS news_date, nc.category AS category, n.title AS title, nl.link AS link, n.press_agency AS press_agency, array_to_string(array_agg(nc.keyword), ', ') AS keyword, n.input_date AS input_date\n" +
//            " \t\tFROM news n LEFT JOIN news_category nc ON n.news_date = nc.news_date AND n.serial_num = nc.serial_num\n" +
//            " \t\t\t\t\tLEFT JOIN news_list nl ON n.news_date = nl.news_date AND n.serial_num = nl.serial_num\n" +
//            " \t\tWHERE n.news_date >= to_date(:fromDate, 'YYYYMMDD') AND n.news_date <= to_date(:toDate, 'YYYYMMDD') group by n.news_date, n.input_date, n.serial_num, nc.category, n.title, n.press_agency, nl.link", nativeQuery = true)
//    Page<News> findNewsDivision(@Param("fromDate") String fromDate, @Param("toDate") String toDate, @Param("secondcolName") String division, Pageable pageable);

}

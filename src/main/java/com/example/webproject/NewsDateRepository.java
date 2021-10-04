package com.example.webproject;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface NewsDateRepository extends JpaRepository<NewsDate, String> {

    @Query(value = "SELECT news_date AS news_date, count(*) AS news_date_count FROM news_list\n" +
            "\t\tWHERE news_date >= to_date(:fromDate, 'YYYYMMDD') AND news_date <= to_date(:toDate, 'YYYYMMDD') group by news_date", nativeQuery = true)
    List<NewsDate> newsListDate(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

}

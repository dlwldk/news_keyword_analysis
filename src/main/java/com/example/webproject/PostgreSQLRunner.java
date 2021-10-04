package com.example.webproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Component
public class PostgreSQLRunner implements ApplicationRunner {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        try (Connection connection = dataSource.getConnection()){
//            System.out.println(connection);
//            System.out.println(dataSource.getClass());
//            System.out.println(connection.getMetaData().getURL());
//            System.out.println(connection.getMetaData().getUserName());
//
            Statement statement = connection.createStatement();
//            //String sql = "CREATE TABLE t_product(product_no INTEGER NOT NULL, product_name VARCHAR(255), PRIMARY KEY (product_no))";
//            String sql = "SELECT news_date, count(*) FROM news_list group by news_date limit 30";
//            ResultSet rs = statement.executeQuery(sql);
//            while (rs.next())
//            {
//                System.out.println("DATE = " + rs.getString(1) + ", COUNT = " + rs.getInt(2));
//            }
            System.out.println("db 커넥션 성공");

        }
        jdbcTemplate.execute("select * from news limit 10");
    }


}

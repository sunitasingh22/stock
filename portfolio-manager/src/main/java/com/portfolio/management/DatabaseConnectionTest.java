package com.portfolio.management;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConnectionTest implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) {
        try {
            if (dataSource.getConnection() != null) {
                System.out.println("Database connection successful!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Database connection failed!");
        }
    }
}

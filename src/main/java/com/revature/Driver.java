package com.revature;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@Log4j2
@SpringBootApplication
@EnableAutoConfiguration
public class Driver {

    public static void main(String[] args) {
        String[] betterArgs = new String[]{
                "spring.datasource.url=" + System.getenv("DB_URL"),
                "spring.datasource.username=" + System.getenv("DB_USER"),
                "spring.datasource.password=" + System.getenv("DB_PASS"),
                "spring.datasource.driver=" + System.getenv("DB_DRIVER"),
                "spring.datasource.dialect=" + System.getenv("DB_DIALECT")
        };
        SpringApplication.run(Driver.class, betterArgs);
    }
}

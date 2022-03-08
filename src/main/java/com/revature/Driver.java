package com.revature;

import net.minidev.json.JSONUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class Driver {
    public static void main(String[] args) {
        SpringApplication.run(Driver.class, args);
    }
}
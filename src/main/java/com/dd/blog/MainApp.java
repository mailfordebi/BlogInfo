package com.dd.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan
@SpringBootApplication
public class MainApp {
   public static void main(String[] args) {
      SpringApplication.run(MainApp.class, args);
   }
}

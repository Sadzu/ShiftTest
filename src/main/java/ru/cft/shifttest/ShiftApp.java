package ru.cft.shifttest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ShiftApp {

    public static void main(String[] args) {
        SpringApplication.run(ShiftApp.class, args);
    }

}

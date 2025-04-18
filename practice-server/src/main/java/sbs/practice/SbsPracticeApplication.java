package sbs.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SbsPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbsPracticeApplication.class, args);
    }

}

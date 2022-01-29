package me.junsu.demojpastudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DemoJpaStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoJpaStudyApplication.class, args);
    }

}

package com.drools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.drools.config.DroolsConfig;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@Import({DroolsConfig.class})
@EnableSwagger2
@EntityScan(basePackages = {"com.drools.jpa"})
@CrossOrigin(origins="*")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
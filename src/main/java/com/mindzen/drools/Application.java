package com.mindzen.drools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.mindzen.drools.config.DroolsConfig;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@Import({DroolsConfig.class})
@EnableSwagger2
@EntityScan(basePackages = {"com.mindzen.drools.jpa"})
@CrossOrigin(origins="*")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
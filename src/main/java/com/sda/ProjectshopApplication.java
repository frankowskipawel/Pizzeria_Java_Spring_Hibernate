package com.sda;

import com.sda.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;



//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@SpringBootApplication
//@EnableConfigurationProperties(StorageProperties.class)
public class ProjectshopApplication {


    public static void main(String[] args) {
        SpringApplication.run(ProjectshopApplication.class, args);


    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
//            storageService.deleteAll();
            storageService.init();
        };
    }
}

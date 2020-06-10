package com.sda;

import com.sda.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@SpringBootApplication
public class ProjectshopApplication {


    public static void main(String[] args) {
        SpringApplication.run(ProjectshopApplication.class, args);


    }
}

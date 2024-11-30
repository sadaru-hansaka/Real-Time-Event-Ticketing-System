package org.Backend.ticketing_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication//(exclude = {DataSourceAutoConfiguration.class })
@EnableJpaRepositories(basePackages = "org.Backend.ticketing_system.repositary")
public class TicketingSystemApplication {

    public static void main(String[] args) {

        SpringApplication.run(TicketingSystemApplication.class, args);


    }

}

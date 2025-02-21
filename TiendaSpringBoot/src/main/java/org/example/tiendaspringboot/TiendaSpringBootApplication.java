package org.example.tiendaspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TiendaSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(TiendaSpringBootApplication.class, args);
    }

}

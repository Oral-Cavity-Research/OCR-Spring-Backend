package com.oasis.ocrspring.config;

import io.github.cdimascio.dotenv.Dotenv;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class EnvConfig {

    @PostConstruct
    public void init() {
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
    }
}

package com.ottistech.indespensa.api.ms_indespensa.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APIConfig {
    @Bean
    public Dotenv loadEnv() {
        Dotenv env = Dotenv.configure().load();
        String databaseUrl = env.get("DATABASE_URL");
        String databaseUsername = env.get("DATABASE_USERNAME");
        String databasePassword = env.get("DATABASE_PASSWORD");

        System.setProperty("DATABASE_URL", databaseUrl);
        System.setProperty("DATABASE_USERNAME", databaseUsername);
        System.setProperty("DATABASE_PASSWORD", databasePassword);

        return env;
    }
}

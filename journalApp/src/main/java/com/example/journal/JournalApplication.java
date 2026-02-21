package com.example.journal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(JournalApplication.class, args);
        System.out.println(context.getEnvironment());
	}

    @Bean
    public PlatformTransactionManager add(MongoDatabaseFactory dbFactory){
        return new MongoTransactionManager(dbFactory);
    }
}

// PlatformTransactionManager bean is automatically configured by Spring Boot
// when you use @EnableTransactionManagement annotation along with the appropriate dependencies for your database
// (like Spring Data JPA, Spring Data MongoDB, etc.).

// MongoTransactionManager is specifically used for managing transactions in MongoDB databases.
// If your application uses MongoDB and you have the necessary Spring Data MongoDB dependencies,
// Spring Boot will automatically configure a MongoTransactionManager bean for you when you enable transaction management.

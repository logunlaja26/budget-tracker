package com.lyomann.budgettracker.config;


import com.lyomann.budgettracker.repository.EntryRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = EntryRepository.class)
@Configuration
public class MongoDBConfig {

}

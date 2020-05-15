package com.lyomann.budgettracker.repository;

import com.lyomann.budgettracker.document.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EntryRepository extends MongoRepository<Entry,Integer> {

}
